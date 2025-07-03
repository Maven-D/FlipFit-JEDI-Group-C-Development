package com.flipfit.business;

<<<<<<< HEAD
import java.util.List;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Customer;

public interface FlipFitCustomerServiceInterface {

	void setCustomer(Customer customer);

	List<Booking> viewBookings();

	void addBooking(Booking booking);

	boolean cancelBooking(String bookingId);

}
=======
import com.flipfit.bean.Booking;
import com.flipfit.bean.Customer;

import java.util.List;

public interface FlipFitCustomerServiceInterface {
    void setCustomer(Customer customer);

    List<Booking> viewBookings();

    void addBooking(Booking booking);

    boolean cancelBooking(String bookingId);
}
>>>>>>> 4678108a044592849fba849c12eb21ae5b8698e0
