// File: com/flipfit/dao/GymDAO.java
package com.flipfit.dao;

import com.flipfit.bean.Gym;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Gym operations.
 */
public interface GymDAO {
    /**
     * Saves a new gym to the database.
     * @param gym The gym object to save.
     */
    void save(Gym gym);

    /**
     * Finds a gym by its ID.
     * @param gymId The ID of the gym.
     * @return An Optional containing the gym if found.
     */
    Optional<Gym> findById(String gymId);

    /**
     * Finds all gyms owned by a specific user.
     * @param ownerId The ID of the gym owner.
     * @return A list of gyms owned by the user.
     */
    List<Gym> findByGymId(String ownerId);

    /**
     * Retrieves all gyms from the database.
     * @return A list of all gyms.
     */
    List<Gym> findAll();

    /**
     * Removes a gym from the database.
     * @param gymId The ID of the gym to remove.
     * @return true if successful, false otherwise.
     */
    boolean remove(String gymId);
}