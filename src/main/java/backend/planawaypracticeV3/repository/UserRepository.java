package backend.planawaypracticeV3.repository;

import backend.planawaypracticeV3.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // userId로 사용자 정보 가져옴
    Optional<User> findByUserId(String userId);
    Optional<User> findByEmail(String email);
    Boolean existsByUserId(String userId);
    Boolean existsByEmail(String email);
}
