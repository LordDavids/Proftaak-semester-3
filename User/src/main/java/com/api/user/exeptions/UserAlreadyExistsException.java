package com.api.user.exeptions;

public class userAlreadyExistsException extends RuntimeException {
    public userAlreadyExistsException(String message) {
        super(message);
    }
}
