package devcourse.assignment.lottomap.store;

import devcourse.assignment.lottomap.crawler.CrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    private final CrawlerService crawlerService;

    @GetMapping
    public ResponseEntity<List<StoreResponseDto>> getNearByStores(BoundingBoxDto dto) {
        List<Store> stores = storeService.getNearByStores(dto);
        List<StoreResponseDto> list = storeService.convert(stores);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long storeId) {
        Store store = storeService.getStoreById(storeId);
        return ResponseEntity.ok(store);
    }
}