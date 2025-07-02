// File: com/flipfit/dao/GymDAOImpl.java
package com.flipfit.dao;

import com.flipfit.bean.Gym;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the GymDAO interface.
 */
public class GymDAOImpl implements GymDAO {

    private static final List<Gym> gymTable = new ArrayList<>();



    @Override
    public void save(Gym gym) {
        // --- Database Call ---
        // String sql = "INSERT INTO gym (gymId, name, address, ownerId) VALUES (?, ?, ?, ?)";
        System.out.println("DAO: Saving gym " + gym.getName() + " to the database.");
        gymTable.add(gym);
    }

    @Override
    public Optional<Gym> findById(String gymId) {
        // --- Database Call ---
        // String sql = "SELECT * FROM gym WHERE gymId = ?";
        System.out.println("DAO: Searching for gym with ID: " + gymId);
        return gymTable.stream()
                .filter(g -> g.getGymID().equals(gymId))
                .findFirst();
    }

    @Override
    public List<Gym> findByGymId(String ownerId) {
        // --- Database Call ---
        // String sql = "SELECT * FROM gym WHERE ownerId = ?";
        System.out.println("DAO: Fetching gyms for owner ID: " + ownerId);
        return gymTable.stream()
                .filter(g -> g.getGymID().equals(ownerId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Gym> findAll() {
        // --- Database Call ---
        // String sql = "SELECT * FROM gym";
        System.out.println("DAO: Fetching all gyms.");
        return new ArrayList<>(gymTable);
    }

    @Override
    public boolean remove(String gymId) {
        // --- Database Call ---
        // String sql = "DELETE FROM gym WHERE gymId = ?";
        System.out.println("DAO: Removing gym with ID: " + gymId);
        return gymTable.removeIf(g -> g.getGymID().equals(gymId));
    }
}
