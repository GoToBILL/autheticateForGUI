package autotradingAuthenticate.autotrading.exception.customException;

import autotradingAuthenticate.autotrading.exception.response.ErrorMessage;

public class NotFoundException extends BaseException {
    public NotFoundException(ErrorMessage error) {
        super(error);
    }
}
