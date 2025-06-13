package devcourse.assignment.lottomap.store;

import devcourse.assignment.lottomap.crawler.CrawlerService;
import devcourse.assignment.lottomap.scheduler.Scheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<List<StoreRanking>> getNearByStores(BoundingBoxDto dto) {
        List<StoreRanking> stores = storeService.getNearByStores(dto);
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long storeId) {
        Store store = storeService.getStoreById(storeId);
        return ResponseEntity.ok(store);
    }
}