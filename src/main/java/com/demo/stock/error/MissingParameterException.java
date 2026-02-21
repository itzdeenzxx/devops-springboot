package com.demo.stock.error;

public class MissingParameterException extends RuntimeException {
    public MissingParameterException(String name) {
        super("Missing parameter: " + name);
    }
}
