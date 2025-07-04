package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.TimeSlot;
import com.flipfit.dao.BookingDAOImpl;
import com.flipfit.dao.GymDAOImpl;
import com.flipfit.dao.GymOwnerDAOImpl;
import com.flipfit.dao.TimeSlotDAOImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for handling business logic for a Gym Owner.
 * This class implements the FlipFitGymOwnerServiceInterface and provides concrete
 * logic for each of the operations by interacting with the DAO layer.
 */
public class FlipFitGymOwnerServiceImpl implements FlipFitGymOwnerServiceInterface {

    // DAOs to interact with the data layer
    private final GymDAOImpl gymDAO = new GymDAOImpl();
    private final BookingDAOImpl bookingDAO = new BookingDAOImpl();
    private final TimeSlotDAOImpl timeSlotDAO = new TimeSlotDAOImpl();
    private final GymOwnerDAOImpl gymOwnerDAO = new GymOwnerDAOImpl();


    @Override
    public Optional<GymOwner> getGymOwner(String userId){
        return gymOwnerDAO.findById(userId);
    }

    /**
     * Retrieves all bookings for a specific gym by calling the Booking DAO.
     *
     * @param gymId The ID of the gym.
     * @return A list of Booking objects for that gym.
     */
    @Override
    public List<Booking> viewGymBookings(String gymId) {
        System.out.println("Service: Fetching bookings for Gym ID " + gymId);
        return bookingDAO.findByGymId(gymId);
    }

    /**
     * Updates the details of a gym by calling the Gym DAO.
     *
     * @param details The Gym object with updated information.
     */
    @Override
    public void manageGymDetails(Gym details) {
        try {
            System.out.println("Service: Updating details for Gym ID " + details.getGymID());
            gymDAO.update(details); // Assumes GymDAO has an update method
            System.out.println("Gym details updated successfully for: " + details.getName());
        } catch (Exception e) {
            System.err.println("Error updating gym details: " + e.getMessage());
        }
    }

    /**
     * Registers a new gym by calling the Gym DAO.
     *
     * @param newGym The new Gym object to be created.
     * @return true if registration is successful, false otherwise.
     */
    @Override
    public boolean registerNewGym(Gym newGym) {
        try {
            System.out.println("Service: Registering new gym '" + newGym.getName() + "'");
            gymDAO.save(newGym); // Assumes GymDAO has a create method
            return true;
        } catch (Exception e) {
            System.err.println("Error registering new gym: " + e.getMessage());
            return false;
        }
    }

    /**
     * Adds a new time slot by calling the TimeSlot DAO.
     *
     * @param slot The new TimeSlot object to be created.
     * @return true if the slot is added successfully, false otherwise.
     */
    @Override
    public boolean addTimeSlot(TimeSlot slot) {
//        try {
//            System.out.println("Service: Adding new time slot for Gym ID " + slot.getGymID());
//            timeSlotDAO.create(slot); // Assumes TimeSlotDAO has a create method
//            return true;
//        } catch (Exception e) {
//            System.err.println("Error adding time slot: " + e.getMessage());
//            return false;
//        }
        return true;
    }

    /**
     * Retrieves all gyms belonging to a specific owner.
     * It fetches all gyms and then filters them based on the owner's ID.
     *
     * @param ownerId The ID of the gym owner.
     * @return A list of Gym objects owned by the specified owner.
     */
    @Override
    public List<Gym> getAllGymsOfOwner(String ownerId) {
        System.out.println("Service: Fetching all gyms for owner ID: " + ownerId);
        // Fetch all gyms from the data source
        List<Gym> allGyms = gymDAO.getAll();
        // Filter the list to find gyms belonging to the specified owner
        return allGyms.stream()
                .filter(gym -> gym.getGymOwnerID().equals(ownerId))
                .collect(Collectors.toList());
    }
}