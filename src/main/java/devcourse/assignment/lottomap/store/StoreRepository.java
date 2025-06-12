package devcourse.assignment.lottomap.store;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store,Long> {
    @Query("SELECT s FROM Store s WHERE s.lat BETWEEN :minLat AND :maxLat AND s.lon BETWEEN :minLon AND :maxLon")
    List<Store> findStoresInBounds(
            @Param("minLat") BigDecimal minLat,
            @Param("maxLat") BigDecimal maxLat,
            @Param("minLon") BigDecimal minLon,
            @Param("maxLon") BigDecimal maxLon
    );

    @EntityGraph(attributePaths = {"winningInfos", "winningInfos.store"})
    Optional<Store> findById(long id);
}
