package backend.planawaypracticeV3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
public class MessageResponse {
    private String message;

    public MessageResponse(ArrayList<String> listMessage){

    }
}
