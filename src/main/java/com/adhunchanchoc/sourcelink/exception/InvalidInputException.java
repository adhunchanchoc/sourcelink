package com.adhunchanchoc.sourcelink.exception;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(){
        super("The parameter \"url\" is required in the request body");
    }
}
