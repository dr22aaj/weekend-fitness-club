package com.coursework.wfc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Fitness_Lessons {
    static Scanner console = new Scanner(System.in);
    public static void readfile(int type){
        try {
            String filePath= "";
            if (type == 11)
                filePath = "textFiles\\viewSessionsBySessionType.txt";
            else {
                filePath = filePath= "textFiles\\viewSessionsByDate.txt";
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
