package com.flipkart.wallet.exception;

public class DuplicateUserException extends RuntimeException {
  public DuplicateUserException(String message) {
    super(message);
  }
}
