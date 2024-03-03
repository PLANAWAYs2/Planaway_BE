package backend.planawaypracticeV3.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// 로그인
@AllArgsConstructor
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

    private Long id;

    private String siteUsername;

    private String userId;

    private String email;

    @JsonIgnore
    private String password;

    private String phone;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    // 유저 아이디 반환
    // getUsername() -> userId 반환
    // getUserId() -> userId 반환
    // getSiteUsername() -> siteUsername 반환
    @Override
    public String getUsername() {
        return userId;
    }

    public static UserDetailsImpl build(User user) {

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
