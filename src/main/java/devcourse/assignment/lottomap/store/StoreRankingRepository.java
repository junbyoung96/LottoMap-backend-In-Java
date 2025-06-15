package devcourse.assignment.lottomap.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface StoreRankingRepository extends JpaRepository<StoreRanking, Long> {
    @Query(value = "SELECT * " +
            "FROM store_rankings " +
            "WHERE earth_box(ll_to_earth(:userLat,:userLon),:radius) @> ll_to_earth(lat,lon) " +
            "ORDER BY score DESC " +
            "LIMIT 30", nativeQuery = true)
    List<StoreRanking> findNearestStores(
            @Param("userLat") BigDecimal userLat,
            @Param("userLon") BigDecimal userLon,
            @Param("radius") Integer radius);

    @Query(value = "SELECT s.* " +
            "FROM store_rankings s " +
            "WHERE s.lat BETWEEN :minLat AND :maxLat AND s.lon BETWEEN :minLon AND :maxLon ORDER BY s.score DESC LIMIT 20 ", nativeQuery = true)
    List<StoreRanking> findStoresInBounds(
            @Param("minLat") BigDecimal minLat,
            @Param("maxLat") BigDecimal maxLat,
            @Param("minLon") BigDecimal minLon,
            @Param("maxLon") BigDecimal maxLon);
}
