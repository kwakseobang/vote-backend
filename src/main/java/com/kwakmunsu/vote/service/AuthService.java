package com.kwakmunsu.vote.service;

import com.kwakmunsu.vote.dto.AuthDto;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    void signup(AuthDto.SignUpRequest signUpRequestDto );
    AuthDto.TokenResponse login(AuthDto.LoginRequest loginRequestDto,HttpServletResponse response);
    void updatePassword(AuthDto.UpdateRequest updateRequestDto);
}
