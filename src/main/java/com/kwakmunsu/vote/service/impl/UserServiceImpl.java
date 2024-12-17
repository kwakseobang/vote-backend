package com.kwakmunsu.vote.service.impl;

import com.kwakmunsu.vote.domain.User;
import com.kwakmunsu.vote.repository.UserRepository;
import com.kwakmunsu.vote.response.exception.UserException;
import com.kwakmunsu.vote.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserException.UserNotFound(String.format("userId = %d", userId)));
    }
}
