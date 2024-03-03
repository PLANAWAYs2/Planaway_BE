package backend.planawaypracticeV3.service;

import backend.planawaypracticeV3.domain.User;
import backend.planawaypracticeV3.dto.mail.EmailDto;
import backend.planawaypracticeV3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    private final UserRepository userRepository;


    public String findLostId(String email) throws Exception {

        Boolean isExistedEmail = userRepository.existsByEmail(email);

        if(isExistedEmail){
            Optional<User> byEmail = userRepository.findByEmail(email);
            User user = byEmail.get();

            return user.getUserId();
        }

        return null;
    }

    public String createCode() throws NoSuchAlgorithmException {
        int length = 6;

        Random random = SecureRandom.getInstanceStrong();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }

    public boolean existEmail(String email){

        return userRepository.existsByEmail(email);
    }


    public void sendMailFindId(EmailDto emailDto) throws Exception{

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("planaway.ewha@gmail.com");
        simpleMailMessage.setTo(emailDto.getEmailRecipient());
        simpleMailMessage.setSubject("[PLANAWAY] " + emailDto.getEmailTitle());
        simpleMailMessage.setText(emailDto.getEmailContent());

        javaMailSender.send(simpleMailMessage);

    }

}
