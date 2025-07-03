package com.flipfit.business;

import com.flipfit.bean.TimeSlot;

import java.time.LocalDate;
import java.util.List;

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
