package devcourse.assignment.lottomap.winninginfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WinningInfoRepository extends JpaRepository<WinningInfo,Long> {
    @Query("SELECT COALESCE(MAX(drawNo),1) FROM WinningInfo")
    public int getMaxDrawNo();
}
