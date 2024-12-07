package com.kwakmunsu.vote.service.impl;

import com.kwakmunsu.vote.domain.User;
import com.kwakmunsu.vote.dto.AuthDto.SignUpRequest;
import com.kwakmunsu.vote.dto.AuthDto.UpdateRequest;
import com.kwakmunsu.vote.repository.UserRepository;
import com.kwakmunsu.vote.service.AuthService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    @Transactional
    @Override
    public void signup(SignUpRequest signUpRequestDto) {
        // 중복 체크
        String username = signUpRequestDto.getUsername();
        String nickname = signUpRequestDto.getNickname();
        userRepository.findByUsername(username)
                        .ifPresent(user -> {throw new DuplicateRequestException("해당 id가 존재합니다."); });
        userRepository.findByNickname(nickname)
                .ifPresent(user -> {throw new DuplicateRequestException("해당 닉네임이 존재합니다."); });

        User user = signUpRequestDto.toEntity();

        userRepository.save(user);
    }

    @Override
    public void updatePassword(UpdateRequest updateRequestDto) {

    }
}
