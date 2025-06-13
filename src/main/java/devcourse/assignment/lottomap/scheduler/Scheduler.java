package devcourse.assignment.lottomap.scheduler;

import devcourse.assignment.lottomap.crawler.CrawlerService;
import devcourse.assignment.lottomap.winninginfo.WinningInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final JdbcTemplate jdbcTemplate;

    private final WinningInfoService winningInfoService;

    private final CrawlerService crawlerService;

    /**
     * 가장 최근 회차의 당첨내역을 추가합니다.
     * 매주 일요일 02시에 실행됩니다.
     */
    @Scheduled(cron = "0 0 2 * * 0")
    public void addLatestWinningInfo() {
        while (true) {
            int maxDrawNo = winningInfoService.getMaxDrawNo();
            if (!crawlerService.updateWinningInfo(maxDrawNo)) { //저장할 회차가 없을때 까지 저장.
                break;
            }
        }
        //저장 후 물리 뷰 갱신.
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW store_rankings");
    }
}
