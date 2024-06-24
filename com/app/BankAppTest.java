package com.app;

import com.app.Bank.AccountDetails;
import java.util.Map;
import java.util.Random;

public class BankAppTest {

    /*public static void main(String[] args) {
        Map<Integer, AccountDetails> accountDetails = BankApp.loadAccountDetails();
        if (accountDetails.isEmpty()) {
            for (int i = 1; i <= 100; i++) {
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

    // TEST CASE 1:
    private static void testTransferAmountConsistency(Map<Integer, AccountDetails> accountDetails) {
        double initialTotalBalance = calculateTotalBalance(accountDetails);

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int fromAccountNumber = random.nextInt(100) + 1;
            int toAccountNumber = random.nextInt(100) + 1;

            while (toAccountNumber == fromAccountNumber) {
                toAccountNumber = random.nextInt(100) + 1;
            }

            float amount = (float) (Math.round(random.nextFloat() * 1000 * 10) / 10.0);

            if (accountDetails.containsKey(fromAccountNumber) && accountDetails.containsKey(toAccountNumber)) {
                BankApp.transferAmount(accountDetails, fromAccountNumber, toAccountNumber, amount);
            }
        }

        double finalTotalBalance = calculateTotalBalance(accountDetails);

        if (Math.abs(initialTotalBalance - finalTotalBalance) < 0.2) {
            System.out.println("Test case i passed: Initial and final total balance are equal.");
        } else {
            System.out.println("Test case i failed: Initial and final total balance are not equal.");
        }
        System.out.println("Initial: " + initialTotalBalance + ", Final: " + finalTotalBalance);
    }

    // TEST CASE 2:
    private static void testOperationsConsistency(Map<Integer, AccountDetails> accountDetails) {
        double initialTotalBalance = calculateTotalBalance(accountDetails);
        double depositTotal = 0;
        double withdrawTotal = 0;

        Random random = new Random();
        for (int i = 0; i < 10; i++) { 
            int accountNumber = random.nextInt(10) + 1;
            float depositAmount = (float) (Math.round(random.nextFloat() * 100 * 10) / 10.0);
            float withdrawAmount = (float) (Math.round(random.nextFloat() * 100 * 10) / 10.0);

            depositTotal += depositAmount;
            withdrawTotal += withdrawAmount;

            if (accountDetails.containsKey(accountNumber)) {
                BankApp.depositAmount(accountDetails, accountNumber, depositAmount);

                BankApp.withdrawAmount(accountDetails, accountNumber, withdrawAmount);
            }

            int fromAccountNumber = random.nextInt(100) + 1;
            int toAccountNumber = random.nextInt(100) + 1;

            while (toAccountNumber == fromAccountNumber) {
                toAccountNumber = random.nextInt(100) + 1;
            }

            float transferAmount = (float) (Math.round(random.nextFloat() * 1000 * 10) / 10.0);

            if (accountDetails.containsKey(fromAccountNumber) && accountDetails.containsKey(toAccountNumber)) {
                BankApp.transferAmount(accountDetails, fromAccountNumber, toAccountNumber, transferAmount);
            }
        }

        double finalTotalBalance = calculateTotalBalance(accountDetails);

        if (Math.abs(finalTotalBalance - (initialTotalBalance + depositTotal - withdrawTotal)) < 0.2) {
            System.out.println("Test case 2 passed: Final total balance is correct.");
        } else {
            System.out.println("Test case 2 failed: Final total balance is incorrect.");
        }
        System.out.println("Initial: " + initialTotalBalance + ", Deposited: " + depositTotal + ", Withdrawn: " + withdrawTotal + ", Final: " + finalTotalBalance);
    }

    // CALCULATE TOTAL BALANCE
    private static double calculateTotalBalance(Map<Integer, AccountDetails> accountDetails) {
        double totalBalance = 0;
        for (AccountDetails account : accountDetails.values()) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }*/
}
