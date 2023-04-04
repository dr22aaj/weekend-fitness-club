package com.coursework.wfc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    Feedback myUnit = new Feedback();

    @Test
    @DisplayName("Validate Feedback Given - True")
    void isFeedbackGiven() throws FileNotFoundException {
        String bookingNo = "BK021";
        boolean actual = myUnit.isFeedbackGiven(bookingNo);
        assertTrue(actual);
    }

    @Test
    @DisplayName("Validate Feedback Given - False")
    void isFeedbackGivenFalse() throws FileNotFoundException {
        String bookingNo = "BK022";
        boolean actual = myUnit.isFeedbackGiven(bookingNo);
        assertFalse(actual);
    }
}

