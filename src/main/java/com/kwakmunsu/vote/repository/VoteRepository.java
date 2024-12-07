package com.kwakmunsu.vote.repository;

import com.kwakmunsu.vote.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Long, Vote> {

}
