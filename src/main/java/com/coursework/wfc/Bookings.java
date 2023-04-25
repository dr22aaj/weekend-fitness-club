/*
Created By: Dinushi Ranasinghe
Created Date: 21/March/2023
Description: This is the booking class of the WFC programme.
             Booking related all functionality are handel under this class
             (create booking, edit booking, view booking, cancel booking)
**/
package com.coursework.wfc;

import java.io.*;
import java.util.Scanner;

public class Bookings {
    static Scanner console = new Scanner(System.in);
    // attributes
    private static String customerName;
    private static String customerNo;
    private static String group;
    private static String day;
    private static String week;
    private static String bookingId;
    private static String status;

    static final String filePath= "C:\\textFiles\\bookings.txt";
    static final String tempFile="C:\\textFiles\\tempnew.txt";

    //Constructor

    public Bookings() {
    }
    public Bookings(String bookingId , String customerNo, String customerName, String group, String day, String week, String status){
        this.bookingId=bookingId;
        this.customerName=customerName;
        this.group=group;
        this.day=day;
        this.week=week;
        this.customerNo=customerNo;
        this.status=status;
    }
    public static String getBookingData(){
        return   bookingId +"," + customerNo +"," + customerName +"," + group +"," +day  +"," +week+"," +status ;
    }

    public static String getBookingDataN(){
        return   "Booking ID:" + bookingId  + " Customer No:" + customerNo + " Customer Name:" + customerName + " Group:"+ group + " Day:" + day + " Week:" +week ;
    }

    //view booking data
    public static void readBookingFile(){
        try {
            boolean isValidPath = Common.validateFilePath(filePath);
            if(isValidPath){
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
                else {
                    System.out.println("Thank You & Have a Great Day!");
                }
                reader.close();
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
    //save the booking data in booking text file
    public static void createBooking() {
        try{
            bookingId = generateBookingNo();
            System.out.println("Enter customer number :");
            customerNo=console.next().toUpperCase();
            System.out.println("Enter customer name :");
            customerName=console.next().toString();
            System.out.println("Enter the selected date (1 - saturday / 2 - sunday) :");
            int dayInt = Integer.parseInt(console.next());
            if(dayInt == 1){
                day = "saturday";
            } else if (dayInt == 2) {
                day = "sunday";
            }else{
                System.out.println("Error: Invalid input!");
                createBooking();
            }
            if(day.equals("saturday")){
                System.out.println("Enter the selected group (GP1, GP2) : ");
                group=console.next().toLowerCase();
                if(!(group.equals("gp1")||group.equals("gp2"))){
                    System.out.println("Entered group is invalid! ");
                    Bookings.createBooking();
                }
            }else if(day.equals("sunday")){
                System.out.println("Enter the selected group (GP3, GP4) : ");
                group=console.next().toLowerCase();
                if(!(group.equals("gp3")||group.equals("gp4"))){
                    System.out.println("Entered group is invalid! ");
                    Bookings.createBooking();
                }
            }
            else {
                System.out.println("Entered day is invalid!");
                createBooking();
            }
            System.out.println("Enter the selected week (1,2,3,4,5,6,7,8) :");
            int weekInt = Integer.parseInt(console.next());
            week=String.valueOf(weekInt).toString();

            if (weekInt < 0 || weekInt > 8) {
                System.out.println("Entered week is invalid!");
                createBooking();
            }

            //validate duplicate bookings (customer trying to make more than one booking for same session in group (week,group,customer no) )
            boolean isDuplicateBooking = Common.validateDuplicateBookings(group,week,customerNo);
            if(isDuplicateBooking){
                System.out.println("You have already made the booking!");
                System.out.println();
                System.out.println("Do you need to add another booking? (y/n)");
                String rst=console.next().toLowerCase();
                if(rst.equals("y")){
                    createBooking();
                }
                else PrintMain.printMenu();
            }
            else {
                //validate number of customers per each lesson (week) is exceeded or not
                boolean isExceeded = isExceedCusCount(week,group);
                if(isExceeded){
                    System.out.println("Cannot create booking. Selected session is already full!");
                    System.out.println();
                    System.out.println("Do you need to add the booking again? (y/n)");
                    String rst = console.next().toLowerCase();
                    if (rst.equals("y")) {
                        createBooking();
                    } else if (rst.equals("n")) {
                        PrintMain.printMenu();
                    }
                }
                else {
                    try {
                        boolean isValidPath = Common.validateFilePath(filePath);
                        status = "booked";
                        if(isValidPath){
                            FileWriter writer=new FileWriter(filePath,true);
                            writer.append(Bookings.getBookingData());
                            writer.append("\n");
                            writer.close();
                            System.out.println("Booking added successfully! Your booking number is: " + bookingId);
                            System.out.println();
                            System.out.println("Do you need to add another booking? (y/n)");
                            String rst=console.next().toLowerCase();
                            if(rst.equals("y")){
                                createBooking();
                            }
                            else PrintMain.printMenu();
                        }
                        else {
                            System.out.println("File path does not exist!");
                            PrintMain.printMenu();
                        }
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }
            // closing the scanner stream
            console.close();
        }
        catch (NumberFormatException ex) {
            System.out.println("Error: Input value is not a number");
            System.out.println("------------------------------------");
            createBooking();
        }
    }

    //save the updated the in booking text file
    public static void editBooking(String editBookingId,String editGroupId,String editDay,String editWeek){
        File oldFile = new File(filePath);
        File newFile = new File(tempFile);

        boolean boolFound = false;
        try {
            FileWriter fw= new FileWriter(tempFile,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            boolean isValidPath = Common.validateFilePath(filePath);
            if(isValidPath){
                Scanner x = new Scanner(new File(filePath));
                x.useDelimiter("[,\n]");

                while (x.hasNext()){
                    bookingId = x.next();
                    customerNo = x.next();
                    customerName = x.next();
                    group = x.next();
                    day = x.next();
                    week = x.next();
                    status = x.next();

                    if(bookingId.equals(editBookingId)){
                        pw.print(editBookingId + "," + customerNo + "," + customerName + "," + editGroupId + "," + editDay+ "," + editWeek+ "," + "changed"+ "\n");
                        boolFound=true;

                    }
                    else{
                        pw.print(bookingId + "," + customerNo + "," + customerName + "," + group + "," + day+ "," + week+"," + status+ "\n");
                    }
                }

                x.close();
                pw.flush();
                pw.close();
                oldFile.delete();
                File dump = new File(filePath);
                newFile.renameTo(dump);

                if(!boolFound){
                    System.out.println("---Invalid booking Id---");
                    System.out.println();
                    System.out.println("Do you need to edit booking? (y/n)");
                    String rst=console.next().toLowerCase();
                    if(rst.equals("y")){
                        editBooking();
                    }
                    else{
                        PrintMain.printMenu();
                    }
                }
                else{
                    System.out.println("Booking details updated successfully.");
                    System.out.println("Booking Number: " + editBookingId );
                    System.out.println();
                    PrintMain.printMenu();
                }
            }
            else {
                System.out.println("File path does not exist!");
                PrintMain.printMenu();
            }
            console.close();
        }catch (Exception e){
            System.out.println("---Error---");
        }
    }

    //get the booking details that need to be change
    public static void editBooking(){
        String editGroupId ="";
        try {
            System.out.println("Please enter booking id:  ");
            String editBookingId = console.next().toUpperCase();
            System.out.println("Enter the selected date ( 1 - saturday/ 2 - sunday) :");
            int dayInt = Integer.parseInt(console.next());
            String editDay = null;
            if(dayInt == 1){
                editDay = "saturday";
            } else if (dayInt == 2) {
                editDay = "sunday";
            }else{
                System.out.println("Error: Invalid input!");
                editBooking();
            }
            if (editDay.equals("saturday")) {
                System.out.println("Enter the selected group (GP1, GP2) : ");
                editGroupId = console.next().toLowerCase();
                if(!(editGroupId.equals("gp1")||editGroupId.equals("gp2"))){
                    System.out.println("Entered group is invalid! ");
                    Bookings.editBooking();
                }
            } else if (editDay.equals("sunday")) {
                System.out.println("Enter the selected group (GP3, GP4) : ");
                editGroupId = console.next().toLowerCase();
                if(!(editGroupId.equals("gp3")||editGroupId.equals("gp4"))){
                    System.out.println("Entered group is invalid! ");
                    Bookings.editBooking();
                }
            } else {
                System.out.println("Entered day is invalid!");
                editBooking();
            }
            System.out.println("Enter the selected week (1,2,3,4,5,6,7,8) :");
            int editWeekInt = Integer.parseInt(console.next());
            String editWeek = String.valueOf(editWeekInt).toString();

            if (editWeekInt < 0 || editWeekInt > 8) {
                System.out.println("Invalid week!");
                System.out.println();
                editBooking();
            }

            //Validate entered booking number
            boolean isExist = Common.isBookingNoAlreadyExist(editBookingId);
            if(!isExist){
                System.out.println("Booking number is invalid!");
                System.out.println("");
                editBooking();
            }

            //validate customer is already attend for entered booking number
            boolean isAttend = Common.isValidateAttendance(editBookingId);
            //Get the customer number by passing the booking number
            customerNo = Common.getCustomerNo(editBookingId);
            if (isAttend) {
                System.out.println("Cannot edit the entered booking number. Customer already attended!");
                System.out.println();
                System.out.println("Do you need to edit another booking? (y/n)");
                String rst = console.next().toLowerCase();
                if (rst.equals("y")) {
                    editBooking();
                } else {
                    PrintMain.printMenu();
                }
            }
            else
            {   //Validate if entered booking number is cancelled
                boolean isCancelled = Common.isBookingNoCancelled(editBookingId);
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
                else
                {
                    //Validate customer already booked for same session in group (week,group) )
                    boolean isHaving = Common.validateDuplicateBookings(editGroupId,editWeek,customerNo);
                    if(isHaving){
                        System.out.println("Cannot edit. You have already booked for this session!");
                        System.out.println();
                        System.out.println("Do you need to edit another booking? (y/n)");
                        String rst = console.next().toLowerCase();
                        if (rst.equals("y")) {
                            editBooking();
                        } else{
                            PrintMain.printMenu();
                        }
                    }
                    //validate number of customers per each lesson (week) is exceeded or not
                    boolean isExceeded = isExceedCusCount(editWeek,editGroupId);
                    if(isExceeded){
                        System.out.println("Cannot edit. Selected session is already full!");
                        System.out.println();
                        System.out.println("Do you need to edit the booking again? (y/n)");
                        String rst = console.next().toLowerCase();
                        if (rst.equals("y")) {
                            editBooking();
                        } else{
                            PrintMain.printMenu();
                        }
                    }
                    else
                    {
                        if(isExist && !isAttend && !isHaving && !isExceeded){
                            editBooking(editBookingId, editGroupId,editDay,editWeek);
                        }
                    }
                }
            }

        console.close();
        } catch (NumberFormatException ex) {
            System.out.println("Error: Input value is not a number");
            System.out.println("------------------------------------");
            editBooking();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    //cancel the insert booking data from the booking text file
    public static void cancelBooking(){
        File oldFile = new File(filePath);
        File newFile = new File(tempFile);

        System.out.println("Please enter booking number you need to cancel:  ");
        String cancelBookingId =console.next().toUpperCase();

        //Validate entered booking number
        boolean isExist = Common.isBookingNoAlreadyExist(cancelBookingId);
        if(!isExist){
            System.out.println("Booking number is invalid!");
            System.out.println("");
            PrintMain.printMenu();
        }

        //validate customer already attended for booked session
        //allowed to cancel a booking before the booked lesson was attended.
        boolean isAttend = Common.isValidateAttendance(cancelBookingId);
        if (isAttend){
            System.out.println("Cannot cancel the entered booking number. Customer already attended!");
            System.out.println();
            System.out.println("Do you need to cancel another booking? (y/n)");
            String rst=console.next().toLowerCase();
            if(rst.equals("y")){
                cancelBooking();
            }
            else{
                PrintMain.printMenu();
            }
        }
        if((isExist) && (!isAttend))
        {
            boolean boolFound = false;
            try {
                FileWriter fw= new FileWriter(tempFile,true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);

                boolean isValidPath = Common.validateFilePath(filePath);
                if(isValidPath){
                    Scanner x = new Scanner(new File(filePath));
                    x.useDelimiter("[,\n]");

                    while (x.hasNext()){
                        bookingId = x.next();
                        customerNo = x.next();
                        customerName = x.next();
                        group = x.next();
                        day = x.next();
                        week = x.next();
                        status = x.next();

                        if(bookingId.equals(cancelBookingId)){
                            pw.print(cancelBookingId + "," + customerNo + "," + customerName + "," + group + "," + day+ "," + week+ "," + "cancelled"+ "\n");
                            boolFound=true;

                        }
                        else{
                            pw.print(bookingId + "," + customerNo + "," + customerName + "," + group + "," + day+ "," + week+"," + status+ "\n");
                        }
                    }
                    x.close();
                    pw.flush();
                    pw.close();
                    oldFile.delete();
                    File dump = new File(filePath);
                    newFile.renameTo(dump);

                    if(!boolFound){
                        System.out.println("---Invalid booking Id---");
                        System.out.println();
                        System.out.println("Do you need to cancel booking? (y/n)");
                        String rst=console.next().toLowerCase();
                        if(rst.equals("y")){
                            cancelBooking();
                        }
                        else{
                            PrintMain.printMenu();
                        }
                    }
                    else{
                        System.out.println("Booking details cancelled successfully.");
                        System.out.println("Booking Number: " + cancelBookingId );
                        System.out.println();
                        PrintMain.printMenu();
                    }
                }
                else {
                    System.out.println("File path does not exist!");
                    PrintMain.printMenu();
                }
                console.close();
            }catch (Exception e){
                System.out.println("---Error---");
            }
        }

    }

    //delete the insert booking data from the booking text file
    public static void deleteBooking(){
        System.out.println("Please enter booking number you need to cancel:  ");
        String cancelBookingId =console.next().toUpperCase();

        //Validate entered booking number
        boolean isExist = Common.isBookingNoAlreadyExist(cancelBookingId);
        if(!isExist){
            System.out.println("Booking number is invalid!");
            System.out.println("");
            PrintMain.printMenu();
        }

        //validate customer already attended for booked session
        //allowed to cancel a booking before the booked lesson was attended.
        boolean isAttend = Common.isValidateAttendance(cancelBookingId);
        if (isAttend){
            System.out.println("Cannot delete the entered booking number. Customer already attended!");
            System.out.println();
            System.out.println("Do you need to delete another booking? (y/n)");
            String rst=console.next().toLowerCase();
            if(rst.equals("y")){
                PrintMain.printMenu();
            }
            else{
                PrintMain.printMenu();
            }
        }

        if((!isAttend) && (isExist))
        {
            String tempFile="textFiles\\temp.txt";
            File oldFile = new File(filePath);
            File newFile = new File(tempFile);

            try {
                boolean isValidPath = Common.validateFilePath(filePath);
                if(isValidPath){
                    FileWriter fw = new FileWriter(tempFile, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(bw);

                    Scanner x = new Scanner(new File(filePath));
                    x.useDelimiter("[,\n]");

                    while (x.hasNext()){
                        bookingId = x.next();
                        customerNo = x.next();
                        customerName = x.next();
                        group = x.next();
                        day = x.next();
                        week = x.next();
                        status = x.next();

                        if(!bookingId.equals(cancelBookingId)){
                            pw.print(bookingId + "," + customerNo + "," + customerName + "," + group + "," + day+ "," + week +"," + status + "\n");
                        }
                    }
                    x.close();
                    pw.flush();
                    pw.close();
                    oldFile.delete();
                    File dump = new File(filePath);
                    newFile.renameTo(dump);

                    System.out.println("Booking details deleted successfully.");
                    System.out.println();
                    PrintMain.printMenu();
                }
                else {
                    System.out.println("File path does not exist!");
                    PrintMain.printMenu();
                }

                // closing the scanner stream
                console.close();
            }catch (Exception e){
                System.out.println("---Error---");
            }
        }

    }

    //Generate Booking No.
    public static String generateBookingNo() {
        String bookingNo = null;
        String bookingId = null;
        String cusNo,cusName,group,day,week;

        try {
            boolean isValidPath = Common.validateFilePath(filePath);
            if(isValidPath) {
                Scanner x = new Scanner(new File(filePath));
                x.useDelimiter("[,\n]");

                while (x.hasNext()) {
                    bookingId = x.next();
                    cusNo = x.next();
                    cusName = x.next();
                    group = x.next();
                    day = x.next();
                    week = x.next();
                    status = x.next();
                }
                bookingId = bookingId.substring(2,5);
                int newId = Integer.parseInt(bookingId)+1;
                bookingNo = "BK0" + newId;

                x.close();
            }
            else {
                System.out.println("File path does not exist!");
                PrintMain.printMenu();
            }

        }catch (Exception e){
            System.out.println("---Error---");
        }
        return bookingNo;
    }

    //validate number of customers per each lesson (week).
    //maximum number of customers should be 5
    public static boolean isExceedCusCount(String chkWeek, String chkGroup){
        boolean isExceed = false;
        int count= 0;
        try {
            boolean isValidPath = Common.validateFilePath(filePath);
            if(isValidPath){
                Scanner x = new Scanner(new File(filePath));
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
                    status = x.next();

                    if (chkWeek.equals(week) && chkGroup.equals(groupNo) && (!status.equals("cancelled"))) {
                        count ++;
                    }
                }
                if(count >= 5)
                {
                    isExceed = true;
                }
                else
                {
                    isExceed =false;
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
        return isExceed;
    }

    //update the booking status
    public static void updateBookingStatus(String bookingNo,String newStatus)
    {
        File oldFile = new File(filePath);
        File newFile = new File(tempFile);

        boolean boolFound = false;
        try {
            FileWriter fw= new FileWriter(tempFile,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            boolean isValidPath = Common.validateFilePath(filePath);
            if(isValidPath){
                Scanner x = new Scanner(new File(filePath));
                x.useDelimiter("[,\n]");

                while (x.hasNext()){
                    bookingId = x.next();
                    customerNo = x.next();
                    customerName = x.next();
                    group = x.next();
                    day = x.next();
                    week = x.next();
                    status = x.next();

                    if(bookingId.equals(bookingNo)){
                        pw.print(bookingNo + "," + customerNo + "," + customerName + "," + group + "," + day+ "," + week+ "," + newStatus+ "\n");
                        boolFound=true;

                    }
                    else{
                        pw.print(bookingId + "," + customerNo + "," + customerName + "," + group + "," + day+ "," + week+"," + status+ "\n");
                    }
                }

                x.close();
                pw.flush();
                pw.close();
                oldFile.delete();
                File dump = new File(filePath);
                newFile.renameTo(dump);

            }
            else {
                System.out.println("File path does not exist!");
                PrintMain.printMenu();
            }

        }catch (Exception e){
            System.out.println("---Error---");
        }
    }
}
