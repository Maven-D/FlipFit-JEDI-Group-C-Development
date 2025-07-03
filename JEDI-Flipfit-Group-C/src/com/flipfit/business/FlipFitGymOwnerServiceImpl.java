package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Gym;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling business logic for a Gym Owner.
 */
public class FlipFitGymOwnerServiceImpl implements FlipFitGymOwnerServiceInterface {

    private static List<Booking> allBookings = new ArrayList<>();
    private static List<Gym> allGyms = new ArrayList<>();

    /**
     * Retrieves all bookings for a specific gym.
     *
     * @param gymId The ID of the gym.
     * @return A list of Booking objects for that gym.
     */
    @Override
<<<<<<< HEAD
	public List<Booking> viewGymBookings(String gymId) {
=======
    public List<Booking> viewGymBookings(String gymId) {
>>>>>>> 4678108a044592849fba849c12eb21ae5b8698e0
        System.out.println("Gym Owner fetching bookings for gym ID: " + gymId);
        // This requires linking a booking to a gym.
        // We'd need to add gymId to the Booking bean or cross-reference through TimeSlot.
        // For now, returning an empty list as a placeholder.
        return new ArrayList<>();
    }

    /**
     * Updates the details of a gym.
     *
     * @param details The Gym object with updated information.
     */
    @Override
<<<<<<< HEAD
	public void manageGymDetails(Gym details) {
=======
    public void manageGymDetails(Gym details) {
>>>>>>> 4678108a044592849fba849c12eb21ae5b8698e0
        for (int i = 0; i < allGyms.size(); i++) {
            if (allGyms.get(i).getGymID().equals(details.getGymID())) {
                allGyms.set(i, details); // Replace the old gym object with the new one.
                System.out.println("Gym details updated for: " + details.getName());
                return;
            }
        }
        System.out.println("Gym with ID " + details.getGymID() + " not found for update.");
    }
}
