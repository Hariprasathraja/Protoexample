package com.app;


import java.io.*;
import com.app.Bank.AccountDetails;

import java.util.*;
import java.util.concurrent.locks.*;



public class BankApp {
    private static final String account_file_name="/home/hpr/Protoexample/com/accountdetails.bin";
    private static final Object lock = new Object();
    public static void main(String[] args) {
        Map<Integer, AccountDetails> accountDetails = loadAccountDetails();
        Scanner scanner = new Scanner(System.in);
        boolean exit=false;
        while(!exit){
            System.out.println("****Bank Application Menu****\n");
            System.out.println("1. Create new Account\n2. View Account\n3. Update Account\n4. Delete Account\n5. Deposit Amount\n6. Withdraw Amount\n7. Transfer Acccount\n8. Display All Account Details\n9. Exit");
            System.out.print("\nEnter your choice: ");
            int choice=scanner.nextInt();

            switch (choice) {
                case 1:
                    createAccount(scanner, accountDetails);
                    break;
                case 2:
                    viewAccountDetails(scanner, accountDetails);
                    break;
                case 3:
                    updateAccount(scanner, accountDetails);
                    break;
                case 4:
                    deleteAccount(scanner, accountDetails);
                    break;
                case 5:
                    depositAmount(scanner, accountDetails);
                    break;
                case 6:
                    withdrawAmount(scanner, accountDetails);
                    break;
                case 7:
                    transferAmount(scanner, accountDetails);
                    break;
                case 8:
                    displayAccountDetails();
                    break;
                case 9:
                    System.out.println("Thanks for using..");
                    exit=true;
                    saveAccountDetails(accountDetails);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }        
        scanner.close();
    }

    //CREATE ACCOUNT
    private static void createAccount(Scanner scanner,Map<Integer,AccountDetails>accountDetails){
        System.out.println("Enter account number: ");
        int accountnumber=scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter name:" );
        String name=scanner.nextLine();
        synchronized(lock){
            AccountDetails account=AccountDetails.newBuilder()
                                                 .setAccountnumber(accountnumber)
                                                 .setName(name)
                                                 .setBalance(0)
                                                 .build();
            accountDetails.put(accountnumber,account);
            System.out.println("Account created successfully.\n");
        }
    }

    //VIEW ACCOUNT DETAILS
    private static void viewAccountDetails(Scanner scanner, Map<Integer, AccountDetails> accountDetails) {
        System.out.print("Enter account number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine();
        synchronized (lock){
            AccountDetails account = accountDetails.get(accountNumber);
            if (account != null) {
                System.out.println("Account Number: " + accountNumber);
                System.out.println("Name: " + account.getName());
                System.out.println("Balance: " + account.getBalance()+"\n");
            } else {
                System.out.println("Account not found.\n");
            }
        }
    }

    //UPDATE ACCOUNT
    private static void updateAccount(Scanner scanner,Map<Integer,AccountDetails>accountDetails){
        System.out.println("Enter your account number: ");
        int accountnum=scanner.nextInt();
        scanner.nextLine();
        synchronized (lock){
            AccountDetails account=accountDetails.get(accountnum);
            if(account!=null){
                System.out.println("Enter your new name: ");
                String newname=scanner.nextLine();
                account=AccountDetails.newBuilder(account).setName(newname).build();
                accountDetails.put(accountnum,account);
                System.out.println("Account name updated successfully.");
            }else{
                System.out.println("Account not found.");
            }
        }
    }

    //DELETE ACCOUNT
    private static void deleteAccount(Scanner scanner,Map<Integer,AccountDetails>accountDetails){
        System.out.println("Enter your account number: ");
        int accountnum=scanner.nextInt();
        scanner.nextLine();
        synchronized (lock){
            if(accountDetails.remove(accountnum)!=null){
                System.out.println("Account deleted successfully.");
            }else{
                System.out.println("Account not found.");
            }
        }
    }

    //DEPOSIT AMOUNT
    private static void depositAmount(Scanner scanner,Map<Integer,AccountDetails> accountDetails){
        System.out.println("Enter your account number: ");
        int accountnum=scanner.nextInt();
        scanner.nextLine();
        synchronized (lock){
            AccountDetails account=accountDetails.get(accountnum);
            if(account!=null){
                System.out.println("Enter amount to be deposited: ");
                float amount=scanner.nextFloat();
                scanner.nextLine();

                if(amount>0){
                    account=AccountDetails.newBuilder(account).setBalance(account.getBalance()+amount).build();
                    accountDetails.put(accountnum,account);
                    lock.notifyAll();
                    System.out.println("\n**Amount Deposited Successfully**\n");
                }else{
                    System.out.println("Invalid amount");
                }
            }else{
                System.out.println("Account not found.");
            }
        }
    }

    //DEPOSIT AMOUNT TESTING
    public static void depositAmount(Map<Integer,AccountDetails> accountDetails,int accountnum,float amount){
        synchronized (lock){
            AccountDetails account=accountDetails.get(accountnum);
            if(account!=null){
                if(amount>0){
                    account=AccountDetails.newBuilder(account).setBalance(account.getBalance()+amount).build();
                    accountDetails.put(accountnum,account);
                    lock.notifyAll();
                    System.out.println("\n**Amount Deposited Successfully**\n");
                }else{
                    System.out.println("Invalid amount");
                }
            }else{
                System.out.println("Account not found.");
            }
        }
    }

    //WITHDRAW AMOUNT
    private static void withdrawAmount(Scanner scanner,Map<Integer,AccountDetails> accountDetails){
        System.out.println("Enter your account number: ");
        int accountnum=scanner.nextInt();
        scanner.nextLine();
        synchronized (lock){
            AccountDetails account=accountDetails.get(accountnum);
            if(account!=null){
                System.out.println("Enter amount to be withdrawn: ");
                float amount=scanner.nextFloat();
                scanner.nextLine();
                if(amount>0 && amount<=account.getBalance()){
                    account=AccountDetails.newBuilder(account).setBalance(account.getBalance()-amount).build();
                    accountDetails.put(accountnum,account);
                    lock.notifyAll();
                    System.out.println("\nAmount withdrawn successfully");
                }else{
                    System.out.println("Invalid amount");
                }
            }else{
                System.out.println("Account not found.");
            }
        }
    }

    //WITHDRAW AMOUNT TESTING
    public static void withdrawAmount(Map<Integer,AccountDetails> accountDetails,int accountnum,float amount){
        synchronized (lock){
            AccountDetails account=accountDetails.get(accountnum);
            if(account!=null){
                if(amount>0 && amount<=account.getBalance()){
                    account=AccountDetails.newBuilder(account).setBalance(account.getBalance()-amount).build();
                    accountDetails.put(accountnum,account);
                    lock.notifyAll();
                }else{
                    System.out.println("Invalid amount");
                }
            }else{
                System.out.println("Account not found.");
            }
        }
    }

    //TRANSFER AMOUNT FROM ONE ACCOUNT TO ANOTHER
    public static void transferAmount(Scanner scanner,Map<Integer,AccountDetails>accountDetails){
        System.out.println("Enter your account no: ");
        int fromAccountNum=scanner.nextInt();
        scanner.nextLine();
        synchronized (lock){
            AccountDetails fromAccount= accountDetails.get(fromAccountNum);

            if(fromAccount!=null){
                System.out.println("Enter the recipient account no: ");
                int toAccountNum=scanner.nextInt();
                scanner.nextLine();
                AccountDetails toAccount=accountDetails.get(toAccountNum);

                if(toAccount!=null){
                    System.out.println("Enter the amount to transfer: ");
                    float amount=scanner.nextFloat();

                    if(amount>0 && amount<=fromAccount.getBalance()){
                        fromAccount=AccountDetails.newBuilder(fromAccount).setBalance(fromAccount.getBalance()-amount).build();
                        toAccount=AccountDetails.newBuilder(toAccount).setBalance(toAccount.getBalance()+amount).build();
                        accountDetails.put(fromAccountNum,fromAccount);
                        accountDetails.put(toAccountNum, toAccount);
                        saveAccountDetails(accountDetails);
                        lock.notifyAll();
                        System.out.println("\n---Amount has been transferred successfully---");
                    }else{
                        System.out.println("Insufficient funds or Invalid Amount");
                    }
                }else{
                    lock.notifyAll();
                    System.out.println("Recipient account not found.");
                }
            }else{
                lock.notifyAll();
                System.out.println("Invalid account number.");
            }
        }
    }

    //TRANSFER AMOUNT TESTING
    public static synchronized void transferAmount(Map<Integer,AccountDetails>accountDetails,int fromAccountNum,int toAccountNum,float amount){
        synchronized (lock){
            AccountDetails fromAccount= accountDetails.get(fromAccountNum);

            if(fromAccount!=null){
                AccountDetails toAccount=accountDetails.get(toAccountNum);

                if(toAccount!=null){
                    if(amount>0 && amount<=fromAccount.getBalance()){
                        fromAccount=AccountDetails.newBuilder(fromAccount).setBalance(fromAccount.getBalance()-amount).build();
                        toAccount=AccountDetails.newBuilder(toAccount).setBalance(toAccount.getBalance()+amount).build();
                        accountDetails.put(fromAccountNum,fromAccount);
                        accountDetails.put(toAccountNum, toAccount);
                        saveAccountDetails(accountDetails);
                        lock.notifyAll();
                        System.out.println("\n---Amount has been transferred successfully---");
                    }else{
                        System.out.println("Insufficient funds or Invalid Amount");
                    }
                }else{
                    lock.notifyAll();
                    System.out.println("Recipient account not found.");
                }
            }else{
                lock.notifyAll();
                System.out.println("Invalid account number.");
            }
        }
    }


    //DISPLAY ACCOUNT DETAILS
    private static void displayAccountDetails(){
        System.out.println("Bank Account Details loaded from file:\n");
        Map<Integer,AccountDetails> loadedAccountDetails= loadAccountDetails();
        for (Map.Entry<Integer, AccountDetails> entry : loadedAccountDetails.entrySet()) {
            int accountNumber = entry.getKey();
            AccountDetails account = entry.getValue();
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Name: " + account.getName());
            System.out.println("Balance: "+account.getBalance());
            System.out.println();
        }
    }

    //SAVE ACCOUNT DETAILS TO THE FILE
    public static void saveAccountDetails(Map<Integer, AccountDetails> accountDetails) {
        synchronized (lock){
        try (FileOutputStream fos = new FileOutputStream(account_file_name)) {
            for (AccountDetails account : accountDetails.values()) {
                account.writeDelimitedTo(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
    
    
    //LOAD ACCOUNT DETAILS FROM THE FILE TO THE HASHMAP
    public static Map<Integer, AccountDetails> loadAccountDetails() {
        synchronized (lock){
        Map<Integer, AccountDetails> accountDetails = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(account_file_name)) {
            while (fis.available() > 0) {
                AccountDetails account = AccountDetails.parseDelimitedFrom(fis);
                accountDetails.put(account.getAccountnumber(), account);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing data file found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accountDetails;
        }
    }   
}
