package devcourse.assignment.lottomap.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface StoreRankingRepository extends JpaRepository<StoreRanking,Long> {
    @Query(value = "SELECT s.* FROM store_rankings s WHERE s.lat BETWEEN :minLat AND :maxLat AND s.lon BETWEEN :minLon AND :maxLon LIMIT 20", nativeQuery = true)
    List<StoreRanking> findStoresInBounds(
            @Param("minLat") BigDecimal minLat,
            @Param("maxLat") BigDecimal maxLat,
            @Param("minLon") BigDecimal minLon,
            @Param("maxLon") BigDecimal maxLon);
}
