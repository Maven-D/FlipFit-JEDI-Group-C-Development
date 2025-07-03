// File: com/flipfit/dao/GymDAO.java
package com.flipfit.dao;

import com.flipfit.bean.Gym;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Gym operations.
 */
public interface GymDAO {
    void save(Gym gym);
    Optional<Gym> findByGymId(String gymId);
    List<Gym> findByOwnerId(String ownerId);
    List<Gym> getAll();
    boolean remove(String gymId);
    boolean update(Gym gym);
}
