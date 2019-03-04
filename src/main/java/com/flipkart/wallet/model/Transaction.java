package com.flipkart.wallet.model;

public class Transaction {
  private String party;
  private TransactionType transactionType;
  private double amount;

  public Transaction(String party, TransactionType transactionType, double amount) {
    this.party = party;
    this.transactionType = transactionType;
    this.amount = amount;
  }

  public String getParty() {
    return party;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public double getAmount() {
    return amount;
  }
}
