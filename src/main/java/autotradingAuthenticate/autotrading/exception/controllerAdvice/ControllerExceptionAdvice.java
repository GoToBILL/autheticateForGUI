package autotradingAuthenticate.autotrading.exception.controllerAdvice;

import autotradingAuthenticate.autotrading.exception.ApiResponse;
import autotradingAuthenticate.autotrading.exception.customException.BaseException;
import autotradingAuthenticate.autotrading.exception.customException.ForbiddenException;
import autotradingAuthenticate.autotrading.exception.customException.NotFoundException;
import autotradingAuthenticate.autotrading.exception.customException.UnauthorizedException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse> handleBaseException(BaseException exception) {
        return ResponseEntity.status(exception.getError().getHttpStatus())
                .body(ApiResponse.error(exception.getError(), exception.getMessage()));
    }
}
