package com.flipkart.wallet.exception;

public class AccountDoesNotExist extends RuntimeException {
  public AccountDoesNotExist(String message) {
    super(message);
  }
}
