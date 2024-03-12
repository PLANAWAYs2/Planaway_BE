package backend.planawaypracticeV3.repository;

import backend.planawaypracticeV3.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
