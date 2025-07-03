package com.flipfit.business;

import com.flipfit.bean.TimeSlot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling business logic related to Gyms.
 */
public class FlipFitGymServiceImpl implements FlipFitGymServiceInterface {

    // In-memory list to simulate a database of all available time slots.
    // This is the single source of truth for all time slots.
    private static final List<TimeSlot> allTimeSlots = new ArrayList<>();

    public static List<TimeSlot> getAllTimeSlots() {
        return allTimeSlots;
    }

    /**
     * Gets the available time slots for a specific gym on a given date.
     *
     * @param gymId The ID of the gym.
     * @param date The date for which to check availability.
     * @return A list of available TimeSlot objects.
     */
    @Override
<<<<<<< HEAD
	public List<TimeSlot> getAvailability(String gymId, LocalDate date) {
=======
    public List<TimeSlot> getAvailability(String gymId, LocalDate date) {
>>>>>>> 4678108a044592849fba849c12eb21ae5b8698e0
        System.out.println("Fetching availability for gym ID: " + gymId + " on date: " + date);
        return allTimeSlots.stream()
                .filter(slot -> slot.getGymID().equals(gymId) && slot.getDate().isEqual(date) && slot.getAvailableSeats() > 0)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to add a timeslot to the system.
     * @param slot The TimeSlot to add.
     */
    @Override
<<<<<<< HEAD
	public void addTimeSlot(TimeSlot slot) {
=======
    public void addTimeSlot(TimeSlot slot) {
>>>>>>> 4678108a044592849fba849c12eb21ae5b8698e0
        allTimeSlots.add(slot);
        System.out.println("New timeslot added for gym " + slot.getGymID() + " at " + slot.getStartTime());
    }
}
