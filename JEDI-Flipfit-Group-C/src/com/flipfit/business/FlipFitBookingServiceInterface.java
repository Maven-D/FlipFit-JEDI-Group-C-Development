package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Customer;
import com.flipfit.bean.TimeSlot;

public interface FlipFitBookingServiceInterface {

	Booking makeBooking(Customer customer, TimeSlot slot);

	void addTimeSlot(TimeSlot slot);

}