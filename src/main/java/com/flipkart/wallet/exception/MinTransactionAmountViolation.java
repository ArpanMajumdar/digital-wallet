package com.flipkart.wallet.exception;

public class MinTransactionAmountViolation extends RuntimeException {
  public MinTransactionAmountViolation(String message) {
    super(message);
  }
}
