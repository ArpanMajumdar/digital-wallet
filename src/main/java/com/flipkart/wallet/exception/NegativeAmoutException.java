package com.flipkart.wallet.exception;

public class NegativeAmoutException extends RuntimeException {
  public NegativeAmoutException(String message) {
    super(message);
  }
}
