package com.app;

import com.app.Bank.AccountDetails;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class BankAppTest {
    
    public static void main(String[] args) {
        Map<Integer, AccountDetails> accountDetails = BankApp.loadAccountDetails();
        if (accountDetails.isEmpty()) {
            for (int i = 1; i <= 10; i++) {
                AccountDetails account = AccountDetails.newBuilder()
                        .setAccountnumber(i)
                        .setName("AccountHolder" + i)
                        .setBalance(1000)
                        .build();
                accountDetails.put(i, account);
            }
            BankApp.saveAccountDetails(accountDetails);
        }

        // Perform test case i
        testTransferAmountConsistency(accountDetails);

        // Perform test case ii
        testOperationsConsistency(accountDetails);
    }
    //TEST CASE 1:
    private static void testTransferAmountConsistency(Map<Integer, AccountDetails> accountDetails) {
        double initialTotalBalance = calculateTotalBalance(accountDetails);

        Random random = new Random();
        for (int i = 0; i < 50; i++) { // Perform 50 random transfers
            int fromAccountNumber = random.nextInt(10);
            int toAccountNumber = random.nextInt(10);

            // Ensure different accounts for transfer
            while (toAccountNumber == fromAccountNumber) {
                toAccountNumber = random.nextInt(10);
            }

            float amount = random.nextFloat(1000);

            BankApp.transferAmount(new Scanner(System.in), accountDetails, fromAccountNumber, toAccountNumber, amount);
        }

        double finalTotalBalance = calculateTotalBalance(accountDetails);

        if (initialTotalBalance == finalTotalBalance) {
            System.out.println("Test case i passed: Initial and final total balance are equal.");
        } else {
            System.out.println("Test case i failed: Initial and final total balance are not equal.");
        }
        System.out.println(initialTotalBalance+" "+finalTotalBalance);
    }

    //TEST CASE 2:
    private static void testOperationsConsistency(Map<Integer, AccountDetails> accountDetails) {
        double initialTotalBalance = calculateTotalBalance(accountDetails);
        double depositTotal = 0;
        double withdrawTotal = 0;

        Random random = new Random();
        for (int i = 0; i < 50; i++) { // Perform 50 random operations
            int accountNumber = random.nextInt(10);
            float depositAmount = random.nextFloat() * 100; 
            float withdrawAmount = random.nextFloat() * 100; 

            depositTotal += depositAmount;
            withdrawTotal += withdrawAmount;

            // Deposit
            BankApp.depositAmount(new Scanner(System.in), accountDetails, accountNumber, depositAmount);

            // Withdraw
            BankApp.withdrawAmount(new Scanner(System.in), accountDetails, accountNumber, withdrawAmount);

            // Transfer between random accounts
            int fromAccountNumber = random.nextInt(10);
            int toAccountNumber = random.nextInt(10);

            // Ensure different accounts for transfer
            while (toAccountNumber == fromAccountNumber) {
                toAccountNumber = random.nextInt(10);
            }

            float transferAmount = random.nextFloat(1000);

            BankApp.transferAmount(new Scanner(System.in), accountDetails, fromAccountNumber, toAccountNumber, transferAmount);
        }

        double finalTotalBalance = calculateTotalBalance(accountDetails);

        if (finalTotalBalance == initialTotalBalance + depositTotal - withdrawTotal) {
            System.out.println("Test case 2 passed: Final total balance is correct.");
        } else {
            System.out.println("Test case 2 failed: Final total balance is incorrect.");
        }
        System.out.println(initialTotalBalance+" "+finalTotalBalance);
    }

    //CALCULATE TOTAL BALANCE
    private static double calculateTotalBalance(Map<Integer, AccountDetails> accountDetails) {
        double totalBalance = 0;
        for (AccountDetails account : accountDetails.values()) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }
}
