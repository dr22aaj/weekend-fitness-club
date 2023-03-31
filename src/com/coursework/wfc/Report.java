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

    //containing the number of customers per lesson on each day
    public static void getNumOfCustomers(String month){
        int yogaCount=0, yogaRate=0;
        int zumCount=0, zumRate=0;
        int spainCount=0, spainRate=0;
        int bodysclptCount=0, bodysulRate=0;
        double avgyo=0.0, avgzu=0.0, avgsp=0.0, avgbod=0.0;

        //read data from booking file
        try {
            Scanner x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");

            while (x.hasNext()) {
                bookingId = x.next();
                customerNo = x.next();
                customerName = x.next();
                group = x.next();
                day = x.next();
                week = x.next();

                if (month.equals("1")){
                    //gp1=Zumba, gp2=yoga, gp3=Spin, gp4=bodySult
                    if(Integer.parseInt(getWeek()) <= 4){
                        if(getGroup().equals("gp1")){
                            zumCount ++;
                        }
                        if(getGroup().equals("gp2")){
                            yogaCount ++;
                        }
                        if(getGroup().equals("gp3")){
                            spainCount ++;
                        }
                        if(getGroup().equals("gp4")){
                            bodysclptCount ++;
                        }
                    }
                }
                else if (month.equals("2")) {
                    if(Integer.parseInt(getWeek()) >4 && Integer.parseInt(getWeek()) <=8 ){
                        if(getGroup().equals("gp1")){
                            zumCount ++;
                        }
                        if(getGroup().equals("gp2")){
                            yogaCount ++;
                        }
                        if(getGroup().equals("gp3")){
                            spainCount ++;
                        }
                        if(getGroup().equals("gp4")){
                            bodysclptCount ++;
                        }
                    }
                }
                else{
                    System.out.println("No data found for the given month!");
                    System.out.println();
                    System.out.println("Do you want to continue? (y/n) ");
                    String rst=console.next().toLowerCase();
                    if(rst.equals("y")){
                        PrintMain.printMenu();
                    }
                    break;
                }
            }
            x.close();

            //read data from attendance&feedback file
            Scanner y = new Scanner(new File(filepathA));
            y.useDelimiter("[,\n]");

            while (y.hasNext()) {
                bookingId = y.next();
                customerName = y.next();
                group = y.next();
                week = y.next();
                attended = y.next();
                feedback = y.next();
                rate = y.next();

                if (month.equals("1")){
                    if(Integer.parseInt(getWeek()) <= 4) {
                        if(getGroup().equals("gp1")){
                            zumRate = zumRate + Integer.parseInt(getRate());
                        }
                        if(getGroup().equals("gp2")){
                            yogaRate = yogaRate + Integer.parseInt(getRate());
                        }
                        if(getGroup().equals("gp3")){
                            spainRate = spainRate + Integer.parseInt(getRate());
                        }
                        if(getGroup().equals("gp4")){
                            bodysulRate = bodysclptCount + Integer.parseInt(getRate());
                        }
                    }
                } else if (month.equals("2")) {
                    if(Integer.parseInt(getWeek()) >4 && Integer.parseInt(getWeek()) <=8 ){
                        if(getGroup().equals("gp1")){
                            zumRate = zumRate + Integer.parseInt(getRate());
                        }
                        if(getGroup().equals("gp2")){
                            yogaRate = yogaRate + Integer.parseInt(getRate());
                        }
                        if(getGroup().equals("gp3")){
                            spainRate =  + Integer.parseInt(getRate());
                        }
                        if(getGroup().equals("gp4")){
                            bodysulRate = bodysulRate + Integer.parseInt(getRate());
                        }
                    }
                }
            }
            y.close();

            //convert values into double
            avgzu= Double.parseDouble(String.valueOf(zumRate))/Double.parseDouble(String.valueOf(zumCount));
            avgyo= Double.parseDouble(String.valueOf(yogaRate))/Double.parseDouble(String.valueOf(yogaCount));;
            avgsp= Double.parseDouble(String.valueOf(spainRate))/Double.parseDouble(String.valueOf(spainCount));;
            avgbod= Double.parseDouble(String.valueOf(bodysulRate))/Double.parseDouble(String.valueOf(bodysclptCount));;

            System.out.println("**************************************************");
            System.out.println("************ The Weekend Fitness Club ************");
            System.out.println("**************************************************");

            System.out.println("*********** Customer count on Saturdays **********");
            System.out.println("Zumba number of customers booking:" + " " + zumCount);
            if(zumRate == 0){
                System.out.println("Rating is not given for Zumba");
            }else{
                System.out.println("Average Rating:" + " " + avgzu);
            }
            System.out.println("");
            System.out.println("Yoga number of customers booking:" + " " + yogaCount);
            if(yogaRate == 0){
                System.out.println("Rating is not given for Yoga");
            }else{
                System.out.println("Average Rating:" + " " + avgyo);
            }
            System.out.println("");
            System.out.println("*********** Customer count on Sundays **********");
            System.out.println("Spin number of customers booking:" + " " + spainCount);
            if(spainRate == 0){
                System.out.println("Rating is not given for Spain");
            }else{
                System.out.println("Average Rating:" + " " + avgsp);
            }
            System.out.println("");
            System.out.println("Bodysculpt number of customers booking:" + " " + bodysclptCount);
            if(bodysulRate == 0){
                System.out.println("Rating is not given for Bodysculpt");
            }else{
                System.out.println("Average Rating:" + " " + avgbod);
            }
            System.out.println();
            System.out.println("****************** End Report ******************");
            System.out.println();
            System.out.println("Do you want to continue? (y/n) ");
            String rst=console.next().toLowerCase();
            if(rst.equals("y")){
                PrintMain.printMenu();
            }

        } catch (
                IOException e) {
            System.out.println(e);
        }
    }

   // type of fitness lessons which has generated the highest income
    public static void calIncomePerLesson(String month){
        int yogaCount=0;
        int zumCount=0;
        int spainCount=0;
        int bodysclptCount=0;
        String type=null;

        //read data from booking file
        try {
            Scanner x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");

            while (x.hasNext()) {
                bookingId = x.next();
                customerNo = x.next();
                customerName = x.next();
                group = x.next();
                day = x.next();
                week = x.next();

                if (month.equals("1")){
                    //gp1=Zumba, gp2=yoga, gp3=Spin, gp4=bodySult
                    if(Integer.parseInt(getWeek()) <= 4){
                        if(getGroup().equals("gp1")){
                            zumCount ++;
                        }
                        if(getGroup().equals("gp2")){
                            yogaCount ++;
                        }
                        if(getGroup().equals("gp3")){
                            spainCount ++;
                        }
                        if(getGroup().equals("gp4")){
                            bodysclptCount ++;
                        }
                    }
                }
                else if (month.equals("2")) {
                    if(Integer.parseInt(getWeek()) >4 && Integer.parseInt(getWeek()) <=8 ){
                        if(getGroup().equals("gp1")){
                            zumCount ++;
                        }
                        if(getGroup().equals("gp2")){
                            yogaCount ++;
                        }
                        if(getGroup().equals("gp3")){
                            spainCount ++;
                        }
                        if(getGroup().equals("gp4")){
                            bodysclptCount ++;
                        }
                    }
                }
                else{
                    System.out.println("No data found for the given month!");
                    System.out.println();
                    System.out.println("Do you want to continue? (y/n) ");
                    String rst=console.next().toLowerCase();
                    if(rst.equals("y")){
                        PrintMain.printMenu();
                    }
                    break;
                }
            }
            x.close();

            //Calculate the income per session
            double dblZum = zumCount * LessonPrice.ZUMBA.price;
            double dblYoga = yogaCount * LessonPrice.YOGA.price;
            double dblSpin = spainCount * LessonPrice.SPIN.price;
            double dblBodyscult = bodysclptCount * LessonPrice.BODYSCULPT.price;

            //Find the highest income
            double arr[] = {dblZum,dblYoga,dblSpin,dblBodyscult};
            double maxAmt= arr[0];
            for (double i=0; i < arr.length; i++){
                maxAmt = Math.max(maxAmt,arr[(int) i]);
            }

            if(maxAmt==dblZum){
                type="Zumba";
            } else if (maxAmt ==dblYoga) {
                type="Yoga";
            } else if (maxAmt==dblSpin) {
                type="Spin";
            } else if (maxAmt==dblBodyscult) {
                type="Bodyscult";
            }

            System.out.println("**************************************************");
            System.out.println("************ The Weekend Fitness Club ************");
            System.out.println("**************************************************");
            System.out.println("");
            System.out.println("************** Income on Saturdays ***************");
            System.out.println("");
            System.out.println("Total Income for Zumba:" + " " + dblZum + "£");
            System.out.println("Total Income for Yoga:" + " " + dblYoga + "£");
            System.out.println("");

            System.out.println("**************** Income on Sundays ***************");
            System.out.println("");
            System.out.println("Total Income for Spin:" + " " + dblSpin + "£");
            System.out.println("Total Income for Bodysculpt:" + " " + dblBodyscult + "£");
            System.out.println();

            System.out.println("------------------------------------------------");
            System.out.println("Highest income get from:" + type );
            System.out.println("Income is:" + maxAmt + "£" );
            System.out.println("------------------------------------------------");
            System.out.println();

            System.out.println("******************* End Report ********************");
            System.out.println();
            System.out.println("Do you want to continue? (y/n) ");
            String rst=console.next().toLowerCase();
            if(rst.equals("y")){
                PrintMain.printMenu();
            }

        } catch (
                IOException e) {
            System.out.println(e);
        }
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
