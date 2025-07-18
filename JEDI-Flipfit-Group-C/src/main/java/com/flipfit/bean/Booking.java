package com.flipfit.bean;

import java.time.LocalDateTime;

/**
 * Represents a booking made by a user for a gym time slot.
 * It contains all the details related to a specific booking.
 */
public class Booking {

    private String bookingID;
    private LocalDateTime bookingTime;
    private String status; // e.g., "Confirmed", "Cancelled"
//    private TimeSlot slot; // TODO: remove this and do it by slotID
    private String userID;
    private String paymentID;
    private String gymID;
    private String slotID;

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getSlotID() {
        return slotID;
    }

    public void setSlotID(String slotID) {
        this.slotID = slotID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getGymID() {
        return gymID;
    }

    public void setGymID(String gymID) {
        this.gymID = gymID;
    }



//    public TimeSlot getSlot() {
//        return slot;
//    }
//
//    public void setSlot(TimeSlot slot) {
//        this.slot = slot;
//    }
// Getters and Setters

    /**
     * Gets the unique identifier for the booking.
     * @return A string representing the booking ID.
     */
    public String getBookingID() {
        return bookingID;
    }

    /**
     * Sets the unique identifier for the booking.
     * @param bookingID A string containing the booking ID.
     */
    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    /**
     * Gets the date and time when the booking was made.
     * @return A LocalDateTime object representing the booking timestamp.
     */
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    /**
     * Sets the date and time when the booking was made.
     * @param bookingTime A LocalDateTime object for the booking timestamp.
     */
    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    /**
     * Gets the current status of the booking.
     * @return A string representing the status (e.g., "Confirmed", "Cancelled").
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the booking.
     * @param status A string for the status (e.g., "Confirmed", "Cancelled").
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
