package com.flipfit.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a regular user in the FlipFit system.
 * This class inherits all its properties from the BaseUser class
 * and is used for customers who book gym slots.
 */
public class Customer extends BaseUser {
    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    // This class inherits all fields (userID, name, email, passwordHash)
    // and their getters/setters from BaseUser.
    // According to the class diagram, it does not have any additional
    // attributes of its own. Business logic related to a User
    // will be handled in the service/business layer.
    private List<Booking> bookingList = new ArrayList<>();


}
