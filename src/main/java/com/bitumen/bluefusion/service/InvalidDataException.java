package com.bitumen.bluefusion.service;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException() {
        super();
    }

    public InvalidDataException(String message) {
        super(message);
    }
}
