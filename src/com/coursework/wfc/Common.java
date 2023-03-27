package com.coursework.wfc;

import java.io.*;
import java.util.Scanner;

public class Common {
    //Check whether booking number is already exist
    public static boolean isBookingNoAlreadyExist(String bookingNo) {
        boolean isExist = false;
        try {
            String filepath = "textFiles\\bookings.txt";
            Scanner x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");
            String Id;
            String bookingNum, cusNo, cusName, groupNo, day, week, validGroup = null, validCus = null;

            while (x.hasNext()) {
                bookingNum = x.next();
                cusNo = x.next();
                cusName = x.next();
                groupNo = x.next();
                day = x.next();
                week = x.next();

                if (bookingNum.equals(bookingNo)) {
                    isExist = true;
                    break;
                }
            }
            x.close();
        } catch (
                IOException e) {
            System.out.println(e);
        }

        return isExist;
    }

    //validate customer already attended for booked session
    public static boolean isValidateAttendance(String bookingNo) {
        boolean alreadyAttend = false;
        try {
            String filepath = "textFiles\\attendanceAndFeedback.txt";
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

                if (Id.equals(bookingNo)) {
                    alreadyAttend = true;
                    break;
                }
            }
            x.close();
        } catch (
                IOException e) {
            System.out.println(e);
        }

        return alreadyAttend;
    }

    //validate duplicate bookings (customer trying to make more than one booking for same session in group (week,group) )
    public static boolean validateDuplicateBookings(String group, String session, String customerNo){
        boolean isDuplicate = false;
        try {
            String filepath = "textFiles\\bookings.txt";
            Scanner x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");
            String Id;
            String bookingNum, cusNo, cusName, groupNo, day, week;

            while (x.hasNext()) {
                bookingNum = x.next();
                cusNo = x.next();
                cusName = x.next();
                groupNo = x.next();
                day = x.next();
                week = x.next();

                if (cusNo.equals(customerNo) && groupNo.equals(group) && week.equals(session)) {
                    isDuplicate = true;
                    break;
                }
                else {
                    isDuplicate = false;
                }
            }
            x.close();
        } catch (
                IOException e) {
            System.out.println(e);
        }
        return isDuplicate;
    }

    //Get the customer number for the given booking number
    public static String getCustomerNo(String bookingNo){
        String cusNo="";
        try {
            String filepath = "textFiles\\bookings.txt";
            Scanner x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");
            String Id;
            String bookingNum, customerNo, cusName, groupNo, day, week;

            while (x.hasNext()) {
                bookingNum = x.next();
                customerNo = x.next();
                cusName = x.next();
                groupNo = x.next();
                day = x.next();
                week = x.next();

                if (bookingNum.equals(bookingNo.toUpperCase())) {
                    cusNo = customerNo;
                    break;
                }
            }
            x.close();
        } catch (
                IOException e) {
            System.out.println(e);
        }
        return cusNo;
    }

   /*
   public static void main(String[] args) {
       // boolean sDD = validateDuplicateBookings("gp4","3","CUS004");
       // System.out.println(sDD);

    }

    public static void editBooking(String editBookingId,String editGroupId,String editDay,String editWeek){
        String tempFile="textFiles\\tempedit.txt";
        String filepath="textFiles\\bookings.txt";
        boolean boolFound = false;
        File oldFile = new File(filepath);
        File newFile = new File(tempFile);
        String Id=""; String cusName="", cusNo=""; String group=""; String day=""; String week="";

        try {
            FileWriter fw= new FileWriter(tempFile,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            Scanner x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");

            while (x.hasNext()){
                Id = x.next();
                cusNo = x.next();
                cusName = x.next();
                group = x.next();
                day = x.next();
                week = x.next();

                if(Id.equals(editBookingId)){
                    pw.print(editBookingId + "," + cusNo + "," + cusName + "," + editGroupId + "," + editDay+ "," + editWeek+ "\n");
                    boolFound=true;

                }
                else{
                    pw.print(Id + "," + cusNo + "," + cusName + "," + group + "," + day+ "," + week+ "\n");
                }
            }
            x.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File(filepath);
            newFile.renameTo(dump);


        }catch (Exception e){
            System.out.println("---Error---");
        }
    }
*/
}