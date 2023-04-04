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
                switch (inputType) {
                    case "saturday":
                        filePath = "textFiles\\saturday.txt";
                        break;
                    case "sunday":
                        filePath = "textFiles\\sunday.txt";
                        break;
                    default:
                        filePath = "textFiles\\viewSessionsByDate.txt";
                }

            else {
                switch (inputType) {
                    case "zumba":
                        filePath = "textFiles\\zumba.txt";
                        break;
                    case "yoga":
                        filePath = "textFiles\\yoga.txt";
                        break;
                    case "spin":
                        filePath = "textFiles\\spin.txt";
                        break;
                    case "bodysculpt":
                        filePath = "textFiles\\bodusculpt.txt";
                        break;
                    default:
                        filePath = "textFiles\\viewSessionsBySessionType.txt";
                }
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
