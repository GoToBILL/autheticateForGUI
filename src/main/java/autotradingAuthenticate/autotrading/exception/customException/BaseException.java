package autotradingAuthenticate.autotrading.exception.customException;

import autotradingAuthenticate.autotrading.exception.response.ErrorMessage;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorMessage error;

    public BaseException(ErrorMessage error, String message) {
        super(message);
        this.error = error;
    }

    public int getHttpStatus(){
        return error.getHttpStatusValue();
    }
}
