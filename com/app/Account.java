package com.app;

import com.app.Bank.AccountDetails;

public class Account {
    private AccountDetails accountDetails;

    public Account(int accountNumber, String name, float balance) {
        this.accountDetails = AccountDetails.newBuilder()
                .setAccountnumber(accountNumber)
                .setName(name)
                .setBalance(balance)
                .build();
    }

    public Account(AccountDetails accountDetails) {
        this.accountDetails = accountDetails;
    }

    public int getAccountNumber() {
        return accountDetails.getAccountnumber();
    }

    public String getName() {
        return accountDetails.getName();
    }

    public void setName(String name) {
        accountDetails = accountDetails.toBuilder().setName(name).build();
    }

    public float getBalance() {
        return accountDetails.getBalance();
    }

    public void setBalance(float balance) {
        accountDetails = accountDetails.toBuilder().setBalance(balance).build();
    }

    public AccountDetails getAccountDetails() {
        return accountDetails;
    }
}
