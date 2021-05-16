package com.shrikant.registration;

import lombok.Getter;

@Getter
public class RegistrationDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int age;
}
