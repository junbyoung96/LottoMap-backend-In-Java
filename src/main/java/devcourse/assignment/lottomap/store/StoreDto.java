package devcourse.assignment.lottomap.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreDto {
    @JsonProperty("RTLRID")
    private String rtlrId;

    @JsonProperty("FIRMNM")
    private String firmNm;

    @JsonProperty("BPLCDORODTLADRES")
    private String bplcDoroDtlAdres;

    @JsonProperty("LATITUDE")
    private String latitude;

    @JsonProperty("LONGITUDE")
    private String longitude;

    @JsonProperty("RTLRSTRTELNO")
    private String rtlrStrTelNo;
}

