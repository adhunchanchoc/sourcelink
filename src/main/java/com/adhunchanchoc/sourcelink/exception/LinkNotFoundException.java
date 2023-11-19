package com.adhunchanchoc.sourcelink.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) //would be needed if there was no advice
public class LinkNotFoundException extends RuntimeException {
    public LinkNotFoundException(Long id) { //String message could be passed from the calling controller/service
        super("Desired link " + id + " does not exist");
    }
}
