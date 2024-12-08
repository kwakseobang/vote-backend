package com.kwakmunsu.vote.controller;


import com.kwakmunsu.vote.dto.AuthDto;
import com.kwakmunsu.vote.response.ResponseCode;
import com.kwakmunsu.vote.response.ResponseData;
import com.kwakmunsu.vote.service.AuthService;
import com.kwakmunsu.vote.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<ResponseData> signup(@RequestBody AuthDto.SignUpRequest signUpRequestDto) {
        authService.signup(signUpRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_USER);


    }

}
