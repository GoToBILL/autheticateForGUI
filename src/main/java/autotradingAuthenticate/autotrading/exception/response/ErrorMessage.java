package autotradingAuthenticate.autotrading.exception.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage {
    // 사용자 관련 실패 메시지
    USER_LOGIN_ID_NOT_EXIST(NOT_FOUND, "존재하지 않는 아이디"),
    USER_PASSWORD_NOT_EXIST(NOT_FOUND, "존재하지 않는 비밀번호"),
    USER_NOT_EXIST(NOT_FOUND, "존재하지 않는 회원"),
    USER_LOGIN_ID_DUPLICATE(BAD_REQUEST, "아이디가 이미 존재합니다."),
    USER_NOT_LOGGED_IN(UNAUTHORIZED, "로그인이 필요합니다."),
    USER_NOT_AUTHORIZED(FORBIDDEN, "권한이 없습니다."),
    USER_LOGIN_FAILED(UNAUTHORIZED, "로그인 실패"),
    USER_SIGNUP_FAILED(BAD_REQUEST, "회원 가입 실패"),
    USER_INFO_UPDATE_FAILED(BAD_REQUEST, "회원 정보 수정 실패"),

    // 게시글 관련 실패 메시지
    POST_NOT_FOUND(NOT_FOUND, "존재하지 않는 게시글"),
    POST_CREATE_FAILED(BAD_REQUEST, "게시글 작성 실패"),
    POST_UPDATE_FAILED(BAD_REQUEST, "게시글 수정 실패"),
    POST_DELETE_FAILED(BAD_REQUEST, "게시글 삭제 실패"),
    POST_LIST_FAILED(INTERNAL_SERVER_ERROR, "게시글 목록 조회 실패"),

    // 댓글 관련 실패 메시지
    COMMENT_NOT_FOUND(NOT_FOUND, "존재하지 않는 댓글"),
    COMMENT_ADD_FAILED(BAD_REQUEST, "댓글 추가 실패"),
    COMMENT_DELETE_FAILED(BAD_REQUEST, "댓글 삭제 실패"),

    // 좋아요, 북마크 관련 실패 메시지
    LIKE_ADD_FAILED(BAD_REQUEST, "좋아요 추가 실패"),
    LIKE_REMOVE_FAILED(BAD_REQUEST, "좋아요 취소 실패"),
    BOOKMARK_ADD_FAILED(BAD_REQUEST, "북마크 추가 실패"),
    BOOKMARK_REMOVE_FAILED(BAD_REQUEST, "북마크 취소 실패"),

    // 인증 실패 메시지
    AUTHENTICATION_FAILED(UNAUTHORIZED, "인증에 실패했습니다."),
    INVALID_TOKEN(UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(UNAUTHORIZED, "만료된 토큰입니다."),

    // 기타 실패 메시지
    CATEGORY_NOT_FOUND(NOT_FOUND, "존재하지 않는 카테고리"),
    CATEGORY_LIST_FAILED(INTERNAL_SERVER_ERROR, "카테고리 목록 조회 실패"),
    UNEXPECTED_ERROR(INTERNAL_SERVER_ERROR, "예상치 못한 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusValue(){
        return httpStatus.value();
    }

}
