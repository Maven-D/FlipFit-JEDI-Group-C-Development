package com.flipfit.business;

<<<<<<< HEAD
import java.time.LocalDate;
import java.util.List;

import com.flipfit.bean.TimeSlot;

public interface FlipFitGymServiceInterface {

	/**
	 * Gets the available time slots for a specific gym on a given date.
	 *
	 * @param gymId The ID of the gym.
	 * @param date The date for which to check availability.
	 * @return A list of available TimeSlot objects.
	 */
	List<TimeSlot> getAvailability(String gymId, LocalDate date);

	/**
	 * Helper method to add a timeslot to the system.
	 * @param slot The TimeSlot to add.
	 */
	void addTimeSlot(TimeSlot slot);

}
=======
import com.flipfit.bean.TimeSlot;

import java.time.LocalDate;
import java.util.List;

public interface FlipFitGymServiceInterface {
    /**
     * Public static method to allow other classes to access the master list.
     *
     * @return The list of all time slots.
     */
    static List<TimeSlot> getAllTimeSlots() {
        return FlipFitGymServiceImpl.getAllTimeSlots();
    }

    List<TimeSlot> getAvailability(String gymId, LocalDate date);

    void addTimeSlot(TimeSlot slot);
}
>>>>>>> 4678108a044592849fba849c12eb21ae5b8698e0
