package com.kwitterbackend.user_service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthUtil implements PasswordEncoder {

    @Override
    public String encode(final CharSequence charSequence) {
        return new BCryptPasswordEncoder().encode(charSequence);
    }

    @Override
    public boolean matches(final CharSequence charSequence, String s) {
        return new BCryptPasswordEncoder().matches(charSequence, s);
    }
}
