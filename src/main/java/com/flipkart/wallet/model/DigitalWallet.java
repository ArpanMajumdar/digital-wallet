package com.flipkart.wallet.model;

import com.flipkart.wallet.exception.*;

import java.util.*;

public class DigitalWallet {
  private Map<String, AccountHolder> accountHolders;
  private static final String EXCEPTION = "An exception occurred due to: ";
  private int numAccounts;

  public DigitalWallet() {
    accountHolders = new HashMap<>();
  }

  public void createWallet(String accountHolderName, double amount) {
    try {
      if (accountHolders.get(accountHolderName) != null) {
        throw new DuplicateUserException("account with given name already exists");
      }

      if (!isValidAmount(amount)) {
        throw new NegativeAmoutException("amout cannot be negative");
      }

      this.numAccounts++;
      AccountHolder accountHolder = new AccountHolder(accountHolderName, amount, this.numAccounts);
      accountHolders.put(accountHolderName, accountHolder);
    } catch (Exception e) {
      System.out.println(EXCEPTION + e.getMessage());
      System.out.println();
    }
  }

  public void transferMoney(String fromAccount, String toAccount, double amount) {

    // Validations
    try {
      if (!isValidAmount(amount)) {
        throw new NegativeAmoutException("amount cannot be negative");
      }
      if (fromAccount.equals(toAccount)) {
        throw new IllegalTransactionException("from and to accounts cannot be the same");
      }
      if (!isAccountPresent(fromAccount)) {
        throw new AccountDoesNotExist("account with name " + fromAccount + " does not exist");
      }
      if (!isAccountPresent(toAccount)) {
        throw new AccountDoesNotExist("account with name " + toAccount + " does not exist");
      }

      AccountHolder from = accountHolders.get(fromAccount);
      AccountHolder to = accountHolders.get(toAccount);

      from.debitAmount(toAccount, amount);
      to.creditAmount(fromAccount, amount);

      if (isEligibleForOffer1(from, to)) {
        applyOffer1(from, to);
      }
    } catch (Exception e) {

      System.out.println(EXCEPTION + e.getMessage());
      System.out.println();
    }
  }

  public void getStatement(String accountName) {
    try {
      if (!isAccountPresent(accountName)) {
        throw new AccountDoesNotExist("account with name " + accountName + " does not exist");
      }
      AccountHolder accountHolder = accountHolders.get(accountName);
      List<Transaction> transactions = accountHolder.getTransactions();

      System.out.println("Account statement for " + accountName);
      transactions.stream()
          .forEach(
              transaction -> {
                System.out.println(
                    transaction.getParty()
                        + " "
                        + transaction.getTransactionType()
                        + " "
                        + transaction.getAmount());
              });
      System.out.println();
    } catch (Exception e) {
      System.out.println(EXCEPTION + e.getMessage());
      System.out.println();
    }
  }

  public void getOverview() {
    System.out.println("Overview");
    accountHolders.forEach(
        (accountName, accountHolder) -> {
          System.out.println(accountName + " " + accountHolder.getBalance());
        });
    System.out.println();
  }

  public void applyOffer2() {
    List<AccountHolder> accountHolderList = new ArrayList<>();
    accountHolders.forEach(
        (accountName, accountHolder) -> {
          accountHolderList.add(accountHolder);
        });

    Collections.sort(accountHolderList);

    if (accountHolders.size() < 3) {
      throw new Offer2NotApplicable("offer 2 is not applicable due to not enough accounts present");
    }

    accountHolderList.get(0).creditAmount("Offer2", 10);
    accountHolderList.get(1).creditAmount("Offer2", 5);
    accountHolderList.get(2).creditAmount("Offer2", 2);
  }

  public void applyForFD(String accountName, double minBalance) {
    try {
      if (!isAccountPresent(accountName)) {
        throw new AccountDoesNotExist("account with name " + accountName + " does not exist");
      }
      if (!isValidAmount(minBalance)) {
        throw new NegativeAmoutException("amount cannot be negative");
      }
      AccountHolder accountHolder = accountHolders.get(accountName);
      accountHolder.applyForFD(minBalance);
    } catch (Exception e) {
      System.out.println(EXCEPTION + e.getMessage());
      System.out.println();
    }
  }

  private boolean isValidAmount(double amount) {
    return amount >= 0;
  }

  private boolean isAccountPresent(String account) {
    return accountHolders.containsKey(account);
  }

  private boolean isEligibleForOffer1(AccountHolder from, AccountHolder to) {
    return from.getBalance() == to.getBalance();
  }

  private void applyOffer1(AccountHolder account1, AccountHolder account2) {
    account1.creditAmount("Offer1", 10);
    account2.creditAmount("Offer1", 10);
  }
}
