package com.flipkart.wallet;

import com.flipkart.wallet.model.DigitalWallet;

public class DigitalWalletDemo {
  public static void main(String[] args) {
    //
    DigitalWallet digitalWallet = new DigitalWallet();

    digitalWallet.createWallet("Harry", 100);
    digitalWallet.createWallet("Ron", 95.7);
    digitalWallet.createWallet("Hermoine", 104);
    digitalWallet.createWallet("Albus", 200);

    digitalWallet.transferMoney("Hermoine", "Harry", 2);
    digitalWallet.applyOffer2();
    digitalWallet.getStatement("Harry");

    digitalWallet.getOverview();
  }
}
