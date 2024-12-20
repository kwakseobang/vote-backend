package com.kwakmunsu.vote.service;

import com.kwakmunsu.vote.dto.AuthDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    void signup(AuthDto.SignUpRequest signUpRequestDto );
    AuthDto.TokenResponse login(AuthDto.LoginRequest loginRequestDto,HttpServletResponse response);
    void updatePassword(AuthDto.UpdateRequest updateRequestDto);

    // Refresh토큰으로 access 토큰 재발급.
    AuthDto.TokenResponse reissue(AuthDto.ReissueRequest reissueRequestDto, HttpServletRequest request);
    void logout(HttpServletRequest request,HttpServletResponse response);


}
