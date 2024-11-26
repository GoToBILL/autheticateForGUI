package autotradingAuthenticate.autotrading.exception.customException;

import autotradingAuthenticate.autotrading.exception.response.ErrorMessage;
import lombok.Getter;

@Getter
public class UnauthorizedException extends BaseException {
    public UnauthorizedException(ErrorMessage error) {
        super(error);
    }
}