package com.kwakmunsu.vote.controller;


import com.kwakmunsu.vote.dto.AuthDto;
import com.kwakmunsu.vote.response.ResponseCode;
import com.kwakmunsu.vote.response.ResponseData;
import com.kwakmunsu.vote.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
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

    @PostMapping("/login")
    @Operation(summary = "로그인 [JWT X]")
    public ResponseEntity<ResponseData<AuthDto.TokenResponse>> login(
            @RequestBody AuthDto.LoginRequest loginRequestDto,
            HttpServletResponse response
    ) {
        AuthDto.TokenResponse tokenResponseDto = authService.login(loginRequestDto,response);
        return ResponseData.toResponseEntity(ResponseCode.LOGIN_SUCCESS, tokenResponseDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<ResponseData<AuthDto.TokenResponse>> reissue(
            @RequestBody AuthDto.ReissueRequest reissueRequestDto,
            HttpServletRequest request
    ) {

        AuthDto.TokenResponse tokenResponseDto =  authService.reissue(reissueRequestDto,request);
        return ResponseData.toResponseEntity(ResponseCode.REISSUE_SUCCESS, tokenResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseData> logout(HttpServletRequest request,HttpServletResponse response) {
        log.info("logout..");
         authService.logout(request,response);
        return ResponseData.toResponseEntity(ResponseCode.LOGOUT_SUCCESS);
    }

}
