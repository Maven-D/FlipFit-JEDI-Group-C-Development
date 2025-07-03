package com.flipfit.business;

import java.util.List;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Gym;

public interface FlipFitGymOwnerServiceInterface {

	/**
	 * Retrieves all bookings for a specific gym.
	 *
	 * @param gymId The ID of the gym.
	 * @return A list of Booking objects for that gym.
	 */
	List<Booking> viewGymBookings(String gymId);

	/**
	 * Updates the details of a gym.
	 *
	 * @param details The Gym object with updated information.
	 */
	void manageGymDetails(Gym details);

}