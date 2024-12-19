package com.kwakmunsu.vote.repository;

import com.kwakmunsu.vote.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByNickname(String nickname);

    // user_id로 RT 찾기
    String findRefreshTokenById(Long userId);



}
