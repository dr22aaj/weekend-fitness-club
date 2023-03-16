package com.coursework.wfc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Customer {
    private int id;
    private String customerName;
    private int contactNumber;
    private String address;

    //Constructor
    public Customer(int id, String customerName, int contactNumber, String address) {
        this.id = id;
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    //Read data from the text file
    public static void readfile(){
        try {
            String filePath= "textFiles\\customers.txt";
            FileReader reader = new FileReader(filePath);
            int data = reader.read();
            while (data != -1){
                System.out.print((char)data);
                data = reader.read();
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public int getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
