package com.moodsensor.stem.exception;

public class DataServicesException extends RuntimeException {
  public DataServicesException(String message) {
    super(message);
  }

  public DataServicesException(String message, Throwable cause) {
    super(message, cause);
  }
}
