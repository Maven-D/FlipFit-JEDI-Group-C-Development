package com.flipfit.business;

import com.flipfit.bean.TimeSlot;
import com.flipfit.dao.TimeSlotDAO;
import com.flipfit.dao.TimeSlotDAOImpl;

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
//    private static final List<TimeSlot> allTimeSlots = new TimeSlotDAOImpl().getAll();
    private TimeSlotDAO timeSlotDAO = new TimeSlotDAOImpl();

    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotDAO.getAll();
    }

    /**
     * Gets the available time slots for a specific gym on a given date.
     *
     * @param gymId The ID of the gym.
     * @param date The date for which to check availability.
     * @return A list of available TimeSlot objects.
     */
    @Override
    public List<TimeSlot> getAvailability(String gymId, LocalDate date) {
//        System.out.println("Fetching availability for gym ID: " + gymId + " on date: " + date);
//        return allTimeSlots.stream()
//                .filter(slot -> slot.getGymID().equals(gymId) && slot.getDate().isEqual(date) && slot.getAvailableSeats() > 0)
//                .collect(Collectors.toList());
        return timeSlotDAO.findAvailableByGymIdAndDate(gymId, date);
    }

    /**
     * Helper method to add a timeslot to the system.
     * @param slot The TimeSlot to add.
     */
    @Override
    public void addTimeSlot(TimeSlot slot) {
//        allTimeSlots.add(slot);
//        System.out.println("New timeslot added for gym " + slot.getGymID() + " at " + slot.getStartTime());
        timeSlotDAO.save(slot);
    }
}
