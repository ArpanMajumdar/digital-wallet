package com.flipkart.wallet.model;

import com.flipkart.wallet.exception.InsufficientBalanceException;
import com.flipkart.wallet.exception.MinTransactionAmountViolation;

import java.util.ArrayList;
import java.util.List;

public class AccountHolder implements Comparable<AccountHolder> {
  private static final double MIN_TX_AMOUNT = 0.0001;
  private String name;
  private double balance;
  private int orderOfCreation;
  private List<Transaction> transactions;

  private boolean appliedForFD;
  private long numTransactionsAfterFDApplied;
  private double minBalanceForFD;

  public AccountHolder(String name, double balance, int orderOfCreation) {
    this.name = name;
    this.balance = balance;
    this.orderOfCreation = orderOfCreation;
    this.transactions = new ArrayList<>();
  }

  public int getOrderOfCreation() {
    return orderOfCreation;
  }

  public boolean hasAppliedForFD() {
    return appliedForFD;
  }

  public void setAppliedForFD(boolean appliedForFD) {
    this.appliedForFD = appliedForFD;
  }

  public double getMinBalanceForFD() {
    return minBalanceForFD;
  }

  public void setMinBalanceForFD(double minBalanceForFD) {
    this.minBalanceForFD = minBalanceForFD;
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public double getBalance() {
    return balance;
  }

  public String getName() {
    return name;
  }

  public void applyForFD(double minbalance) {
    this.minBalanceForFD = minbalance;
    this.appliedForFD = true;
    this.numTransactionsAfterFDApplied = 0;
  }

  public void debitAmount(String toAccount, double amount) {
    if (!isValidTransactionAmount(amount)) {
      throw new MinTransactionAmountViolation("transaction amount cannot be less than FkR 0.0001");
    }

    if (balance < amount) {
      throw new InsufficientBalanceException("balance must be greater than amount to be debited");
    }

    this.balance -= amount;
    transactions.add(new Transaction(toAccount, TransactionType.DEBIT, amount));
    checkForMaturityOfFD();
  }

  public void creditAmount(String fromAccount, double amount) {
    if (!isValidTransactionAmount(amount)) {
      throw new MinTransactionAmountViolation("transaction amount cannot be less than FkR 0.0001");
    }
    this.balance += amount;
    transactions.add(new Transaction(fromAccount, TransactionType.CREDIT, amount));
    checkForMaturityOfFD();
  }

  private void checkForMaturityOfFD() {
    if (hasAppliedForFD()) {
      if (balance > minBalanceForFD) {
        numTransactionsAfterFDApplied++;
      } else {
        appliedForFD = false;
        numTransactionsAfterFDApplied = 0;
      }

      if (numTransactionsAfterFDApplied >= 5) {
        appliedForFD = false;
        numTransactionsAfterFDApplied = 0;
        creditAmount("FD", 10);
        System.out.println("FkR 10 credited to " + this.name + " for maturity of FD");
      }
    }
  }

  private boolean isValidTransactionAmount(double transactionAmount) {
    return transactionAmount >= MIN_TX_AMOUNT;
  }

  @Override
  public int compareTo(AccountHolder accountHolder) {
    if (this.transactions.size() < accountHolder.getTransactions().size()) {
      return 1;
    } else if (this.transactions.size() > accountHolder.getTransactions().size()) {
      return -1;
    } else {
      if (this.balance < accountHolder.getBalance()) {
        return 1;
      } else if (this.balance > accountHolder.getBalance()) {
        return -1;
      } else {
        if (this.orderOfCreation > accountHolder.getOrderOfCreation()) {
          return 1;
        } else {
          return -1;
        }
      }
    }
  }
}
