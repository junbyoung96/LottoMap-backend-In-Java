package devcourse.assignment.lottomap.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface StoreRankingRepository extends JpaRepository<StoreRanking, Long> {
    @Query(value = "SELECT id, name, phone, address, lat, lon, score, " +
            "earth_distance(ll_to_earth(lat, lon), ll_to_earth(:userLat, :userLon)) AS distance " +
            "FROM store_rankings " +
            "ORDER BY distance ASC " +
            "LIMIT 10", nativeQuery = true)
    List<StoreRanking> findNearestStores(
            @Param("userLat") BigDecimal userLat,
            @Param("userLon") BigDecimal userLon);
}
