package com.flipfit.dao;

import com.flipfit.bean.Booking;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the BookingDAO interface.
 */
public class BookingDAOImpl implements BookingDAO {

    private static final List<Booking> bookingTable = new ArrayList<>();

    static {
        Booking booking = new Booking();
        booking.setBookingID("booking001");
        booking.setUserID("cust001");
        booking.setGymID("gym01");
        booking.setSlotID("slot001");
        booking.setBookingTime(LocalDateTime.now().minusHours(1));
        booking.setStatus("Confirmed");
        booking.setPaymentID("payment001");
        bookingTable.add(booking);
    }

    @Override
    public void save(Booking booking) {
        System.out.println("DAO: Saving booking " + booking.getBookingID());
        bookingTable.add(booking);
    }

    @Override
    public boolean update(Booking bookingToUpdate) {
        System.out.println("DAO: Updating booking with ID: " + bookingToUpdate.getBookingID());
        for (int i = 0; i < bookingTable.size(); i++) {
            if (bookingTable.get(i).getBookingID().equals(bookingToUpdate.getBookingID())) {
                bookingTable.set(i, bookingToUpdate);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Booking> findByUserId(String userId) {
        System.out.println("DAO: Fetching bookings for user ID: " + userId);
        return bookingTable.stream()
                .filter(b -> b.getUserID().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findByGymId(String gymId) {
        System.out.println("DAO: Fetching bookings for gym ID: " + gymId);
        return bookingTable.stream()
                .filter(b -> b.getGymID().equals(gymId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Booking> findByBookingId(String bookingId) {
        System.out.println("DAO: Finding booking with ID: " + bookingId);
        return bookingTable.stream()
                .filter(b -> b.getBookingID().equals(bookingId))
                .findFirst();
    }

    @Override
    public List<Booking> findBySlotId(String slotId) {
        System.out.println("DAO: Fetching bookings for slot ID: " + slotId);
        return bookingTable.stream()
                .filter(b -> b.getSlotID().equals(slotId))
                .collect(Collectors.toList());
    }
}
