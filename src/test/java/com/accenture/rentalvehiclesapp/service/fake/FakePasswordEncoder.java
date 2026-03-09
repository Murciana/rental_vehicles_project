package com.accenture.rentalvehiclesapp.service.fake;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

public class FakePasswordEncoder implements PasswordEncoder {

    @Override
    public @Nullable String encode(@Nullable CharSequence rawPassword) {
        return "";
    }

    @Override
    public boolean matches(@Nullable CharSequence rawPassword, @Nullable String encodedPassword) {
        return false;
    }

    @Override
    public boolean upgradeEncoding(@Nullable String encodedPassword) {
        return PasswordEncoder.super.upgradeEncoding(encodedPassword);
    }
}
