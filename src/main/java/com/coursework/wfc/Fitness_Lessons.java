/*
Created By: Dinushi Ranasinghe
Created Date: 21/March/2023
Description: Fitness Lesson related methods are handel under this class
**/
package com.coursework.wfc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Fitness_Lessons {
    static Scanner console = new Scanner(System.in);
    protected static void readSessionFile(int type){
        try {
            String filePath;
            String inputType = null;

            if (type == 11) {
                System.out.println("Which day you want to view? (1 - saturday / 2 - sunday )");
                int inputTypeNum = Integer.parseInt(console.next());
                if(inputTypeNum == 1){
                    inputType= "saturday";
                } else if (inputTypeNum == 2) {
                    inputType= "sunday";
                } else {
                    inputType= "both";
                }

                filePath = switch (inputType) {
                    case "saturday" -> "C:\\textFiles\\saturday.txt";
                    case "sunday" -> "C:\\textFiles\\sunday.txt";
                    default -> "C:\\textFiles\\viewSessionsByDate.txt";
                };

            } else {
                System.out.println("Which session do you want to view? (z - zumba/ y - yoga/ s - spin/ b - bodysculpt)");
                String inputVal = console.next().toLowerCase();

                if(inputVal.equals("z")){
                    inputType= "zumba";
                } else if (inputVal.equals("y")) {
                    inputType= "yoga";
                } else if (inputVal.equals("s")) {
                    inputType= "spin";}
                else if (inputVal.equals("b")) {
                    inputType= "bodysculpt";
                } else {
                    inputType= "all";
                }

                filePath = switch (inputType) {
                    case "zumba" -> "C:\\textFiles\\zumba.txt";
                    case "yoga" -> "C:\\textFiles\\yoga.txt";
                    case "spin" -> "C:\\textFiles\\spin.txt";
                    case "bodysculpt" -> "C:\\textFiles\\bodusculpt.txt";
                    default -> "C:\\textFiles\\viewSessionsBySessionType.txt";
                };
            }
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
            }
            else {
                System.out.println("File path does not exist!");
                PrintMain.printMenu();
            }

        } catch (NumberFormatException ex) {
            System.out.println("Error: Input value is not a number");
            System.out.println("------------------------------------");
            PrintMain.printMenu();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    protected static void readSessionFile(int type, String inputType){
        try {
            String filePath;
            if (type == 11)
                filePath = switch (inputType) {
                    case "saturday" -> "C:\\textFiles\\saturday.txt";
                    case "sunday" -> "C:\\textFiles\\sunday.txt";
                    default -> "C:\\textFiles\\viewSessionsByDate.txt";
                };

            else {
                filePath = switch (inputType) {
                    case "zumba" -> "C:\\textFiles\\zumba.txt";
                    case "yoga" -> "C:\\textFiles\\yoga.txt";
                    case "spin" -> "C:\\textFiles\\spin.txt";
                    case "bodysculpt" -> "C:\\textFiles\\bodusculpt.txt";
                    default -> "C:\\textFiles\\viewSessionsBySessionType.txt";
                };
            }
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
            }
            else {
                System.out.println("File path does not exist!");
                PrintMain.printMenu();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
