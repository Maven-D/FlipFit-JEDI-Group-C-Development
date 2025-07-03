package com.flipfit.business;

<<<<<<< HEAD
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
=======
import com.flipfit.bean.Booking;
import com.flipfit.bean.Gym;

import java.util.List;

public interface FlipFitGymOwnerServiceInterface {
    List<Booking> viewGymBookings(String gymId);

    void manageGymDetails(Gym details);
}
>>>>>>> 4678108a044592849fba849c12eb21ae5b8698e0
