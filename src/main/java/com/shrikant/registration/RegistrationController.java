package com.shrikant.registration;

import com.shrikant.registration.token.TokenConfirmationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class RegistrationController {

    private final TokenConfirmationService tokenConfirmationService;
    private final RegistrationService registrationService;

    @PostMapping("/signup")
    public String register(@RequestBody RegistrationDto registrationDto){
        return registrationService.signup(registrationDto);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token){
        return tokenConfirmationService.confirmToken(token);
    }
}
