package bdn.code.battleshipsbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = InvalidBoardException.class)
    public ResponseEntity<Object> invalidBoardException(InvalidBoardException invalidBoardException) {

        ApiException apiException = new ApiException(
                invalidBoardException.getMessage(),
                HttpStatus.BAD_REQUEST,
                new Date()
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> defaultException() {

        ApiException apiException = new ApiException(
                ApiExceptionMessages.INTERNAL_SERVER_ERROR.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                new Date()
        );
        return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
