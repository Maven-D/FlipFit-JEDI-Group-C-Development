// File: com/flipfit/dao/UserDAO.java
package com.flipfit.dao;

import com.flipfit.bean.BaseUser;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for User operations.
 */
public interface UserDAO {
    /**
     * Saves a new user to the database.
     * @param user The user object to save.
     */
    void save(BaseUser user);

    /**
     * Finds a user by their email address.
     * @param email The email to search for.
     * @return An Optional containing the user if found.
     */
    Optional<BaseUser> findByEmail(String email);

    /**
     * Finds a user by their ID.
     * @param userId The ID to search for.
     * @return An Optional containing the user if found.
     */
    Optional<BaseUser> findById(String userId);

    /**
     * Retrieves all users from the database.
     * @return A list of all users.
     */
    List<BaseUser> findAll();
}