package net.blog.springbootrestapi.service;

import net.blog.springbootrestapi.payload.LoginDto;
import net.blog.springbootrestapi.payload.SignUpDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(SignUpDto signUpDto);
}
