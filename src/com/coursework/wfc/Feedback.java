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
    private static String week;

    public Feedback(String feedback, String isAttend, int rating, String week) {
        this.feedback = feedback;
        this.isAttend = isAttend;
        this.rating = rating;
        this.week = week;
    }

    public static String getFeedBackData(){
        return   bookingNo +"," + customerName +"," + group +"," + week +"," + isAttend +"," +feedback  +"," +rating ;
    }

    public static void updateAttendanceAndFeedback(){
       try {
           boolean isValidBookingNo = false;
           System.out.println("Please enter booking number:  ");
           bookingNo=console.next().toUpperCase();

           //validate entered booking no,customer name and group
           //read the data from bookings text file
           String filepath="textFiles\\bookings.txt";
           Scanner x = new Scanner(new File(filepath));
           x.useDelimiter("[,\n]");
           String Id; String cusNo, cusName, groupNo, day, week, validGroup = null,validCus = null, validWeek=null;

           while (x.hasNext()){
               Id = x.next();
               cusNo = x.next();
               cusName = x.next();
               groupNo = x.next();
               day = x.next();
               week = x.next();

               if(Id.equals(bookingNo)){
                   validGroup = groupNo;
                   validCus = cusName;
                   isValidBookingNo=true;
                   validWeek=week;
                   break;
               }
           }
           if(!isValidBookingNo){
               System.out.println("Entered booking number is invalid! ");
               System.out.println("------------------------------------");
               updateAttendanceAndFeedback();
           }
           else {
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
                   }
               }
               System.out.println("Please enter customer name:  ");
               customerName=console.next().toLowerCase();
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
                       week=console.next();
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
                               feedback =console.next().toLowerCase();
                               System.out.println("Please enter your rating from 1 to 5 (1: Very dissatisfied, 2: Dissatisfied, 3: Ok, 4: Satisfied, 5: Very Satisfied):  ");
                               rating=console.nextInt();
                           }
                           else {
                               isAttend = "n";
                               feedback="-";
                               rating=0;
                               week="0";
                           }
                           //write the attendance, feedback and rating into attendanceAndFeedback text file
                           String filename = "textFiles\\attendanceAndFeedback.txt";
                           FileWriter writer=new FileWriter(filename,true);
                           writer.append(getFeedBackData());
                           writer.append("\n");
                           writer.close();
                           System.out.println("Attendance and Feedback added successfully! ");
                           System.out.println();
                           System.out.println("Do you need to update another Attendance and Feedback? (y/n)");
                           String rst=console.next().toLowerCase();
                           if(rst.equals("y")){
                               updateAttendanceAndFeedback();
                           }
                           else PrintMain.printMenu();
                       }
                   }
               }

           }

        } catch (IOException e) {
            System.out.println(e);
        }
        // closing the scanner stream
        console.close();
    }

    public static boolean isFeedbackGiven(String bookingNo) throws FileNotFoundException {
        boolean isAttedanceUpdate = false;

        //read the data from bookings text file
        String filepath="textFiles\\attendanceAndFeedback.txt";
        Scanner x = new Scanner(new File(filepath));
        x.useDelimiter("[,\n]");
        String bookingNum; //,customerName,group,isAttend,feedback,rating;

        while (x.hasNext()){
            bookingNum = x.next();
            /*customerName = x.next();
            group = x.next();
            isAttend = x.next();
            feedback = x.next();
            rating = x.next();
            */
            if(bookingNum.equals(bookingNo)){
                isAttedanceUpdate = true;
                break;
            }
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
}
