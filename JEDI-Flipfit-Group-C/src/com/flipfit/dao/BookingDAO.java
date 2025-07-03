package com.flipfit.dao;

import com.flipfit.bean.Booking;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Booking operations.
 */
public interface BookingDAO {
    void save(Booking booking);
    boolean update(Booking booking);
    List<Booking> findByUserId(String userId);
    List<Booking> findByGymId(String gymId);
    Optional<Booking> findByBookingId(String bookingId);
    List<Booking> findBySlotId(String slotId);
}