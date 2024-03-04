package backend.planawaypracticeV3.service;

import backend.planawaypracticeV3.domain.User;
import backend.planawaypracticeV3.dto.mail.NewPasswordDto;
import backend.planawaypracticeV3.dto.request.UserInfoRequest;
import backend.planawaypracticeV3.dto.request.SignupRequest;
import backend.planawaypracticeV3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public boolean save(SignupRequest signupRequest) {

        userRepository.save(User.builder()
                .username(signupRequest.getUsername())
                .userId(signupRequest.getUserId())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .phone(signupRequest.getPhone())
                .build());

        return true;
    }

    // 새 비밀번호 업데이트
    @Transactional
    public boolean updatePassword(NewPasswordDto newPasswordDto) {

        Optional<User> finduser = userRepository.findByEmail(newPasswordDto.getEmail());
        if(finduser.isEmpty()){
            return false;
        }
        User user = finduser.get();

        user.updatePassword(passwordEncoder.encode((newPasswordDto.getNewPassword())));

        return true;
    }

    // 프로필 수정
    @Transactional
    public boolean updateUserInfo(String userId, UserInfoRequest userInfoRequest){

        Optional<User> findUser = userRepository.findByUserId(userId);
        if(findUser.isEmpty()){
            return false;
        }

        User user = findUser.get();

        // 회원 정보 수정
        user.updateUserInfo(
                userInfoRequest.getUsername(),
                userInfoRequest.getUserId(),
                userInfoRequest.getEmail(),
                passwordEncoder.encode(userInfoRequest.getPassword()),
                userInfoRequest.getPhone());

        return true;
    }



}


