package com.coursework.wfc;

import java.io.File;
import java.io.IOException;
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
        } catch (
                IOException e) {
            System.out.println(e);
        }

        return alreadyAttend;
    }

}