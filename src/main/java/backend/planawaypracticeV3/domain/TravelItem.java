package backend.planawaypracticeV3.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "travelItem")
public class TravelItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    private boolean completed;

    public TravelItem(String itemName) {
        this.itemName = itemName;
    }

    public TravelItem(String itemName, boolean completed, Plan plan) {
        this.itemName = itemName;
        this.completed = completed;
        this.plan = plan;
    }

    @OneToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;
}
