package autotradingAuthenticate.autotrading.exception.customException;

import autotradingAuthenticate.autotrading.exception.response.ErrorMessage;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorMessage error;

    public BaseException(ErrorMessage error) {
        super(error.getMessage()); // ErrorMessage의 메시지 사용
        this.error = error;
    }

    public int getHttpStatus(){
        return error.getHttpStatusValue();
    }
}