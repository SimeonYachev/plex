package com.scalefocus.java.simeonyachev.exceptions;

public abstract class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
