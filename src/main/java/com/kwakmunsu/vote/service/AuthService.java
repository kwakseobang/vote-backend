package com.kwakmunsu.vote.service;

import com.kwakmunsu.vote.dto.AuthDto;

public interface AuthService {
    void signup(AuthDto.SignUpRequest signUpRequestDto );
    void updatePassword(AuthDto.UpdateRequest updateRequestDto);
}
