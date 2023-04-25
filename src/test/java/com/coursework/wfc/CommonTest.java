package com.coursework.wfc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonTest {
    Common myUnit = new Common();
    @Test
    @DisplayName("Validate Attendance - True")
    void isValidateAttendanceTrue() {
        String bookingNo = "BK021";
        boolean actual = myUnit.isValidateAttendance(bookingNo);
        assertTrue(actual);
    }
    @Test
    @DisplayName("Validate Attendance - False")
    void isValidateAttendanceFalse() {
        String bookingNo = "BK022";
        boolean actual = myUnit.isValidateAttendance(bookingNo);
        assertFalse(actual);
    }
    @Test
    @DisplayName("Get Customer Number")
    void getCustomerNo() {
        String bookingNo = "bk021";
        String result = "CUS002";
        String actual = myUnit.getCustomerNo(bookingNo);
        assertEquals(actual,result);
    }
    @Test
    @DisplayName("Get Customer Number Fail")
    void getCustomerNoFail() {
        String bookingNo = "bk022";
        String result = "CUS002";
        String actual = myUnit.getCustomerNo(bookingNo);
        assertNotEquals(actual,result);
    }
    @Test
    @DisplayName("Validate File Path - False")
    void validateFilePath() {
        String path = "C:\\textF\\bookings.txt";
        boolean actual = myUnit.validateFilePath(path);
        assertFalse(actual);
    }
    @Test
    @DisplayName("Validate File Path - True")
    void validateFilePathTrue() {
        String path = "C:\\textFiles\\bookings.txt";
        boolean actual = myUnit.validateFilePath(path);
        assertTrue(actual);
    }
}