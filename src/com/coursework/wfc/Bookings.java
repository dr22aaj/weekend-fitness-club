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

    //Constructor
    public Bookings(String bookingId ,String customerNo, String customerName,String group, String day, String week){
        this.bookingId=bookingId;
        this.customerName=customerName;
        this.group=group;
        this.day=day;
        this.week=week;
        this.customerNo=customerNo;
    }
    public static String getBookingData(){
        return   bookingId +"," + customerNo +"," + customerName +"," + group +"," +day  +"," +week ;
    }

    public static String getBookingDataN(){
        return   "Booking ID:" + bookingId  + " Customer No:" + customerNo + " Customer Name:" + customerName + " Group:"+ group + " Day:" + day + " Week:" +week ;
    }

    //view booking data
    public static void readBookingFile(){
        try {
            String filePath= "textFiles\\bookings.txt";
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
    //save the booking data in booking text file
    public static void createBooking() {
        String filename = "textFiles\\bookings.txt";
        bookingId = generateBookingNo();
        System.out.println("Enter customer number :");
        customerNo=console.next();
        System.out.println("Enter customer name :");
        customerName=console.next();
        System.out.println("Enter the selected date (saturday/sunday) :");
        day=console.next().toLowerCase();
        if(day.equals("saturday")){
            System.out.println("Enter the selected group (GP1, GP2) : ");
            group=console.next().toLowerCase();
        }else if(day.equals("sunday")){
            System.out.println("Enter the selected group (GP3, GP4) : ");
            group=console.next().toLowerCase();
        }
        else {
            System.out.println("Entered day is invalid!");
            createBooking();
        }
        System.out.println("Enter the selected week (1,2,3,4,5,6,7,8) :");
        week=console.next();
        if (!(Integer.parseInt(week) > 0 || Integer.parseInt(week) < 9)){
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
                    FileWriter writer=new FileWriter(filename,true);
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
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
        // closing the scanner stream
        console.close();
    }

    //get the booking details that need to be change
    public static void editBookingDetails(){
        String editGroupId ="";
        System.out.println("Please enter booking number:  ");
        String editBookingId=console.next().toUpperCase();
        //Validate entered booking number
        boolean isExist = Common.isBookingNoAlreadyExist(editBookingId);
        if(!isExist){
            System.out.println("Booking number is invalid!");
            System.out.println("");
            editBookingDetails();
        }
        else {
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
                    editBookingDetails();
                } else if (rst.equals("n")) {
                    PrintMain.printMenu();
                }
            } else {
                System.out.println("Enter the selected date (saturday/sunday) :");
                String editDay = console.next().toLowerCase();
                if (editDay.equals("saturday")) {
                    System.out.println("Enter the selected group (GP1, GP2) : ");
                    editGroupId = console.next().toLowerCase();
                } else if (editDay.equals("sunday")) {
                    System.out.println("Enter the selected group (GP3, GP4) : ");
                    editGroupId = console.next().toLowerCase();
                } else {
                    System.out.println("Entered day is invalid!");
                    editBookingDetails();
                }
                System.out.println("Enter the selected week (1,2,3,4,5,6,7,8) :");
                String editWeek = console.next();
                if (Integer.parseInt(editWeek) > 0 || Integer.parseInt(editWeek) < 9) {
                    //Validate customer already booked for same session in group (week,group) )
                    boolean isHaving = Common.validateDuplicateBookings(editGroupId,editWeek,customerNo);
                    if(isHaving){
                        System.out.println("Cannot edit. You have already booked for this session!");
                        System.out.println();
                        System.out.println("Do you need to edit another booking? (y/n)");
                        String rst = console.next().toLowerCase();
                        if (rst.equals("y")) {
                            editBookingDetails();
                        } else if (rst.equals("n")) {
                            PrintMain.printMenu();
                        }
                    }
                    else {
                        //validate number of customers per each lesson (week) is exceeded or not
                        boolean isExceeded = isExceedCusCount(editWeek,editGroupId);
                        if(isExceeded){
                            System.out.println("Cannot edit. Selected session is already full!");
                            System.out.println();
                            System.out.println("Do you need to edit the booking again? (y/n)");
                            String rst = console.next().toLowerCase();
                            if (rst.equals("y")) {
                                editBookingDetailsN();
                            } else if (rst.equals("n")) {
                                PrintMain.printMenu();
                            }
                        }
                        else
                        Bookings.editBooking(editBookingId, editGroupId, editDay, editWeek);
                    }
                } else {
                    System.out.println("Entered week is invalid!");
                    editBookingDetails();
                }

                // closing the scanner stream
                console.close();
            }
        }
    }

    //save the updated the in booking text file
    public static void editBooking(String editBookingId,String editGroupId,String editDay,String editWeek){
        String tempFile="textFiles\\tempnew.txt";
        String filepath="textFiles\\bookings.txt";
        File oldFile = new File(filepath);
        File newFile = new File(tempFile);

        boolean boolFound = false;
        try {
            FileWriter fw= new FileWriter(tempFile,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            Scanner x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");

            while (x.hasNext()){
                bookingId = x.next();
                customerNo = x.next();
                customerName = x.next();
                group = x.next();
                day = x.next();
                week = x.next();

                if(bookingId.equals(editBookingId)){
                    pw.print(editBookingId + "," + customerNo + "," + customerName + "," + editGroupId + "," + editDay+ "," + editWeek+ "\n");
                    boolFound=true;

                }
                else{
                    pw.print(bookingId + "," + customerNo + "," + customerName + "," + group + "," + day+ "," + week+ "\n");
                }
            }

            x.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File(filepath);
            newFile.renameTo(dump);

            if(!boolFound){
                System.out.println("---Invalid booking Id---");
                System.out.println();
                System.out.println("Do you need to edit booking? (y/n)");
                String rst=console.next().toLowerCase();
                if(rst.equals("y")){
                    editBookingDetails();
                }
                else if(rst.equals("n")){
                    PrintMain.printMenu();
                }
            }
            else{
                System.out.println("Booking details updated successfully.");
                System.out.println("Booking Number: " + editBookingId );
                System.out.println();
                PrintMain.printMenu();
            }

        }catch (Exception e){
            System.out.println("---Error---");
        }
    }

    public static void editBookingDetailsN(){
        String editGroupId ="";
        try {
            System.out.println("Please enter booking id:  ");
            String editBookingId = console.next().toUpperCase();
            System.out.println("Enter the selected date (saturday/sunday) :");
            String editDay = console.next().toLowerCase();
            if (editDay.equals("saturday")) {
                System.out.println("Enter the selected group (GP1, GP2) : ");
                editGroupId = console.next().toLowerCase();
            } else if (editDay.equals("sunday")) {
                System.out.println("Enter the selected group (GP3, GP4) : ");
                editGroupId = console.next().toLowerCase();
            } else {
                System.out.println("Entered day is invalid!");
                editBookingDetailsN();
            }
            System.out.println("Enter the selected week (1,2,3,4,5,6,7,8) :");
            String editWeek = console.next();

            if (Integer.parseInt(editWeek) < 0 || Integer.parseInt(editWeek) > 8) {
                System.out.println("Invalid week!");
                System.out.println();
                editBookingDetailsN();
            }

            //Validate entered booking number
            boolean isExist = Common.isBookingNoAlreadyExist(editBookingId);
            if(!isExist){
                System.out.println("Booking number is invalid!");
                System.out.println("");
                editBookingDetailsN();
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
                    editBookingDetailsN();
                } else if (rst.equals("n")) {
                    PrintMain.printMenu();
                }
            }

            //Validate customer already booked for same session in group (week,group) )
            boolean isHaving = Common.validateDuplicateBookings(editGroupId,editWeek,customerNo);
            if(isHaving){
                System.out.println("Cannot edit. You have already booked for this session!");
                System.out.println();
                System.out.println("Do you need to edit another booking? (y/n)");
                String rst = console.next().toLowerCase();
                if (rst.equals("y")) {
                    editBookingDetailsN();
                } else if (rst.equals("n")) {
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
                    editBookingDetailsN();
                } else if (rst.equals("n")) {
                    PrintMain.printMenu();
                }
            }

            if(isExist && !isAttend && !isHaving && !isExceeded){
                editBooking(editBookingId, editGroupId,editDay,editWeek);
           }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    //delete the insert booking data from the booking text file
    public static void cancelBooking(){
        System.out.println("Please enter booking number you need to cancel:  ");
        String cancelBookingId =console.next().toUpperCase();

        //validate customer already attended for booked session
        //allowed to cancel a booking before the booked lesson was attended.
        boolean isAttend = Common.isValidateAttendance(cancelBookingId);
        if (isAttend){
            System.out.println("Cannot cancel the entered booking number. Customer already attended!");
            System.out.println();
            System.out.println("Do you need to cancel another booking? (y/n)");
            String rst=console.next().toLowerCase();
            if(rst.equals("y")){
                createBooking();
            }
            else if(rst.equals("n")){
                PrintMain.printMenu();
            }
        }

        String tempFile="textFiles\\temp.txt";
        String filepath="textFiles\\bookings.txt";
        File oldFile = new File(filepath);
        File newFile = new File(tempFile);
        //String Id; String customerNo; String customerName; String group; String day; String week;

        try {
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            Scanner x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");

            while (x.hasNext()){
                bookingId = x.next();
                customerNo = x.next();
                customerName = x.next();
                group = x.next();
                day = x.next();
                week = x.next();

                if(!bookingId.equals(cancelBookingId)){
                    pw.print(bookingId + "," + customerNo + "," + customerName + "," + group + "," + day+ "," + week + "\n");
                }
            }
            x.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File(filepath);
            newFile.renameTo(dump);

            System.out.println("Booking details cancelled successfully.");
            System.out.println();
            PrintMain.printMenu();

        }catch (Exception e){
            System.out.println("---Error---");
        }
        // closing the scanner stream
        console.close();
    }

    //Generate Booking No.
    public static String generateBookingNo() {
        String bookingNo = null;
        String filepath="textFiles\\bookings.txt";
        String bookingId = null;
        String cusNo,cusName,group,day,week;

        try {
            Scanner x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");

            while (x.hasNext()) {
                bookingId = x.next();
                cusNo = x.next();
                cusName = x.next();
                group = x.next();
                day = x.next();
                week = x.next();
            }
            bookingId = bookingId.substring(2,5);
            int newId = Integer.parseInt(bookingId)+1;
            bookingNo = "BK0" + newId;

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

                if (chkWeek.equals(week) && chkGroup.equals(groupNo)) {
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
        } catch (
                IOException e) {
            System.out.println(e);
        }
        return isExceed;
    }

}
