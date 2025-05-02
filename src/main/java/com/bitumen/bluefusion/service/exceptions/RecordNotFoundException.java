package com.bitumen.bluefusion.service.exceptions;

public class RecordNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException() {
        super("Record Not Found !");
    }
}
