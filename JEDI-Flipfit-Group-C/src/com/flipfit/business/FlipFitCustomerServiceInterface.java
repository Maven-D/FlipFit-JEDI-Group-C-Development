package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Customer;

import java.util.List;

public interface FlipFitCustomerServiceInterface {
    void setCustomer(Customer customer);

    List<Booking> viewBookings();

    void addBooking(Booking booking);

    boolean cancelBooking(String bookingId);
}
