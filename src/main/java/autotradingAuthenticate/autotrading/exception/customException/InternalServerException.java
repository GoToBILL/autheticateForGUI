package autotradingAuthenticate.autotrading.exception.customException;

import autotradingAuthenticate.autotrading.exception.response.ErrorMessage;
import lombok.Getter;

@Getter
public class InternalServerException extends BaseException {
    public InternalServerException(ErrorMessage error) {
        super(error);
    }
}
