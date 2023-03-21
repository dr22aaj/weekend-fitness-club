package com.coursework.wfc;
import java.util.Scanner;

public class PrintMain {
    static Scanner console = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("**************************************************");
        System.out.println("****** Welcome to The Weekend Fitness Club *******");
        System.out.println("**************************************************");
        printMenu();
    }
    public static void printMenu() {
        int inputNumber;
        System.out.println("##--------------------- MENU --------------------##");
        System.out.println("");
        System.out.println("[1].Create Booking ");
        System.out.println("[2].Edit Booking ");
        System.out.println("[3].Cancel Booking ");
        System.out.println("[4].Give Feedback ");
        System.out.println("[5].Update Attendance ");
        System.out.println("[6].View Session ");
        System.out.println("[7].Print Report ");
        System.out.println("[8].View Customers ");
        System.out.println("[9].View Bookings ");
        System.out.println("[10].Exit ");
        System.out.println("-----------------------------------------------------");

        try {
            System.out.println("Please enter the number which you need to proceed. ");
            inputNumber = Integer.parseInt(console.next());
            try {
                switch (inputNumber) {
                    case 1:
                        //Create Bookings
                         Bookings.createBooking();
                        break;
                    case 2:
                        //Edit Bookings
                         Bookings.editBookingDetails();
                        break;
                    case 3:
                        //Cancel Bookings
                         Bookings.cancelBooking();
                        break;
                    case 4:
                        //Give Feedback
                        //TODO:
                        test();
                        break;
                    case 5:
                        //Update Attendance
                        test();
                        break;
                    case 6:
                        //View Sessions
                        System.out.println("Which choice you like to view the session? ");
                        System.out.println("[11].View Sessions By Day ");
                        System.out.println("[12].View Sessions By Session Type ");

                        System.out.println("Please enter the number ");
                        inputNumber = Integer.parseInt(console.next());

                        if (inputNumber == 12) {
                            Fitness_Lessons.readfile(inputNumber);
                        } else if (inputNumber == 11) {
                            Fitness_Lessons.readfile(inputNumber);

                        }
                        break;
                    case 7:
                        //View Reports
                        if (inputNumber == 7) {
                            System.out.println("Which choice you like to view the report? ");
                            System.out.println("[13].A report containing the number of customers per lesson on each day ");
                            System.out.println("[14].A report containing the type of fitness lessons which has generated the highest income ");

                            System.out.println("Please enter the number ");
                            inputNumber = Integer.parseInt(console.next());

                            //TODO:
                        }
                        break;
                    case 8:
                        //View Customers
                        if (inputNumber == 8) {
                            Customer.readfile();
                        }
                        break;
                    case 9:
                        //View Booking Details
                        if (inputNumber == 9) {
                            Bookings.readBookingFile();
                        }
                        break;
                    case 10:
                        break;
                    default:
                        System.out.println("Error: Invalid number");
                        printMenu();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Error: Input value is not a number");
                System.out.println("------------------------------------");
                printMenu();
            }
        } catch (NumberFormatException ex) {
            System.out.println("Error: Input value is not a number");
            System.out.println("------------------------------------");
            printMenu();
        }
    }
    public static void test(){

    }
}
