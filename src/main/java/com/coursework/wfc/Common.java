/*
Created By: Dinushi Ranasinghe
Created Date: 24/March/2023
Description: All the common validation related methods are handel
             under this class
**/
package com.coursework.wfc;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.*;
import java.util.Scanner;

public class Common {
    private static String customerName;
    private static String customerNo;
    private static String group;
    private static String day;
    private static String week;
    private static String bookingId;
    private static String feedback;
    private static String attended;
    private static String rate;
    private static String status;

    static final String filepath = "C:\\textFiles\\bookings.txt";
    static final String filepathA = "C:\\textFiles\\attendanceAndFeedback.txt";

    //Check whether booking number is already exist
    protected static boolean isBookingNoAlreadyExist(String bookingNo) {
        boolean isExist = false;
        try {
            boolean isValidPath = Common.validateFilePath(filepath);
            if(isValidPath){
                Scanner x = new Scanner(new File(filepath));
                x.useDelimiter("[,\n]");

                while (x.hasNext()) {
                    bookingId = x.next();
                    customerNo = x.next();
                    customerName = x.next();
                    group = x.next();
                    day = x.next();
                    week = x.next();
                    status = x.next();

                    if (bookingId.equals(bookingNo)) {
                        isExist = true;
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

        return isExist;
    }

    //validate customer already attended for booked session
    protected static boolean isValidateAttendance(String bookingNo) {
        boolean alreadyAttend = false;
        try {
            boolean isValidPath = Common.validateFilePath(filepath);
            if(isValidPath){
                Scanner x = new Scanner(new File(filepathA));
                x.useDelimiter("[,\n]");
                String Id = null,isAttend = null,feedback, session = null;
                String rating;

                while (x.hasNext()) {
                    Id = x.next();
                    customerName = x.next();
                    group = x.next();
                    day =x.next();
                    session = x.next();
                    isAttend = x.next();
                    feedback = x.next();
                    rating = x.next();

                    if (Id.equals(bookingNo)) {
                        alreadyAttend = true;
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

        return alreadyAttend;
    }

    //validate duplicate bookings (customer trying to make more than one booking for same session
    // in group (week,group) )
    protected static boolean validateDuplicateBookings(String groupNo, String session, String cusNo){
        boolean isDuplicate = false;
        try {
            boolean isValidPath = Common.validateFilePath(filepath);
            if(isValidPath){
                Scanner x = new Scanner(new File(filepath));
                x.useDelimiter("[,\n]");
                String Id;

                while (x.hasNext()) {
                    bookingId = x.next();
                    customerNo = x.next();
                    customerName = x.next();
                    group = x.next();
                    day = x.next();
                    week = x.next();
                    status = x.next();

                    if (customerNo.equals(cusNo) && group.equals(groupNo) && week.equals(session) && (status.equals("booked") || status.equals("attended"))) {
                        isDuplicate = true;
                        break;
                    }
                    else {
                        isDuplicate = false;
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
        return isDuplicate;
    }

    //Get the customer number for the given booking number
    protected static String getCustomerNo(String bookingNo){
        String cusNo="";
        try {
            boolean isValidPath = Common.validateFilePath(filepath);
            if(isValidPath){
                Scanner x = new Scanner(new File(filepath)); //read data from file
                x.useDelimiter("[,\n]");
                String Id;

                while (x.hasNext()) {
                    bookingId = x.next();
                    customerNo = x.next();
                    customerName = x.next();
                    group = x.next();
                    day = x.next();
                    week = x.next();
                    status = x.next();

                    if (bookingId.equals(bookingNo.toUpperCase())) { // check the booking number is existing in data file
                        cusNo = customerNo;
                        break;
                    }
                }
                x.close(); //close the scanner
            }
            else {
                System.out.println("File path does not exist!");
                PrintMain.printMenu();
            }

        } catch (
                IOException e) {
            System.out.println(e);
        }
        return cusNo;
    }

    //Validate text file path is already exist
    protected static boolean validateFilePath(String filepath){
        boolean isValidFilePath = false;
        Path fPath = Paths.get(filepath);
        if(Files.exists(fPath)){
            isValidFilePath=true;
        }
        else {
            isValidFilePath=false;
        }
        return isValidFilePath;
    }

    protected static boolean isBookingNoCancelled(String bookingNo){
        boolean isCancelled = false;
        String chkStatus = "cancelled";
        try {
            boolean isValidPath = Common.validateFilePath(filepath);
            if(isValidPath){
                Scanner x = new Scanner(new File(filepath)); //read data from file
                x.useDelimiter("[,\n]");
                String Id;

                while (x.hasNext()) {
                    bookingId = x.next();
                    customerNo = x.next();
                    customerName = x.next();
                    group = x.next();
                    day = x.next();
                    week = x.next();
                    status = x.next();

                    if (bookingId.equals(bookingNo.toUpperCase())) { // check the booking number is existing in data file
                        chkStatus = status;
                        isCancelled =true;
                        break;
                    }
                }
                x.close(); //close the scanner
            }
            else {
                System.out.println("File path does not exist!");
                PrintMain.printMenu();
            }

        } catch (
                IOException e) {
            System.out.println(e);
        }
        return  isCancelled;
    }
}