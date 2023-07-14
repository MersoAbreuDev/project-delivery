package com.example.project.handle.response;

public class ObjectNotFoundException  extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
