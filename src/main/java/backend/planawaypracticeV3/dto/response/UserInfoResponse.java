package backend.planawaypracticeV3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserInfoResponse {

    private Long id;
    private String username;
    private String email;
}
