/*
Created By: Dinushi Ranasinghe
Created Date: 21/March/2023
Description: Fitness Lesson related methods are handel under this class
**/
package com.coursework.wfc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Fitness_Lessons {
    static Scanner console = new Scanner(System.in);
    protected static void readSessionFile(int type, String inputType){
        try {
            String filePath;

            if (type == 11)
                filePath = switch (inputType) {
                    case "saturday" -> "textFiles\\saturday.txt";
                    case "sunday" -> "textFiles\\sunday.txt";
                    default -> "textFiles\\viewSessionsByDate.txt";
                };

            else {
                filePath = switch (inputType) {
                    case "zumba" -> "textFiles\\zumba.txt";
                    case "yoga" -> "textFiles\\yoga.txt";
                    case "spin" -> "textFiles\\spin.txt";
                    case "bodysculpt" -> "textFiles\\bodusculpt.txt";
                    default -> "textFiles\\viewSessionsBySessionType.txt";
                };
            }
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
}
