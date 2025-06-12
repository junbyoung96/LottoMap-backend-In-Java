package devcourse.assignment.lottomap.winninginfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WinningInfoDto {
    private int drawNo;
    private String category;
    private int rank;
    private Long storeId;

    public WinningInfoDto(int drawNo, String category, int rank, Long storeId) {
        this.drawNo = drawNo;
        this.category = category;
        this.rank = rank;
        this.storeId = storeId;
    }
}