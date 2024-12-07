package com.kwakmunsu.vote.repository;

import com.kwakmunsu.vote.domain.UserVotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVotesRepository extends JpaRepository<Long, UserVotes> {

}
