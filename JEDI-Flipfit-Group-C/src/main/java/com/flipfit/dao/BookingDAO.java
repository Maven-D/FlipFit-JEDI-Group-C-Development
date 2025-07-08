package com.flipfit.dao;

import com.flipfit.bean.Booking;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) interface for managing bookings.
 * It defines the standard operations to be performed on Booking objects.
 */
public interface BookingDAO {

    /**
     * Saves a new booking record.
     * @param booking The Booking object to save.
     */
    void save(Booking booking);

    /**
     * Updates an existing booking record.
     * @param bookingToUpdate The Booking object with updated information.
     * @return true if the update was successful, false otherwise.
     */
    boolean update(Booking bookingToUpdate);

    /**
     * Finds a booking by its unique ID.
     * @param bookingId The ID of the booking to find.
     * @return an Optional containing the booking if found, or an empty Optional.
     */
    Optional<Booking> findByBookingId(String bookingId);

    /**
     * Finds all bookings made by a specific user.
     * @param userId The ID of the user.
     * @return a list of bookings for the given user.
     */
    List<Booking> findByUserId(String userId);

    /**
     * Finds all bookings for a specific gym.
     * @param gymId The ID of the gym.
     * @return a list of bookings for the given gym.
     */
    List<Booking> findByGymId(String gymId);

    /**
     * Finds all bookings for a specific time slot.
     * @param slotId The ID of the time slot.
     * @return a list of bookings for the given slot.
     */
    List<Booking> findBySlotId(String slotId);

    /**
     * Cancels a booking by its ID, typically by updating its status.
     * @param bookingId The ID of the booking to cancel.
     * @return true if the cancellation was successful, false otherwise.
     */
    boolean cancelBooking(String bookingId);

    /**
     * Retrieves all bookings from the data store.
     * @return a list of all bookings.
     */
    List<Booking> findAll();
}