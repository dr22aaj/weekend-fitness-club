package com.coursework.wfc;

import java.io.*;
import java.util.Scanner;

public class Bookings {
    static Scanner console = new Scanner(System.in);
    // attributes
    public static int id;
    public static String customerName;
    public static String group;
    public static String day;
    public static String week;
    public static String bookingId;

    //Constructor
    public Bookings(int id , String customerName,String group, String day, String week){
        this.id=id;
        this.customerName=customerName;
        this.group=group;
        this.day=day;
        this.week=week;
    }
    public static String getBookingData(){
        return   bookingId +"," + customerName +"," + group +"," +day  +"," +week ;
    }

    public static String getBookingDataN(){
        return   "Booking ID:" + id  + " Customer Name:" + customerName + " Group:"+ group + " Day:" + day + " Week:" +week ;
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
        System.out.println("Enter booking id :  ");
        id=console.nextInt();
        bookingId = "BK0"+ id;
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

        System.out.println("Enter the selected week (1,2,3,4,5,6,7,8) :");
        week=console.next();
       // Bookings booking=new Bookings(id,customerName,group,day,week);

        try {
            FileWriter writer=new FileWriter(filename,true);
            writer.append(Bookings.getBookingData());
            writer.append("\n");
            writer.close();
            System.out.println("Booking added successfully!");
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
        // closing the scanner stream
        console.close();
    }

    //get the booking details that need to be change
    public static void editBookingDetails(){
        String editGroupId ="";
        System.out.println("Please enter booking id:  ");
        String editBookingId=console.next().toLowerCase();
        System.out.println("Enter the selected date (saturday/sunday) :");
        String editDay=console.next().toLowerCase();
        if(editDay.equals("saturday")){
            System.out.println("Enter the selected group (GP1, GP2) : ");
            editGroupId=console.next().toLowerCase();
        }else if(editDay.equals("sunday")){
            System.out.println("Enter the selected group (GP3, GP4) : ");
            editGroupId=console.next().toLowerCase();
        }
        System.out.println("Enter the selected week (1,2,3,4,5,6,7,8) :");
        String editWeek=console.next();
        Bookings.editBooking(editBookingId,editGroupId,editDay,editWeek);
        // closing the scanner stream
        console.close();
    }

    //save the updated the in booking text file
    public static void editBooking(String editBookingId,String editGroupId,String editDay,String editWeek){
        String tempFile="textFiles\\temp.txt";
        String filepath="textFiles\\bookings.txt";
        boolean boolFound = false;
        File oldFile = new File(filepath);
        File newFile = new File(tempFile);
        String Id;
        String cusName;
        String group;
        String day;
        String week;

        try {
            FileWriter fw= new FileWriter(tempFile,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            Scanner x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");

            while (x.hasNext()){
                Id = x.next();
                cusName = x.next();
                group = x.next();
                day = x.next();
                week = x.next();

                if(Id.equals(editBookingId)){
                    pw.print(editBookingId + "," + cusName + "," + editGroupId + "," + editDay+ "," + editWeek+ "\n");
                    boolFound=true;

                }
                else{
                    pw.print(Id + "," + cusName + "," + group + "," + day+ "," + week+ "\n");
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
            }
            else{
                System.out.println("Booking details updated successfully.");
                System.out.println("Booking Id: " + editBookingId );
                System.out.println();
                PrintMain.printMenu();
            }

        }catch (Exception e){
            System.out.println("---Error---");
        }
    }


}
