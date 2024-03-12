package backend.planawaypracticeV3.repository;

import backend.planawaypracticeV3.domain.TravelItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelItemRepository extends JpaRepository<TravelItem, Long> {

    List<TravelItem> findByPlanId(Long planId);
}
