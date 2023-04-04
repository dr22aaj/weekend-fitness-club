/*
Created By: Dinushi Ranasinghe
Created Date: 30/March/2023
Description: Calculating number of customers related methods are handel under this class
**/
package com.coursework.wfc;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class NumOfCustomers extends Report {
    protected static void getNumOfCustomers(String month){
        int yogaCount=0, yogaRate=0;
        int zumCount=0, zumRate=0;
        int spainCount=0, spainRate=0;
        int bodysclptCount=0, bodysulRate=0;
        double avgyo, avgzu=0.0, avgsp=0.0, avgbod=0.0;

        //read data from booking file
        try {
            Scanner x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");

            while (x.hasNext()) {
                setBookingId(x.next());
                setCustomerNo(x.next());
                setCustomerName(x.next());
                setGroup(x.next());
                setDay(x.next());
                setWeek(x.next());

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
                setBookingId(y.next());
                setCustomerName(y.next());
                setGroup(y.next());
                setWeek(y.next());
                setAttended(y.next());
                setFeedback(y.next());
                setRate(y.next());

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
                            spainRate = spainRate + Integer.parseInt(getRate());
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

}
