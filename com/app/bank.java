package com.app;

import java.io.*;
import java.util.*;

public class bank {
    private static final String ACCOUNT_FILE_NAME = "/home/hpr/Protoexample/com/accountdetails.bin";
    private final Map<Integer, Account> accounts = new HashMap<>();
    private final Object lock = new Object();

    public bank() {
        loadAccounts();
    }

    public void createAccount(int accountNumber, String name) {
        synchronized (lock) {
            if (!accounts.containsKey(accountNumber)) {
                Account account = new Account(accountNumber, name, 0);
                accounts.put(accountNumber, account);
                saveAccounts();
                System.out.println("Account created successfully.\n");
            } else {
                System.out.println("Account number already exists.\n");
            }
        }
    }

    public void viewAccount(int accountNumber) {
        synchronized (lock) {
            Account account = accounts.get(accountNumber);
            if (account != null) {
                System.out.println("Account Number: " + accountNumber);
                System.out.println("Name: " + account.getName());
                System.out.println("Balance: " + account.getBalance() + "\n");
            } else {
                System.out.println("Account not found.\n");
            }
        }
    }

    public void updateAccount(int accountNumber, String newName) {
        synchronized (lock) {
            Account account = accounts.get(accountNumber);
            if (account != null) {
                account.setName(newName);
                saveAccounts();
                System.out.println("Account name updated successfully.\n");
            } else {
                System.out.println("Account not found.\n");
            }
        }
    }

    public void deleteAccount(int accountNumber) {
        synchronized (lock) {
            if (accounts.remove(accountNumber) != null) {
                saveAccounts();
                System.out.println("Account deleted successfully.\n");
            } else {
                System.out.println("Account not found.\n");
            }
        }
    }

    public void deposit(int accountNumber, float amount) {
        synchronized (lock) {
            Account account = accounts.get(accountNumber);
            if (account != null) {
                if (amount > 0) {
                    account.setBalance(account.getBalance() + amount);
                    saveAccounts();
                    System.out.println("Amount deposited successfully.\n");
                } else {
                    System.out.println("Invalid amount.\n");
                }
            } else {
                System.out.println("Account not found.\n");
            }
        }
    }

    public void withdraw(int accountNumber, float amount) {
        synchronized (lock) {
            Account account = accounts.get(accountNumber);
            if (account != null) {
                if (amount > 0 && amount <= account.getBalance()) {
                    account.setBalance(account.getBalance() - amount);
                    saveAccounts();
                    System.out.println("Amount withdrawn successfully.\n");
                } else {
                    System.out.println("Invalid amount.\n");
                }
            } else {
                System.out.println("Account not found.\n");
            }
        }
    }

    public void transfer(int fromAccountNumber, int toAccountNumber, float amount) {
        synchronized (lock) {
            Account fromAccount = accounts.get(fromAccountNumber);
            Account toAccount = accounts.get(toAccountNumber);
            if (fromAccount != null && toAccount != null) {
                if (amount > 0 && amount <= fromAccount.getBalance()) {
                    fromAccount.setBalance(fromAccount.getBalance() - amount);
                    toAccount.setBalance(toAccount.getBalance() + amount);
                    saveAccounts();
                    System.out.println("Amount transferred successfully.\n");
                } else {
                    System.out.println("Invalid amount or insufficient funds.\n");
                }
            } else {
                System.out.println("One or both accounts not found.\n");
            }
        }
    }

    public void displayAllAccounts() {
        synchronized (lock) {
            for (Account account : accounts.values()) {
                System.out.println("Account Number: " + account.getAccountNumber());
                System.out.println("Name: " + account.getName());
                System.out.println("Balance: " + account.getBalance());
                System.out.println();
            }
        }
    }

    private void saveAccounts() {
        synchronized (lock) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ACCOUNT_FILE_NAME))) {
                oos.writeObject(accounts);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void loadAccounts() {
        synchronized (lock) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ACCOUNT_FILE_NAME))) {
                Map<Integer, Account> loadedAccounts = (Map<Integer, Account>) ois.readObject();
                accounts.putAll(loadedAccounts);
            } catch (FileNotFoundException e) {
                System.out.println("No existing data file found.\n");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
