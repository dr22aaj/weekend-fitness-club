/*
Created By: Dinushi Ranasinghe
Created Date: 22/March/2023
Description: All the customer feedback/attendance related methods are handel
             under this class
**/
package com.coursework.wfc;

import java.io.*;
import java.util.Scanner;

public class Feedback {
    static Scanner console = new Scanner(System.in);
    private static String feedback;
    private static String isAttend;
    private static int rating;
    private static String bookingNo;
    private static String group;
    private static String customerName;
    private static String cusNo;
    private static String week;
    private static String day;
    private static String status;
    static final String filepath="C:\\textFiles\\attendanceAndFeedback.txt";
    static final String bookingfilepath="C:\\textFiles\\bookings.txt";

    public Feedback(){

    }
    public Feedback(String feedback, String isAttend, int rating, String week) {
        this.feedback = feedback;
        this.isAttend = isAttend;
        this.rating = rating;
        this.week = week;
    }

    public static String getFeedBackData(){
        return   bookingNo +"," + customerName +"," + group +","+ day +"," + week +"," + isAttend +"," +feedback  +"," +rating ;
    }

    public static void updateAttendanceAndFeedback(){
       try {
           boolean isValidBookingNo = false;
           boolean isUpdate = false;
           Scanner s = new Scanner(System.in);

           System.out.println("Please enter booking number:  ");
           bookingNo=console.next().toUpperCase();

           boolean isValidPath = Common.validateFilePath(filepath);
           if(isValidPath){
               //validate entered booking no,customer name and group
               //read the data from bookings text file
               Scanner x = new Scanner(new File(bookingfilepath));
               x.useDelimiter("[,\n]");
               String Id; String validGroup = null,validCus = null, validWeek=null,validDay = null;

               while (x.hasNext()){
                   Id = x.next();
                   cusNo = x.next();
                   customerName = x.next();
                   group = x.next();
                   day = x.next();
                   week = x.next();
                   status = x.next();

                   if(Id.equals(bookingNo)){
                       validGroup = getGroup();
                       validCus = getCustomerName();
                       isValidBookingNo=true;
                       validWeek=getWeek();
                       validDay=getDay();
                       status=getStatus();
                       break;
                   }
               }
               if(!isValidBookingNo){
                   System.out.println("Entered booking number is invalid! ");
                   System.out.println("------------------------------------");
                   updateAttendanceAndFeedback();
               }
               else{
                   System.out.println("Please enter customer name:  ");
                   customerName=s.nextLine().toLowerCase();
                   if(!validCus.equals(customerName)){
                       System.out.println("Entered customer name is invalid! ");
                       System.out.println("------------------------------------");
                       updateAttendanceAndFeedback();
                   }
                   else {
                       System.out.println("Please enter the session you attended:(GP1/GP2/GP3/GP4)  ");
                       group=console.next().toLowerCase();
                       if(!validGroup.equals(group)){
                           System.out.println("Entered session is invalid! ");
                           System.out.println("------------------------------------");
                           updateAttendanceAndFeedback();
                       }
                       else {
                           System.out.println("Please enter the week you attended:(1/2/3/4/5/6/7/8)  ");
                           int weekInt = Integer.parseInt(console.next());
                           week=String.valueOf(weekInt).toString();

                           //validated entered week is correct according to the booking no.
                           if(!week.equals(validWeek)){
                               System.out.println("Entered week is not relevant to the booking number! ");
                               System.out.println("------------------------------------");
                               updateAttendanceAndFeedback();
                           }
                           else {
                               System.out.println("Did you attended for the session? (y/n)  ");
                               isAttend=console.next().toLowerCase();

                               if(isAttend.equals("y")){
                                   System.out.println("Please enter your feedback:  ");
                                   feedback = s.nextLine();

                                   System.out.println("Please enter your rating from 1 to 5 " +
                                           "(1: Very dissatisfied, 2: Dissatisfied, 3: Ok, 4: Satisfied, 5: Very Satisfied):  ");
                                   rating= Integer.parseInt(console.next());
                                   isUpdate=true;
                               }
                               else {
                                   isAttend = "n";
                                   feedback="-";
                                   rating=0;
                                   week="0";
                               }

                                  //Validate if entered booking number is attendance is already updated.
                                   boolean isExist = isFeedbackGiven(bookingNo);
                                   if(isExist){
                                       System.out.println("Attendance/Feedback already updated!  ");
                                       System.out.println("------------------------------------");
                                       System.out.println();
                                       System.out.println("Do you want to continue? (y/n) ");
                                       String rst=console.next().toLowerCase();
                                       if(rst.equals("y")){
                                           PrintMain.printMenu();
                                       }else{
                                           System.out.println("Thank You & Have a Great Day!");
                                       }
                                   }
                                   //Validate if entered booking number is cancelled
                                   boolean isCancelled = Common.isBookingNoCancelled(bookingNo);
                                   if(isCancelled){
                                       System.out.println("Entered booking number already cancelled!  ");
                                       System.out.println("------------------------------------");
                                       System.out.println();
                                       System.out.println("Do you want to continue? (y/n) ");
                                       String rst=console.next().toLowerCase();
                                       if(rst.equals("y")){
                                           PrintMain.printMenu();
                                       }else {
                                           System.out.println("Thank You & Have a Great Day!");
                                       }
                                   }

                           if((!isExist) && (!isCancelled))
                           {
                               //write the attendance, feedback and rating into attendanceAndFeedback text file
                               FileWriter writer=new FileWriter(filepath,true);
                               writer.append(getFeedBackData());
                               writer.append("\n");
                               writer.close();
                               x.close();

                               if(isUpdate){
                                   //update status in booking file
                                   Bookings.updateBookingStatus(bookingNo,"attended");
                               }

                               System.out.println("Attendance and Feedback added successfully! ");
                               System.out.println();
                               System.out.println("Do you need to update another Attendance and Feedback? (y/n)");
                               String rst=console.next().toLowerCase();
                               if(rst.equals("y")){
                                   updateAttendanceAndFeedback();
                               }
                               else {PrintMain.printMenu();}

                           }

                           }
                       }
                   }
               }
           }
           else {
               System.out.println("File path does not exist!");
               PrintMain.printMenu();
           }
           // closing the scanner stream
           console.close();
        } catch (NumberFormatException ex) {
           System.out.println("Error: Input value is not a number");
           System.out.println("------------------------------------");
           PrintMain.printMenu();
       }
       catch (IOException e) {
            System.out.println(e);
        }
    }

    //Validate feedback is already given for the entered booking number.
    public static boolean isFeedbackGiven(String bookingNo) throws FileNotFoundException {
        boolean isAttedanceUpdate = false;

        boolean isValidPath = Common.validateFilePath(filepath);
        if(isValidPath){
            //read the data from bookings text file
            Scanner x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");
            String bookingNum;

            while (x.hasNext()){
                bookingNum = x.next();
                if(bookingNum.equals(bookingNo)){
                    isAttedanceUpdate = true;
                    break;
                }
            }
        }
        else {
            System.out.println("File path does not exist!");
            PrintMain.printMenu();
        }
        return isAttedanceUpdate;
    }

    public static String getFeedback() {
        return feedback;
    }
    public static void setFeedback(String feedback) {
        Feedback.feedback = feedback;
    }
    public static String getIsAttend() {
        return isAttend;
    }
    public static void setIsAttend(String isAttend) {
        Feedback.isAttend = isAttend;
    }
    public static int getRating() {
        return rating;
    }
    public static void setRating(int rating) {
        Feedback.rating = rating;
    }
    public static String getBookingNo() {
        return bookingNo;
    }
    public static void setBookingNo(String bookingNo) {
        Feedback.bookingNo = bookingNo;
    }
    public static String getGroup() {
        return group;
    }
    public static void setGroup(String group) {
        Feedback.group = group;
    }
    public static String getCustomerName() {
        return customerName;
    }
    public static void setCustomerName(String customerName) {
        Feedback.customerName = customerName;
    }
    public static String getCusNo() {
        return cusNo;
    }
    public static void setCusNo(String cusNo) {
        Feedback.cusNo = cusNo;
    }
    public static String getWeek() {
        return week;
    }
    public static void setWeek(String week) {
        Feedback.week = week;
    }
    public static String getDay() {
        return day;
    }
    public static void setDay(String day) {
        Feedback.day = day;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        Feedback.status = status;
    }
}
