package com.kwakmunsu.vote.repository;

import com.kwakmunsu.vote.domain.User;
import com.kwakmunsu.vote.response.exception.UserException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByNickname(String nickname);
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);

    // user_id로 RT 찾기
    @Query("SELECT u.refreshToken FROM User u WHERE u.id = :userId")
    String findRefreshTokenById(@Param("userId") Long userId);

    @Transactional(rollbackOn = UserException.TokenBadRequest.class) // 이 작업이 실패하거나 예외가 발생했을 때 롤백을 보장하려면 **@Transactional**이 필수이다. @Modifying이 수정 작업이기떄문에 해주워야함
    @Modifying // JPA의 @Query는 읽기 전용으로 설계되어있음. 해당 어노테이션으로 데이터 변경 쿼리 실행 가능하게 해줌.또한 기본적으로 트랜잭션 내에서 실행
    @Query("UPDATE User u SET u.refreshToken = null WHERE u.id = :userId")
    void deleteRefreshTokenById(@Param("userId") Long userId);

}
