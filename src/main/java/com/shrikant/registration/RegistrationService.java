package com.shrikant.registration;

import com.shrikant.email.EmailService;
import com.shrikant.entity.User;
import com.shrikant.exceptions.UserAlreadyExists;
import com.shrikant.registration.token.ConfirmationToken;
import com.shrikant.registration.token.ConfirmationTokenService;
import com.shrikant.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    @Autowired
    private final PasswordEncoder bCryptPasswordEncoder;

    public String signup(RegistrationDto registrationDto){

        User u = userRepository.findByEmail(registrationDto.getEmail());

        if(u != null){
            throw new UserAlreadyExists();
        } else {
            User user = new User(
                    registrationDto.getFirstName(),
                    registrationDto.getLastName(),
                    registrationDto.getEmail(),
                    bCryptPasswordEncoder.encode(registrationDto.getPassword()),
                    registrationDto.getAge(),
                    "read"
            );
            userRepository.save(user);

            String token = UUID.randomUUID().toString();

            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    user
            );

            confirmationTokenService.saveConfirmationToken(confirmationToken);
            emailService.sendEmail(user.getEmail(),token);
            return token;
        }
    }
}
