package backend.planawaypracticeV3.dto.mail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPasswordDto {
    private String email;
    private String newPassword;
    private String newConfirmPassword;
}
