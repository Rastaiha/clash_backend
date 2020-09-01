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

    @ExceptionHandler
    public final ResponseEntity cardNotFoundHandler(CardNotFoundException e) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity cardTypeNotFoundHandler(CardTypeNotFoundException e) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity pickedUpCardHandler(PickedUpCardException e) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity notInTownhallHandler(NotInTownHallException e) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity notCorrectAgeHandler(NotCorrectAgeException e) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity notEnoughResourcesHandler(NotEnoughResourcesException e) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity fullBackpackHandler(FullBackpackException e) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity notInYourBackpackHandler(NotInYourBackpackException e) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity noCardAvailableHandler(NoCardAvailableException e) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity fighterNotAvailableHandler(FighterNotAvailableException e) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
