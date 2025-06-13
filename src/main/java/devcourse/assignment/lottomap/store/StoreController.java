package devcourse.assignment.lottomap.store;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
@Validated
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<List<StoreRanking>> getNearByStores(
            @RequestParam("lat") @NotNull(message = "위도는 필수입니다.")
            @Min(value = -90, message = "위도는 -90 이상이어야 합니다.")
            @Max(value = 90, message = "위도는 90 이하여야 합니다.") BigDecimal lat,
            @RequestParam("lon") @NotNull(message = "경도는 필수입니다.")
            @Min(value = -180, message = "경도는 -180 이상이어야 합니다.")
            @Max(value = 180, message = "경도는 180 이하여야 합니다.") BigDecimal lon) {
        List<StoreRanking> stores = storeService.getNearByStores(lat, lon);
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long storeId) {
        Store store = storeService.getStoreById(storeId);
        return ResponseEntity.ok(store);
    }
}