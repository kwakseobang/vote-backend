package com.kwakmunsu.vote.jwt;

import com.kwakmunsu.vote.domain.User;
import com.kwakmunsu.vote.repository.UserRepository;
import com.kwakmunsu.vote.response.exception.UserException;
import com.kwakmunsu.vote.response.exception.UserException.UserNotFound;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 필터에서 가로채면 해당 클래스를 통해서 유저 정보를 확인한다.
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return  userRepository.findByUsername(username)
               .map(this::createUserDetails)
               .orElseThrow(() -> new UserException.UserNotFound( String.format("username = %s", username) )
               );


    }
    private UserDetails createUserDetails(User user) {
        // 로그인아이디를 이용하여 User을 찾고, 찾은 User의 '사용자DB의 PKid, 비밀번호, 권한'을 가지고 UserDetails 객체를 생성한다.

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuthority().toString());

        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getId()),  // 로그인Email 대신 User_PK id를 String 타입이여서 변환 후 저장.
                user.getPassword(),
                Collections.singleton(grantedAuthority)
        );

        // 보안상의 이유로 id값을 넣어줌. 노출되지 않게.
        // 사용자가 이메일 주소를 변경하면, 그에 맞춰 로그인 시스템도 업데이트해야 하지만, 고유 ID는 변경되지 않기 때문에
        // 장기적으로 더 안정적임
    }
}
