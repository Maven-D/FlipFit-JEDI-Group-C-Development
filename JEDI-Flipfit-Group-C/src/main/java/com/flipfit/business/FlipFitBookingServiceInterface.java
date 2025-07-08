package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Customer;

import java.util.List;

public interface FlipFitBookingServiceInterface {

	/**
	 * Sets the customer context for subsequent operations.
	 * @param customer The currently logged-in customer.
	 */
	void setCurrentCustomer(Customer customer);

	/**
	 * Creates a booking for the given customer for a specific slot ID.
	 * Implements clash detection and automatically cancels overlapping bookings.
	 * @param customer The customer who is making the booking.
	 * @param slotId The ID of the time slot to book.
	 * @return The newly created Booking object, or null if booking failed.
	 */
	Booking makeBooking(Customer customer, String slotId);

	/**
	 * Retrieves all bookings for a specific gym.
	 * @param gymId The ID of the gym.
	 * @return A list of bookings.
	 */
	List<Booking> getBookingsForGym(String gymId);

	/**
	 * Cancels a booking.
	 * It verifies that the booking belongs to the provided customer before proceeding.
	 * @param customer The customer requesting the cancellation.
	 * @param bookingId The ID of the booking to cancel.
	 * @return true if cancellation was successful, false otherwise.
	 */
	boolean cancelBooking(Customer customer, String bookingId);

	List<Booking> getBookingsForCustomerId(String cusTomerId);
}