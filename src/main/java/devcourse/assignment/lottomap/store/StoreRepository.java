package devcourse.assignment.lottomap.store;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @EntityGraph(attributePaths = {"winningInfos", "winningInfos.store"})
    Optional<Store> findById(long id);
}
