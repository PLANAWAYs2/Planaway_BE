package backend.planawaypracticeV3.controller;

import backend.planawaypracticeV3.config.jwt.JwtTokenUtils;
import backend.planawaypracticeV3.domain.User;
import backend.planawaypracticeV3.domain.UserDetailsImpl;
import backend.planawaypracticeV3.dto.request.LoginRequest;
import backend.planawaypracticeV3.dto.request.SignupRequest;
import backend.planawaypracticeV3.dto.response.MessageResponse;
import backend.planawaypracticeV3.dto.response.UserInfoResponse;
import backend.planawaypracticeV3.repository.UserRepository;
import backend.planawaypracticeV3.service.SignupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {



    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtils jwtTokenUtils;

    private final SignupService signupService;



    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody SignupRequest signupRequest){

        // 비밀번호 일치 확인
        if(!((signupRequest.getPassword()).equals(signupRequest.getConfirmPassword()))){
            return ResponseEntity.badRequest().body(new MessageResponse("비밀번호가 일치하지 않습니다."));
        }

        // 회원 저장
        boolean success = signupService.save(signupRequest);

        if(success){
            return ResponseEntity.ok(new MessageResponse("회원가입이 성공적으로 완료되었습니다."));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Signup failed");
    }

    // 아이디 중복 체크
    @GetMapping("/namecheck")
    public ResponseEntity<?> checkUsername(@RequestParam(value = "userId") String userId) {
        boolean isUserIdExists = userRepository.existsByUserId(userId);
        if (isUserIdExists) {
            return ResponseEntity.badRequest().body(new MessageResponse("이미 사용 중인 아이디입니다."));
        } else {
            return ResponseEntity.ok(new MessageResponse("사용 가능한 아이디입니다."));
        }
    }

    // 이메일 중복 체크
    @GetMapping("/emailcheck")
    public ResponseEntity<?> checkEmail(@RequestParam(value = "email") String email) {
        boolean isEmailExists = userRepository.existsByEmail(email);
        if (isEmailExists) {
            return ResponseEntity.badRequest().body(new MessageResponse("이미 사용 중인 이메일입니다."));
        } else {
            return ResponseEntity.ok(new MessageResponse("사용 가능한 이메일입니다."));
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtTokenUtils.generateJwtCookie(userDetails);


        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUserId(),
                        userDetails.getEmail()));
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {

        ResponseCookie cookie = jwtTokenUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }


}
