package autotradingAuthenticate.autotrading.exception.response;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessMessage {
    // 사용자 관련 메시지
    USER_LOGIN_SUCCESS(OK, "로그인 성공"),
    USER_LOGOUT_SUCCESS(OK, "로그아웃 성공"),
    USER_SIGNUP_SUCCESS(CREATED, "회원 가입 성공"),
    USER_INFO_UPDATE_SUCCESS(OK, "회원 정보 수정 성공"),
    USER_INFO_RETRIEVE_SUCCESS(OK, "회원 정보 조회 성공"),
    LOGIN_ID_VALIDATE_SUCCESS(OK, "아이디 중복 체크 성공"),
    PASSWORD_RESET_SUCCESS(OK, "비밀번호 재설정 성공"),
    PASSWORD_CHANGE_SUCCESS(OK, "비밀번호 변경 성공"),

    // 게시글 관련 메시지
    POST_CREATE_SUCCESS(CREATED, "게시글 작성 성공"),
    POST_READ_SUCCESS(OK, "게시글 조회 성공"),
    POST_UPDATE_SUCCESS(OK, "게시글 수정 성공"),
    POST_DELETE_SUCCESS(OK, "게시글 삭제 성공"),
    POST_LIST_SUCCESS(OK, "게시글 목록 조회 성공"),

    // 댓글 관련 메시지
    COMMENT_ADD_SUCCESS(CREATED, "댓글 추가 성공"),
    COMMENT_DELETE_SUCCESS(OK, "댓글 삭제 성공"),
    COMMENT_LIST_SUCCESS(OK, "댓글 목록 조회 성공"),

    // 좋아요, 북마크 관련 메시지
    LIKE_ADD_SUCCESS(OK, "게시글 좋아요 추가 성공"),
    LIKE_REMOVE_SUCCESS(OK, "게시글 좋아요 취소 성공"),
    BOOKMARK_ADD_SUCCESS(OK, "게시글 북마크 추가 성공"),
    BOOKMARK_REMOVE_SUCCESS(OK, "게시글 북마크 취소 성공"),

    // 기타
    CATEGORY_LIST_SUCCESS(OK, "카테고리 목록 조회 성공"),;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusValue(){
        return httpStatus.value();
    }

}
