package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling business logic related to a regular Customer.
 */
public class FlipFitCustomerService {

    private Customer customer;
    public void setCustomer(Customer customer) {this.customer = customer;}

    /**
     * Retrieves all bookings made by a specific customer.
     *
     * @return A list of Booking objects.
     */
    public List<Booking> viewBookings() {
        System.out.println("Fetching bookings for customer ID: " + customer.getUserID());
        // In a real app, this would query the database.
        // For now, we filter the master list of bookings.
        // To make this work, you would add a `private String customerId;` to the Booking.java bean.
        return new ArrayList<>(customer.getBookingList());
    }
    
    public void addBooking(Booking booking) {
    	customer.getBookingList().add(booking);
    }

    /**
     * Cancels a specific booking.
     *
     * @param booking The booking object to be cancelled.
     * @return true if the booking was successfully cancelled, false otherwise.
     */
    public boolean cancelBooking(Booking booking) {
        if (booking == null) {
            System.out.println("Cannot cancel a null booking.");
            return false;
        }
        // Find the booking in the list and update its status.
        for (Booking b : customer.getBookingList()) {
            if (b.getBookingID().equals(booking.getBookingID())) {
                if (!b.getStatus().equalsIgnoreCase("Cancelled")) {
                    b.setStatus("Cancelled");
                    System.out.println("Booking " + booking.getBookingID() + " has been cancelled.");
                    // In a real app, you would also need to increase the seat count for the corresponding timeslot.
                    return true;
                } else {
                    System.out.println("Booking " + booking.getBookingID() + " was already cancelled.");
                    return false;
                }
            }
        }
        System.out.println("Booking " + booking.getBookingID() + " not found.");
        return false;
    }
}
