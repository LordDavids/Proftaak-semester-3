package com.api.user.exeptions;

public class invalidCredentialsException extends RuntimeException {
  public invalidCredentialsException(String message) {
    super(message);
  }
}
