package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;

import java.util.List;

/**
 * Service interface for administrative functionalities in the FlipFit system.
 * This contract defines all the operations that a System Admin can perform,
 * such as managing gym and gym owner approvals and removals.
 */
public interface FlipFitAdminServiceInterface {

    /**
     * Retrieves a list of all gyms that are pending administrative approval.
     *
     * @return A List of Gym objects awaiting approval.
     */
    List<Gym> getPendingGyms();

    /**
     * Retrieves a list of all registered gyms in the system.
     *
     * @return A List of all Gym objects.
     */
    List<Gym> getAllGyms();

    /**
     * Approves a single pending gym.
     *
     * @param gymId The unique identifier of the gym to be approved.
     */
    void approveGym(String gymId);

    /**
     * Rejects a single pending gym request.
     *
     * @param gymId The unique identifier of the gym to be rejected.
     */
    void rejectGym(String gymId);

    /**
     * Removes an existing gym from the system.
     *
     * @param gymId The unique identifier of the gym to be removed.
     */
    void removeGym(String gymId);

    /**
     * Retrieves a list of all gym owners that are pending administrative approval.
     *
     * @return A List of GymOwner objects awaiting approval.
     */
    List<GymOwner> getPendingGymOwners();

    /**
     * Retrieves a list of all registered gym owners in the system.
     *
     * @return A List of all GymOwner objects.
     */
    List<GymOwner> getAllGymOwners();
    
    public void addGym(Gym gym);
    
    public void addGymOwner(GymOwner owner);

    /**
     * Approves a single pending gym owner.
     *
     * @param ownerId The unique identifier of the gym owner to be approved.
     */
    void approveGymOwner(String ownerId);

    /**
     * Rejects a single pending gym owner request.
     *
     * @param ownerId The unique identifier of the gym owner to be rejected.
     */
    void rejectGymOwner(String ownerId);

    /**
     * Removes an existing gym owner from the system.
     * This might also involve handling the gyms owned by them.
     *
     * @param ownerId The unique identifier of the gym owner to be removed.
     */
    void removeGymOwner(String ownerId);
}