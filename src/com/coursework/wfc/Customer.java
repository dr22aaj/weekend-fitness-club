package com.coursework.wfc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Customer {
    static Scanner console = new Scanner(System.in);

    //Read data from the text file
    public static void readCustomerFile(){
        try {
            String filePath= "textFiles\\customers.txt";
            FileReader reader = new FileReader(filePath);
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: Validate entered customer number is valid or not
    public static boolean validateCustomerNo(String customerNo){
        boolean isValidCustomer = false;
        try {
            String filepath = "textFiles\\customers.txt";
            Scanner x = new Scanner(new File(filepath));
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
        } catch (
                IOException e) {
            System.out.println(e);
        }
        return isValidCustomer;
    }
}
