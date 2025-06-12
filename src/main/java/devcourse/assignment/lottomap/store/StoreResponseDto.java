package devcourse.assignment.lottomap.store;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class StoreResponseDto {
    private Long id;
    private String name;
    private BigDecimal lat;
    private BigDecimal lon;

    public StoreResponseDto(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.lat = store.getLat();
        this.lon = store.getLon();
    }
}