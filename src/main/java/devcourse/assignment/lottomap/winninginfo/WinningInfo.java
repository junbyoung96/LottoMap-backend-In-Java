package devcourse.assignment.lottomap.winninginfo;

import devcourse.assignment.lottomap.store.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "winning_infos")
public class WinningInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer drawNo;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Integer rank;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    public WinningInfo(Integer drawNo, String category, Integer rank, Store store) {
        this.drawNo = drawNo;
        this.category = category;
        this.rank = rank;
        this.store = store;
    }
}
