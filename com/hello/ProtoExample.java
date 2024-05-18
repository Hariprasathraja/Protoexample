package com.hello;

import com.hello.Hello.hellorequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProtoExample {
    public static void main(String[] args) {
        Map<Integer, hellorequest> accountDetails = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of accounts: ");
        int numAccounts = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        for (int i = 0; i < numAccounts; i++) {
            System.out.print("Enter account number: ");
            int accountNumber = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            hellorequest account = hellorequest.newBuilder()
                                               .setName(name)
                                               .build();
            accountDetails.put(accountNumber, account);
        }

        System.out.println("Bank Account Details:");
        for (Map.Entry<Integer, hellorequest> entry : accountDetails.entrySet()) {
            int accountNumber = entry.getKey();
            hellorequest account = entry.getValue();
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Name: " + account.getName());
            System.out.println();
        }

        scanner.close();
    }
}
