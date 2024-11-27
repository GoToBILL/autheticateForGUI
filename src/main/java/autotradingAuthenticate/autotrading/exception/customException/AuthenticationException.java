package autotradingAuthenticate.autotrading.exception.customException;

import autotradingAuthenticate.autotrading.exception.response.ErrorMessage;

public class AuthenticationException extends BaseException {
    public AuthenticationException(ErrorMessage error) {
        super(error);
    }
}
