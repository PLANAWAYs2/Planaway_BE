package backend.planawaypracticeV3.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String username;

    @Column(nullable = false, unique = true)
    private String userId;

    private String email;

    private String password;

    private String phone;

    @Builder
    public User(String username, String userId, String email, String password, String phone) {
        this.username = username;
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
