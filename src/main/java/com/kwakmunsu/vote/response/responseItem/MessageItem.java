package com.kwakmunsu.vote.response.responseItem;

public class MessageItem {

        // < User - Success>
        public static final String CREATED_USER = "SUCCESS - 회원 가입 성공";
        public static final String READ_USER = "SUCCESS - 회원 정보 조회 성공";
        public static final String UPDATE_USER = "SUCCESS - 회원 정보 수정 성공";
        public static final String DELETE_USER = "SUCCESS - 회원 탈퇴 성공";

        // < User -> Error >
        public static final String NOT_FOUND_USER = "ERROR - 회원을 찾을 수 없습니다.";
        public static final String BAD_REQUEST_USER = "ERROR - 잘못된 회원 요청 에러";
        public static final String DUPLICATE_ID = "ERROR - 회원가입 ID 중복 에러";
        public static final String DUPLICATE_NICKNAME = "ERROR - 회원가입 닉네임 중복 에러";

        // < Memo - Success>
        public static final String CREATED_VOTE = "SUCCESS - 투표 생성 성공";
        public static final String READ_VOTE = "SUCCESS - 투표 정보 조회 성공";
        public static final String READ_VOTES = "SUCCESS - 회원의 투표 목록 조회 성공";
        public static final String UPDATE_VOTE = "SUCCESS - 투표 수정 성공";

        // <Memo - Error>
        public static final String DELETE_VOTE = "SUCCESS - 투표 삭제 성공";
        public static final String NOT_FOUND_VOTE = "ERROR - 투표를 찾을 수 없습니다.";
        public static final String BAD_REQUEST_VOTE = "ERROR - 잘못된 투표 요청 에러";




        // < Auth - Success>
        public static final String LOGIN_SUCCESS = "SUCCESS - 로그인 성공";
        public static final String LOGOUT_SUCCESS = "SUCCESS - 로그아웃 성공 및 user Refresh Token 삭제";
        public static final String UPDATE_PASSWORD = "SUCCESS - 비밀번호 수정 성공";
        public static final String READ_IS_LOGIN = "SUCCESS - 현재 로그인 여부 조회 성공";
        // < Auth - Error>
        public static final String UNAUTHORIZED = "ERROR - Unauthorized 에러";
        public static final String FORBIDDEN = "ERROR - Forbidden 에러"; // 권한 없을 때
        public static final String PREVENT_GET_ERROR = "Status 204 - 리소스 및 리다이렉트 GET호출 에러 방지";

        // < Token >
        public static final String TOKEN_IS_VALID = "VALID - 유효한 토큰  ";
        public static final String REISSUE_SUCCESS = "SUCCESS - JWT Access 토큰 재발급 성공";
        public static final String TOKEN_EXPIRED = "ERROR - JWT 토큰 만료 에러";
        public static final String TOKEN_ERROR = "ERROR - 잘못된 JWT 토큰 에러";
        public static final String BAD_REQUEST_TOKEN = "ERROR - 잘못된 토큰 요청 에러";

        public static final String TOKEN_IS_BLACKLIST = "ERROR - 폐기된 토큰";
        public static final String TOKEN_HASH_NOT_SUPPORTED = "ERROR - 지원하지 않는 형식의 토큰";
        public static final String WRONG_AUTH_HEADER = "ERROR - [Bearer ]로 시작하는 토큰이 없습니다.";
        public static final String TOKEN_VALIDATION_TRY_FAILED = "ERROR - 토큰 인증 실패";


        // < Etc >
        public static final String INTERNAL_SERVER_ERROR = "ERROR - 서버 내부 에러";
    }

