package com.killteam.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TeamNotFoundAdvice {
    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    String TeamNotFoundHandler(TeamNotFoundException ex) {
        return ex.getMessage();
    }
}
