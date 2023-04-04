package com.coursework.wfc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonTest {
    Common myUnit = new Common();
    @Test
    @DisplayName("Validate Attendance")
    void isValidateAttendance() {
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
}