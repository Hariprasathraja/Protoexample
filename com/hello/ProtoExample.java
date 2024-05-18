package com.hello;

import java.io.*;
import com.hello.Hello.hellorequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProtoExample {
    private static final String file_name="/home/hpr/Protoexample/com/hello/data.text";
    public static void main(String[] args) {
        Map<Integer, hellorequest> accountDetails = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of accounts: ");
        int numAccounts = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numAccounts; i++) {
            System.out.print("Enter account number: ");
            int accountNumber = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            hellorequest account = hellorequest.newBuilder()
                                               .setName(name)
                                               .build();
            accountDetails.put(accountNumber, account);
        }
        save(accountDetails);

        System.out.println("Bank Account Details loaded from file:\n");
        Map<Integer,hellorequest> loadedAccountDetails= load();
        for (Map.Entry<Integer, hellorequest> entry : loadedAccountDetails.entrySet()) {
            int accountNumber = entry.getKey();
            hellorequest account = entry.getValue();
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Name: " + account.getName());
            System.out.println();
        }

        scanner.close();
    }
    private static void save(Map<Integer,hellorequest> accountDetails){
        try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file_name))){
            oos.writeObject(accountDetails);
            System.out.println("Account details saved");
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    private static Map<Integer,hellorequest> load(){
        Map<Integer,hellorequest> Details=new HashMap<>();
        try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file_name))){
            Details=(Map<Integer,hellorequest>)ois.readObject();
        } catch(IOException| ClassNotFoundException e){
            e.printStackTrace();
        }
        return Details;
    }
}
