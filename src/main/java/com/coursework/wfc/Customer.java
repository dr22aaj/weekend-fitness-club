/*
Created By: Dinushi Ranasinghe
Created Date: 16/March/2023
Description: Customer related methods are handel under this class
**/
package com.coursework.wfc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Customer {
    static Scanner console = new Scanner(System.in);
    static final String customerFilePath = "C:\\textFiles\\customers.txt";

    //Read data from the text file
    protected static void readCustomerFile(){
        try {
            boolean isValidPath = Common.validateFilePath(customerFilePath);
            if(isValidPath){
                FileReader reader = new FileReader(customerFilePath);
                int data = reader.read();
                while (data != -1){
                    System.out.print((char)data);
                    data = reader.read();
                }
                reader.close();
                System.out.println();
                System.out.println("Do you want to continue? (y/n) ");
                String rst=console.next().toLowerCase();
                if(rst.equals("y")){
                    PrintMain.printMenu();
                }
                else {
                    System.out.println("Thank You & Have a Great Day!");
                }
            }
            else {
                System.out.println("File path does not exist!");
                PrintMain.printMenu();
            }
        console.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Validate entered customer number is valid or not
    public static boolean validateCustomerNo(String customerNo){
        boolean isValidCustomer = false;
        try {
            boolean isValidPath = Common.validateFilePath(customerFilePath);
            if(isValidPath){
                Scanner x = new Scanner(new File(customerFilePath));
                x.useDelimiter("[,\n]");
                String Id = null, cusName, groupNo, isAttend = null,feedback, session = null, attenging = null;
                String rating;

                while (x.hasNext()) {
                    Id = x.next();
                    cusName = x.next();
                    session = x.next();
                    isAttend = x.next();
                    feedback = x.next();
                    rating = x.next();

                    if (Id.equals(customerNo)) {
                        isValidCustomer = true;
                        break;
                    }
                }
                x.close();
            }
            else {
                System.out.println("File path does not exist!");
                PrintMain.printMenu();
            }
        } catch (
                IOException e) {
            System.out.println(e);
        }
        return isValidCustomer;
    }
}
