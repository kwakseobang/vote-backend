package com.kwakmunsu.vote.repository;

import com.kwakmunsu.vote.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Long, User> {

}
