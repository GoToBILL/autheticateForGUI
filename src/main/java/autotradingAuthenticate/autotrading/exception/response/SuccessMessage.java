package com.likelion.runtale.common.response;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessMessage {
    USER_LOGIN_SUCCESS(OK, "로그인 성공"),
    USER_LOGOUT_SUCCESS(OK, "로그아웃 성공"),
    USER_SIGNUP_SUCCESS(CREATED, "회원 가입 성공"),
    USER_INFO_CHANGE_SUCCESS(OK, "회원 정보 수정 성공"),
    USER_INFO_FIND_SUCCESS(OK, "회원 조회 성공"),
    LOGIN_ID_VALIDATE_SUCCESS(OK, "아이디 중복 체크 성공"),
    RUNNING_INFO_SUCCESS(OK,"러닝 정보 요청 성공"),
    RUNNING_DELETE_SUCCESS(OK,"러닝 삭제 성공"),
    TIER_INFO_SUCCESS(OK,"티어 정보 요청 성공"),
    TIER_ASSIGN_SUCCESS(OK,"티어 할당 성공"),
    SCENARIO_STEP_SUCCESS(OK,"시나리오 스텝 요청 성공"),;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusValue(){
        return httpStatus.value();
    }

}
