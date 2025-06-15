package devcourse.assignment.lottomap.store;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    private final StoreRankingRepository storeRankingRepository;

    public Store getStoreById(long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Store with ID " + id + " not found"));
    }

    public List<StoreRanking> getNearByStores(BigDecimal lat, BigDecimal lon) {
        return storeRankingRepository.findNearestStores(lat, lon, 3000);
    }
}
