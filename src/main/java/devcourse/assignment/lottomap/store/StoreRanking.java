package devcourse.assignment.lottomap.store;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

import java.math.BigDecimal;

@Getter
@Entity
@Immutable
@Table(name = "store_rankings")
public class StoreRanking {
    @Id
    private Long id;

    private String name;
    private String phone;
    private String address;
    private BigDecimal lat;
    private BigDecimal lon;
    private Long firstPrize;
    private Long secondPrize;
    private Long score;
}