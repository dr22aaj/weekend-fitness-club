package com.coursework.wfc;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

enum LessonPrice{
    YOGA(12),ZUMBA(10),SPIN(8),BODYSCULPT(10);
    double price;
    LessonPrice(double price){
        this.price = price;
    }
}
/*
This is the main class of the Report generation.
Relevant report class will be calling from this class.
**/
public class Report {
    static Scanner console = new Scanner(System.in);
    private static String customerName;
    private static String customerNo;
    private static String group;
    private static String day;
    private static String week;
    private static String bookingId;
    private static String feedback;
    private static String attended;
    private static String rate;
    static final String filepath = "textFiles\\bookings.txt";
    static final String filepathA = "textFiles\\attendanceAndFeedback.txt";

    //call the number of customers per lesson on each day method
    protected static void getNumOfCustomers(String month){
        NumOfCustomers.getNumOfCustomers(month);
    }

   // call the type of fitness lessons which has generated the highest income
   protected static void calIncomePerLesson(String month){
        CallIncomePerLesson.calIncomePerLesson(month);
    }

    public static String getCustomerName() {
        return customerName;
    }

    public static void setCustomerName(String customerName) {
        Report.customerName = customerName;
    }

    public static String getCustomerNo() {
        return customerNo;
    }

    public static void setCustomerNo(String customerNo) {
        Report.customerNo = customerNo;
    }

    public static String getGroup() {
        return group;
    }

    public static void setGroup(String group) {
        Report.group = group;
    }

    public static String getDay() {
        return day;
    }

    public static void setDay(String day) {
        Report.day = day;
    }

    public static String getWeek() {
        return week;
    }

    public static void setWeek(String week) {
        Report.week = week;
    }

    public static String getBookingId() {
        return bookingId;
    }

    public static void setBookingId(String bookingId) {
        Report.bookingId = bookingId;
    }

    public static String getFeedback() {
        return feedback;
    }

    public static void setFeedback(String feedback) {
        Report.feedback = feedback;
    }

    public static String getAttended() {
        return attended;
    }

    public static void setAttended(String attended) {
        Report.attended = attended;
    }

    public static String getRate() {
        return rate;
    }

    public static void setRate(String rate) {
        Report.rate = rate;
    }
}
