package com.mwj.personweb.service.serviceimpl;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: 母哥 @Date: 2019-02-28 15:28 @Version 1.0
 */
public class Md5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence);
    }
}
