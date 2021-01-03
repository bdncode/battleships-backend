package bdn.code.battleshipsbackend.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ApiException {

    private final String message;
    private final HttpStatus httpStatus;
    private final Date timeStamp;
}
