package com.adhunchanchoc.sourcelink;

public class LinkNotFoundException extends RuntimeException{
    public LinkNotFoundException(Long id) {
        super("Desired link "+id + " does not exist");
    }
}
