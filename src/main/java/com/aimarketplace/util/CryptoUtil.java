package com.aimarketplace.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class CryptoUtil {


    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();


    public String encrypt(String value) {

        return encoder.encode(value);

    }


    public boolean matches(String rawValue, String encodedValue) {

        return encoder.matches(rawValue, encodedValue);

    }

}