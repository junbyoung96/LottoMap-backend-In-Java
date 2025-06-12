package devcourse.assignment.lottomap.store;

import devcourse.assignment.lottomap.winninginfo.WinningInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "stores")
public class Store {
    @Id
    private Long id;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String address;

    @Column(length = 30)
    private String phone;

    @Column(precision = 10, scale = 6, nullable = false)
    private BigDecimal lat;

    @Column(precision = 10, scale = 6, nullable = false)
    private BigDecimal lon;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<WinningInfo> winningInfos = new ArrayList<>();

    public Store(Long id, String name, String address, String phone, BigDecimal lat, BigDecimal lon) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.lat = lat;
        this.lon = lon;
    }
}
