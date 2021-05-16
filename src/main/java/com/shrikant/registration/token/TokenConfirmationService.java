package com.shrikant.registration.token;

import com.shrikant.entity.User;
import com.shrikant.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TokenConfirmationService {

    private final ConfirmationTokenService tokenService;
    private final UserRepository userRepository;

    public String confirmToken(String token){
        ConfirmationToken confirmationToken =
                tokenService.getToken(token).orElseThrow(
                        () -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("token already confirmed");
        }

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }

        tokenService.confirmToken(token);
        User user = confirmationToken.getUser();
        user.toString();
        userRepository.enableUser(user.getEmail());
        return "confirmed";
    }
}
