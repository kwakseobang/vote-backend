package com.kwakmunsu.vote.jwt;

import com.kwakmunsu.vote.domain.User;
import com.kwakmunsu.vote.dto.CustomUserDetails;
import com.kwakmunsu.vote.repository.UserRepository;
import com.kwakmunsu.vote.response.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException.UserNotFound(username));

        return new CustomUserDetails(user);

    }
}
