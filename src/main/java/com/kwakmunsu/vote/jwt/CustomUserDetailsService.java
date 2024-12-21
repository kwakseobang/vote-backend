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

/*
 인증 과정에서 AuthenticationFilter에서 생성된 UsernamePasswordAuthenticationToken을 AuthenticationManager에게 전달함
AuthenticationManager는 실제로 인증을 할 AuthenticationProvider 선정
AuthenticationProvider는 UserDetailsService를 통해 인증 진행함 지금 그 과정.
loadUserByUsername() 메서드를 호출하여 DB에 있는 사용자 정보를 UserDetails 형태로 가져온다.( 여기선 CustomUserDetails)
그 후 다시 전달함.

 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
//        Spring MVC의 @ControllerAdvice나 @ExceptionHandler에서 잡을 수 없다 그래서 500이 던져졌음.
// 해당 예외는 Spring Security 필터 체인 내부에서 발생하여서 AuthenticationEntryPoint로 예외를 잡음.

        return new CustomUserDetails(user);

    }
}
