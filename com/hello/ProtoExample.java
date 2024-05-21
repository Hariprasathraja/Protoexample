package com.hello;

import java.io.*;
import com.hello.Hello.hellorequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProtoExample {
    private static final String account_file_name="/home/hpr/Protoexample/com/hello/accountdetails.text";
    public static void main(String[] args) {
        Map<Integer, hellorequest> accountDetails = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        boolean exit=false;
        while(!exit){
            System.out.println("****Bank Application Menu****");
            System.out.println("1. Create new Account\n2. Exit");
            System.out.print("Enter your choice: ");
            int choice=scanner.nextInt();

            switch (choice) {
                case 1:
                    createAccount(scanner, accountDetails);
                    break;
                case 2:
                    exit=true;
                    save(accountDetails);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }

        System.out.println("Bank Account Details loaded from file:\n");
        Map<Integer,hellorequest> loadedAccountDetails= load();
        for (Map.Entry<Integer, hellorequest> entry : loadedAccountDetails.entrySet()) {
            int accountNumber = entry.getKey();
            hellorequest account = entry.getValue();
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Name: " + account.getName());
            System.out.println("Balance: "+account.getBalance());
            System.out.println();
        }

        scanner.close();
    }

    private static void createAccount(Scanner scanner,Map<Integer,hellorequest>accountDetails){
        System.out.println("Enter account number: ");
        int accountnumber=scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter name:" );
        String name=scanner.nextLine();

        hellorequest account=hellorequest.newBuilder()
                                             .setName(name)
                                             .setBalance(0)
                                             .build();
        accountDetails.put(accountnumber,account);
        System.out.println("Account created successfully.");

    }

    private static void save(Map<Integer,hellorequest> accountDetails){
        try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(account_file_name))){
            oos.writeObject(accountDetails);
            System.out.println("Account details saved");
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    private static Map<Integer,hellorequest> load(){
        Map<Integer,hellorequest> Details=new HashMap<>();
        try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream(account_file_name))){
            Details=(Map<Integer,hellorequest>)ois.readObject();
        } catch(IOException| ClassNotFoundException e){
            e.printStackTrace();
        }
        return Details;
    }
}
