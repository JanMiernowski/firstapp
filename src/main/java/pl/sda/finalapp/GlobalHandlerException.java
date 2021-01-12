package pl.sda.finalapp;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.sda.finalapp.exceprions.WrongIdException;

import java.util.UUID;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(value = WrongIdException.class)
    public String handleWrongIdException(WrongIdException ex){
        String errorCode = UUID.randomUUID().toString();
        System.out.println("Error code " + errorCode);
        ex.printStackTrace();
        return "contactWithAdmin";
    }

}
