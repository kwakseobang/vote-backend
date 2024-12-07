package com.kwakmunsu.vote.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "UserVotes")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user-votes")
public class UserVotesController {

}
