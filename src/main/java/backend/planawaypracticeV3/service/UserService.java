package backend.planawaypracticeV3.service;

import backend.planawaypracticeV3.domain.Plan;
import backend.planawaypracticeV3.domain.TravelItem;
import backend.planawaypracticeV3.domain.User;
import backend.planawaypracticeV3.dto.mail.NewPasswordDto;
import backend.planawaypracticeV3.dto.request.UserInfoRequest;
import backend.planawaypracticeV3.dto.request.SignupRequest;
import backend.planawaypracticeV3.repository.PlanRepository;
import backend.planawaypracticeV3.repository.TravelItemRepository;
import backend.planawaypracticeV3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TravelItemRepository travelItemRepository;
    private final PlanRepository planRepository;

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

    // 여행 준비물 조회
    public List<TravelItem> getTravelItemsByPlanId(Long planId) {
        // 여행 계획의 준비물 리스트를 조회하는 비즈니스 로직을 구현
        return travelItemRepository.findByPlanId(planId);
    }

    // 준비물 추가
    public TravelItem addTravelItem(Long planId, String addItem) {
        Optional<Plan> planOptional = planRepository.findById(planId);
        if (planOptional.isPresent()) {
            Plan plan = planOptional.get();
            TravelItem travelItem = new TravelItem(addItem, false, plan);

            return travelItemRepository.save(travelItem);
        } else {
            throw new RuntimeException("Plan with ID " + planId + " not found");
        }
    }

    // 준비물 수정
    public TravelItem updateTravelItemName(Long itemId, String newName) {
        TravelItem existingItem = travelItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("TravelItem with ID " + itemId + " not found"));

        existingItem.setItemName(newName);

        return travelItemRepository.save(existingItem);
    }

    // 준비물 삭제
    public void deleteTravelItem(Long itemId) {
        travelItemRepository.deleteById(itemId);
    }


}


