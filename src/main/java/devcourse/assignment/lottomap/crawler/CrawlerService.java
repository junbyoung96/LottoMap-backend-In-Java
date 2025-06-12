package devcourse.assignment.lottomap.crawler;

import com.fasterxml.jackson.databind.ObjectMapper;
import devcourse.assignment.lottomap.store.*;
import devcourse.assignment.lottomap.winninginfo.WinningInfoDto;
import devcourse.assignment.lottomap.winninginfo.WinningInfoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CrawlerService {

    private static final Logger log = LoggerFactory.getLogger(CrawlerService.class);

    @Value("${crawler.baseUrl}")
    private String baseUrl;

    private final WinningInfoService winningInfoService;

    private final StoreRepository storeRepository;

    @Transactional //도시단위로 트랜잭션 실시
    private void saveStoresForCity(List<Store> stores) {
        storeRepository.saveAll(stores);
    }

    public void saveAllStoreInfo() {
        String[] cities = {"서울", "경기", "부산", "대구", "인천", "대전", "울산", "강원", "충북", "충남", "광주", "전북", "전남", "경북", "경남", "제주", "세종"};
        for (String city : cities) {
            try {
                List<Store> stores = fetchStoresForCity(city);
                saveStoresForCity(stores);
            } catch (IOException e) {
                log.error("Error fetching stores for city: {}", city, e);
            }
        }
    }

    private List<Store> fetchStoresForCity(String city) throws IOException {
        List<Store> stores = new ArrayList<>();
        StoreListDto response = getStoreInfo(city, 1);
        int totalPage = response.getTotalPage();
        for (int i = 1; i <= totalPage; i++) {
            response = getStoreInfo(city, i);
            List<StoreDto> storeDtos = response.getArr();
            for (StoreDto storeDto : storeDtos) {
                Store store = mapToStore(storeDto);
                stores.add(store);
            }
        }
        return stores;
    }

    public StoreListDto getStoreInfo(String city, int page) throws IOException {
        Document doc = Jsoup.connect(baseUrl)
                .timeout(5 * 1000)
                .data("searchType", "1")
                .data("sltSIDO", city)
                .data("sltGUGUN", "")
                .data("method", "sellerInfo645Result")
                .data("rtlrSttus", "001")
                .data("nowPage", String.valueOf(page))
                .post();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(doc.body().text(), StoreListDto.class);
    }

    private Store mapToStore(StoreDto dto) {
        long id = Long.parseLong(dto.getRtlrId());
        String name = dto.getFirmNm();
        String address = dto.getBplcDoroDtlAdres();
        BigDecimal lat = new BigDecimal(dto.getLatitude());
        BigDecimal lon = new BigDecimal(dto.getLongitude());
        String phone = dto.getRtlrStrTelNo();
        return new Store(id, name, address, phone, lat, lon);
    }

    public boolean updateWinningInfo(int drawNo) {
        try {
            List<WinningInfoDto> winningInfos = fetchWinningInfoForDrawNo(drawNo);
            if (winningInfos.isEmpty()) {
                return false; // 데이터 없으면 종료
            }
            saveWinningInfoForDrawNo(drawNo, winningInfos);
            return true;
        } catch (IOException e) {
            log.error("Error updating winning info for drawNo: {}", drawNo, e);
            return false;
        }
    }

    private List<WinningInfoDto> fetchWinningInfoForDrawNo(int drawNo) throws IOException {
        List<WinningInfoDto> winningInfos = new ArrayList<>();

        Document doc = Jsoup.connect(baseUrl)
                .timeout(5 * 1000)
                .data("method", "topStore")
                .data("pageGubun", "L645")
                .data("drwNo", String.valueOf(drawNo))
                .post();

        Elements tables = doc.select(".tbl_data_col > tbody");
        if (tables.select(".nodata").isEmpty()) {
            Element firstTable = tables.getFirst();
            winningInfos.addAll(parseWinningInfoTable(firstTable, 1, drawNo));

            int lastPage = getLastPageForSecondRank(doc);
            for (int i = 1; i <= lastPage; i++) {
                doc = Jsoup.connect(baseUrl)
                        .timeout(5 * 1000)
                        .data("method", "topStore")
                        .data("pageGubun", "L645")
                        .data("nowPage", String.valueOf(i))
                        .data("drwNo", String.valueOf(drawNo))
                        .post();
                Element secondTable = doc.select(".tbl_data_col > tbody").getLast();
                winningInfos.addAll(parseWinningInfoTable(secondTable, 2, drawNo));
            }
        }
        return winningInfos;
    }

    private int getLastPageForSecondRank(Document doc) {
        Elements pages = doc.select("#page_box > a");
        Element lastPageLink = pages.getLast();
        String onclick = lastPageLink.attr("onclick");
        Matcher matcher = Pattern.compile("selfSubmit\\((\\d+)\\)").matcher(onclick);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
    }

    private List<WinningInfoDto> parseWinningInfoTable(Element table, int rank, int drawNo) {
        List<WinningInfoDto> winningInfos = new ArrayList<>();
        Elements rows = table.select("tr");
        for (Element row : rows) {
            Elements data = row.select("td");
            String category = (rank == 1) ? data.get(2).text() : null;
            Element link = data.getLast().select("a").first();
            if (link != null) {
                String onclick = link.attr("onclick");
                Matcher matcher = Pattern.compile("(\\d+)").matcher(onclick);
                if (matcher.find()) {
                    Long storeId = Long.parseLong(matcher.group(1));
                    winningInfos.add(new WinningInfoDto(drawNo, category, rank, storeId));
                }
            }
        }
        return winningInfos;
    }

    @Transactional
    private void saveWinningInfoForDrawNo(int drawNo, List<WinningInfoDto> winningInfos) {
        for (WinningInfoDto dto : winningInfos) {
            try {
                winningInfoService.saveWinningInfo(dto.getDrawNo(), dto.getCategory(),
                        dto.getRank(), dto.getStoreId());
            } catch (EntityNotFoundException e) {
                log.warn("Store with ID {} not found, skipping.", dto.getStoreId());
            }
        }
    }
}
