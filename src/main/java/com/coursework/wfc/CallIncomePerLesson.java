/*
Created By: Dinushi Ranasinghe
Created Date: 30/March/2023
Description: Income Calculation is covered under this class
**/
package com.coursework.wfc;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

// type of fitness lessons which has generated the highest income
public class CallIncomePerLesson extends Report {
    protected static void calIncomePerLesson(int month){
        int yogaCount=0;
        int zumCount=0;
        int spainCount=0;
        int bodysclptCount=0;
        String type=null;

        //read data from attendanceAndFeedback file
        try {
            boolean isValidPath = Common.validateFilePath(filepathA);
            if(isValidPath){
                Scanner x = new Scanner(new File(filepathA));
                x.useDelimiter("[,\n]");

                while (x.hasNext()) {
                    setBookingId(x.next());
                    setCustomerName(x.next());
                    setGroup(x.next());
                    setDay(x.next());
                    setWeek(x.next());
                    setAttended(x.next());
                    setFeedback(x.next());
                    setRate(x.next());

                    if (month==1){
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
                    else if (month==2) {
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
                        else{
                            System.out.println("Thank You & Have a Great Day!");
                        }
                    }
                }
                x.close();

                //Calculate the income per session
                double dblZum = zumCount * LessonPrice.ZUMBA.price;
                double dblYoga = yogaCount * LessonPrice.YOGA.price;
                double dblSpin = spainCount * LessonPrice.SPIN.price;
                double dblBodyscult = bodysclptCount * LessonPrice.BODYSCULPT.price;

                //Find the highest income
                double[] arr = {dblZum,dblYoga,dblSpin,dblBodyscult};
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
                System.out.println();
                System.out.println("************** Income on Saturdays ***************");
                System.out.println();
                System.out.println("Total Income for Zumba:" + " " + dblZum + "£");
                System.out.println("Total Income for Yoga:" + " " + dblYoga + "£");
                System.out.println();

                System.out.println("**************** Income on Sundays ***************");
                System.out.println();
                System.out.println("Total Income for Spin:" + " " + dblSpin + "£");
                System.out.println("Total Income for Bodysculpt:" + " " + dblBodyscult + "£");
                System.out.println();

                System.out.println("##################################################");
                System.out.println("      Highest income get from: " + type );
                System.out.println("      Income is: " + maxAmt + "£" );
                System.out.println("##################################################");
                System.out.println();

                System.out.println("******************* End Report ********************");
                System.out.println();
                System.out.println("Do you want to continue? (y/n) ");
                String rst=console.next().toLowerCase();
                if(rst.equals("y")){
                    PrintMain.printMenu();
                }
                else{
                    System.out.println("Thank You & Have a Great Day!");
                }
            }
            else {
                System.out.println("File path does not exist!");
                PrintMain.printMenu();
            }
        console.close();
        } catch (
                IOException e) {
            System.out.println(e);
        }
    }

}
