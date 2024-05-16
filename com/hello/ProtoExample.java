package com.hello;
import com.hello.Hello.hellorequest;
import java.util.HashMap;
import java.util.Map;

public class ProtoExample {
    public static void main(String[] args) {
        Map<Integer, hellorequest> accountDetails = new HashMap<>();
        hellorequest account1 = hellorequest.newBuilder()
                                            .setName("Name1")
                                            .build();
        accountDetails.put(123456, account1);

        hellorequest account2 = hellorequest.newBuilder()
                                            .setName("Name2")
                                            .build();
        accountDetails.put(987654, account2);
        System.out.println("Bank Account Details:");
        for (Map.Entry<Integer, hellorequest> entry : accountDetails.entrySet()) {
            int accountNumber = entry.getKey();
            hellorequest account = entry.getValue();
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Name: " + account.getName());
            System.out.println();
        }
    }
}