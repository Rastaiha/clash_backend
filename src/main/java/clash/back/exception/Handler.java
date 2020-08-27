package clash.back.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Handler {

    @ExceptionHandler
    public final ResponseEntity playerNotFoundHandler(PlayerNotFoundException e) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
