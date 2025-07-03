package com.flipfit.business;

import java.util.List;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Customer;

public interface FlipFitCustomerServiceInterface {

	void setCustomer(Customer customer);

	List<Booking> viewBookings();

	void addBooking(Booking booking);

	boolean cancelBooking(String bookingId);

}