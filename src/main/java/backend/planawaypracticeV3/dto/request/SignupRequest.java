package backend.planawaypracticeV3.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    private String username;
    private String userId;
    private String email;
    private String password;
    private String confirmPassword;
    private String phone;

}
