package com.flipfit.business;

import com.flipfit.bean.TimeSlot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling business logic related to Gyms.
 */
public class GymBusiness {

    // In-memory list to simulate a database of all available time slots.
    private static List<TimeSlot> allTimeSlots = new ArrayList<>();

    /**
     * Gets the available time slots for a specific gym on a given date.
     *
     * @param gymId The ID of the gym.
     * @param date The date for which to check availability.
     * @return A list of available TimeSlot objects.
     */
    public List<TimeSlot> getAvailability(String gymId, LocalDate date) {
        System.out.println("Fetching availability for gym ID: " + gymId + " on date: " + date);
        return allTimeSlots.stream()
                .filter(slot -> slot.getGymID().equals(gymId) && slot.getDate().isEqual(date) && slot.getAvailableSeats() > 0)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to add a timeslot to the system.
     * @param slot The TimeSlot to add.
     */
    public void addTimeSlot(TimeSlot slot) {
        allTimeSlots.add(slot);
        System.out.println("New timeslot added for gym " + slot.getGymID() + " at " + slot.getStartTime());
    }
}
