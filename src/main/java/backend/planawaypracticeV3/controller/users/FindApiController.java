package backend.planawaypracticeV3.controller.users;

import backend.planawaypracticeV3.dto.mail.EmailDto;
import backend.planawaypracticeV3.dto.mail.NewPasswordDto;
import backend.planawaypracticeV3.dto.response.MessageResponse;
import backend.planawaypracticeV3.service.EmailService;
import backend.planawaypracticeV3.service.SignupService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class FindApiController {

    private final EmailService emailService;
    private final SignupService signupService;

    private Map<String, String> emailAuth = new HashMap<>();


    // 아이디 찾기
    // 이메일로 아이디 전송
    // [이메일 전송하기] 버튼 눌렀을 때 입력한 이메일로 아이디 전송
    @PostMapping("/findid/sendemail")
    public ResponseEntity<?> sendIdToEmail(@RequestParam(name = "email") String email) throws Exception {
        String findUserId = emailService.findLostId(email);

        if(findUserId.isBlank()){
            return ResponseEntity.badRequest().body(new MessageResponse("잘못된 이메일입니다."));
        }

        EmailDto emailDto = new EmailDto();

        emailDto.setEmailRecipient(email);
        emailDto.setEmailTitle(email + "님의 아이디");
        emailDto.setEmailContent("아이디는 [ " + findUserId + " ] 입니다. 다시 로그인 시도해주세요.");

        emailService.sendMailFindId(emailDto);

        return ResponseEntity.ok(new MessageResponse("입력한 이메일로 아이디가 전송되었습니다."));
    }

    // 비밀번호 찾기
    // 이메일로 인증번호 전송 -> 인증번호가 맞으면 새 비밀번호로 변경
    // (1) [이메일 전송하기] 버튼 눌렀을 때 -> 입력한 이메일로 인증번호 전송
    @PostMapping("/findpw/sendemail")
    public ResponseEntity<?> sendAuthNumToEmail(@RequestParam(name = "email") String email) throws Exception{

        if(!emailService.existEmail(email)){
            return ResponseEntity.badRequest().body(new MessageResponse("잘못된 이메일입니다."));
        }

        // 인증번호 생성
        String authNum = emailService.createCode();
        emailAuth.put(email, authNum);

        EmailDto emailDto = new EmailDto();

        emailDto.setEmailRecipient(email);
        emailDto.setEmailTitle(email + "님의 비밀번호 찾기 인증번호");
        emailDto.setEmailContent("인증번호는 [ " + authNum + " ] 입니다. 인증번호 입력 후 인증번호 확인을 눌러주세요.");

        emailService.sendMailFindId(emailDto);  // 인증번호 메일로 전송

        return ResponseEntity.ok(new MessageResponse("입력한 이메일로 인증번호가 전송되었습니다."));
    }

    // (2) [인증번호 확인] 버튼을 눌렀을 때 -> 인증번호 확인
    @PostMapping("/findpw/check")
    public ResponseEntity<?> checkAuthNum(@RequestParam(name = "email") String email,
                                          @RequestParam(name = "auth") String auth){
        String trueAuth = emailAuth.get(email);
        emailAuth.remove(email);

        if(trueAuth.equals(auth)){
            return ResponseEntity.ok(new MessageResponse("인증이 완료되었습니다. 새 비밀번호를 입력하세요."));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("잘못된 인증번호입니다."));
    }

    // (3) 새 비밀번호 설정
    @PostMapping("/findpw/newpw")
    public ResponseEntity<?> newPassword(@RequestBody NewPasswordDto newPasswordDto){

        if(!(newPasswordDto.getNewPassword().equals(newPasswordDto.getNewConfirmPassword()))){
            return ResponseEntity.badRequest().body(new MessageResponse("비밀번호가 일치하지 않습니다."));
        }

        boolean success = signupService.updatePassword(newPasswordDto);

        if(!success){
            return ResponseEntity.badRequest().body(new MessageResponse("비밀번호 변경에 실패했습니다."));
        }

        return ResponseEntity.ok(new MessageResponse("비밀번호가 변경되었습니다."));
    }

}
