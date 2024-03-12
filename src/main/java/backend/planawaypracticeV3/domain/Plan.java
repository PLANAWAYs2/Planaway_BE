package backend.planawaypracticeV3.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planId", updatable = false)
    private Long id;

    private String planTitle;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    // 여행 계획과 사용자 간의 관계 설정
    @ManyToOne
    @JoinColumn(name = "plan-userid")
    private User planuser;

    @OneToOne(mappedBy = "plan", cascade = CascadeType.ALL)
    private TravelItem travelItem;

}
