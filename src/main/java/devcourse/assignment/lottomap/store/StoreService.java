package devcourse.assignment.lottomap.store;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public Store getStoreById(long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Store with ID " + id + " not found"));
    }

    public List<Store> getNearByStores(BoundingBoxDto dto) {
        return storeRepository.findStoresInBounds(
                dto.getSouthWestLat(),
                dto.getNorthEastLat(),
                dto.getSouthWestLon(),
                dto.getNorthEastLon()
        );
    }
    public List<StoreResponseDto> convert(List<Store> stores) {
        List<StoreResponseDto> list = new ArrayList<>();
        for (Store store : stores) {
            list.add(new StoreResponseDto(store));
        }
        return list;
    }
}
