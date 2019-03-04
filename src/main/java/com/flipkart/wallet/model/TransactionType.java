package com.flipkart.wallet.model;

public enum TransactionType {
  DEBIT("debit"),
  CREDIT("credit");

  private String name;

  TransactionType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
