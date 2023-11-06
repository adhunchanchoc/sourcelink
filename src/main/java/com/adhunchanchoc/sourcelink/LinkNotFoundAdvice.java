package com.adhunchanchoc.sourcelink;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LinkNotFoundAdvice {
    @ExceptionHandler(LinkNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String linkNotFoundHandler(LinkNotFoundException e){
        return e.getMessage();
    }
}
