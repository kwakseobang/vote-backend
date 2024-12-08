package com.kwakmunsu.vote.response.responseItem;

public class MessageItem {

        // < User >
        public static final String CREATED_USER = "SUCCESS - 회원 가입 성공";
        public static final String READ_USER = "SUCCESS - 회원 정보 조회 성공";
        public static final String UPDATE_USER = "SUCCESS - 회원 정보 수정 성공";
        public static final String DELETE_USER = "SUCCESS - 회원 탈퇴 성공";
        public static final String NOT_FOUND_USER = "ERROR - 회원을 찾을 수 없습니다.";
        public static final String BAD_REQUEST_USER = "ERROR - 잘못된 회원 요청 에러";
        public static final String DUPLICATE_ID = "ERROR - 회원가입 ID 중복 에러";
        public static final String DUPLICATE_NICKNAME = "ERROR - 회원가입 닉네임 중복 에러";

        // < Memo >
        public static final String CREATED_VOTE = "SUCCESS - 투표 생성 성공";
        public static final String READ_VOTE = "SUCCESS - 투표 정보 조회 성공";
        public static final String READ_VOTES = "SUCCESS - 회원의 투표 목록 조회 성공";
        public static final String UPDATE_VOTE = "SUCCESS - 투표 수정 성공";
        public static final String DELETE_VOTE = "SUCCESS - 투표 삭제 성공";
        public static final String NOT_FOUND_VOTE = "ERROR - 투표를 찾을 수 없습니다.";
        public static final String BAD_REQUEST_VOTE = "ERROR - 잘못된 투표 요청 에러";



        // < Auth >
        public static final String LOGIN_SUCCESS = "SUCCESS - 로그인 성공";
        public static final String UPDATE_PASSWORD = "SUCCESS - 비밀번호 수정 성공";
        public static final String READ_IS_LOGIN = "SUCCESS - 현재 로그인 여부 조회 성공";
        public static final String UNAUTHORIZED = "ERROR - Unauthorized 에러";
        public static final String FORBIDDEN = "ERROR - Forbidden 에러"; // 권한 없을 때
        public static final String PREVENT_GET_ERROR = "Status 204 - 리소스 및 리다이렉트 GET호출 에러 방지";

        // < Token >

        // < Etc >
        public static final String INTERNAL_SERVER_ERROR = "ERROR - 서버 내부 에러";
    }

