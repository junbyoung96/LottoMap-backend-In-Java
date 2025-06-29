package devcourse.assignment.lottomap.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreListDto {
    private List<StoreDto> arr;
    private int totalPage;
}