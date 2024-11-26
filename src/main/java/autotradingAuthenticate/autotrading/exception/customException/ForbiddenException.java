package autotradingAuthenticate.autotrading.exception.customException;

import autotradingAuthenticate.autotrading.exception.response.ErrorMessage;
import lombok.Getter;

@Getter
public class ForbiddenException extends BaseException {
    public ForbiddenException(ErrorMessage error) {
        super(error, "[ForbiddenException] " + error.getMessage());
    }
}