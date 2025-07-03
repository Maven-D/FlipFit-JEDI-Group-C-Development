package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling business logic related to a regular Customer.
 */
public class FlipFitCustomerServiceImpl implements FlipFitCustomerServiceInterface {

    private Customer customer;
    @Override
<<<<<<< HEAD
	public void setCustomer(Customer customer) {this.customer = customer;}
=======
    public void setCustomer(Customer customer) {this.customer = customer;}
>>>>>>> 4678108a044592849fba849c12eb21ae5b8698e0

    /**
     * Retrieves all bookings made by a specific customer.
     *
     * @return A list of Booking objects.
     */
    @Override
<<<<<<< HEAD
	public List<Booking> viewBookings() {
=======
    public List<Booking> viewBookings() {
>>>>>>> 4678108a044592849fba849c12eb21ae5b8698e0
        System.out.println("Fetching bookings for customer ID: " + customer.getUserID());
        // In a real app, this would query the database.
        // For now, we filter the master list of bookings.
        // To make this work, you would add a `private String customerId;` to the Booking.java bean.
        return new ArrayList<>(customer.getBookingList());
    }
    
    @Override
<<<<<<< HEAD
	public void addBooking(Booking booking) {
=======
    public void addBooking(Booking booking) {
>>>>>>> 4678108a044592849fba849c12eb21ae5b8698e0
    	customer.getBookingList().add(booking);


    }

    /**
     * Cancels a specific booking.
     *
     * @param booking The booking object to be cancelled.
     * @return true if the booking was successfully cancelled, false otherwise.
     */
    @Override
<<<<<<< HEAD
	public boolean cancelBooking(String bookingId) {
=======
    public boolean cancelBooking(String bookingId) {
>>>>>>> 4678108a044592849fba849c12eb21ae5b8698e0
        // 1. Add a validation check for the input bookingId.
        if (bookingId == null || bookingId.trim().isEmpty()) {
            System.out.println("Booking ID cannot be null or empty.");
            return false;
        }

        // 2. Find the booking in the list by iterating and comparing IDs.
        for (Booking booking : customer.getBookingList()) {
            // Use the provided bookingId for comparison.
            if (booking.getBookingID().equals(bookingId)) {
                
                // 3. Check if the booking is already cancelled.
                if (!booking.getStatus().equalsIgnoreCase("Cancelled")) {
                    // 4. Update the status if not already cancelled.
                    booking.setStatus("Cancelled");
                    System.out.println("Booking " + bookingId + " has been cancelled.");
                    
                    // In a real app, you would also need to find the corresponding
                    // timeslot and increase its available seat count.
                    // e.g., timeSlotService.incrementSeatCount(booking.getTimeSlotId());
                    
                    return true; // Successfully cancelled.
                } else {
                    // The booking was found but was already in a "Cancelled" state.
                    System.out.println("Booking " + bookingId + " was already cancelled.");
                    return false; // Indicate that no action was taken.
                }
            }
        }

        // 5. If the loop completes, the booking was not found.
        System.out.println("Booking " + bookingId + " not found for this customer.");
        return false;
    }
}
