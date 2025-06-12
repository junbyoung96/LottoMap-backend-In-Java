package devcourse.assignment.lottomap.store;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BoundingBoxDto {
    private BigDecimal northEastLat;
    private BigDecimal northEastLon;
    private BigDecimal southWestLat;
    private BigDecimal southWestLon;
}