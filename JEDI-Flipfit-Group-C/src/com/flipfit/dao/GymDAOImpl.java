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

    static {
        Gym gym1 = new Gym();
        gym1.setGymID("gym01");
        gym1.setName("Flex Fitness");
        gym1.setAddress("Koramangala, Bangalore");
        gym1.setGymOwnerID("owner001");
        gymTable.add(gym1);

        Gym gym2 = new Gym();
        gym2.setGymID("gym02");
        gym2.setName("Iron Paradise");
        gym2.setAddress("Indiranagar, Bangalore");
        gym2.setGymOwnerID("owner001");
        gymTable.add(gym2);
    }

    @Override
    public void save(Gym gym) {
        System.out.println("DAO: Saving gym " + gym.getName());
        gymTable.add(gym);
    }

    @Override
    public Optional<Gym> findByGymId(String gymId) {
        System.out.println("DAO: Searching for gym with ID: " + gymId);
        return gymTable.stream()
                .filter(g -> g.getGymID().equals(gymId))
                .findFirst();
    }

    @Override
    public List<Gym> findByOwnerId(String ownerId) {
        System.out.println("DAO: Fetching gyms for owner ID: " + ownerId);
        return gymTable.stream()
                .filter(g -> g.getGymOwnerID().equals(ownerId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Gym> getAll() {
        System.out.println("DAO: Fetching all gyms.");
        return new ArrayList<>(gymTable);
    }

    @Override
    public boolean remove(String gymId) {
        System.out.println("DAO: Removing gym with ID: " + gymId);
        return gymTable.removeIf(g -> g.getGymID().equals(gymId));
    }

    @Override
    public boolean update(Gym gymToUpdate) {
        System.out.println("DAO: Updating gym with ID: " + gymToUpdate.getGymID());
        for (int i = 0; i < gymTable.size(); i++) {
            if (gymTable.get(i).getGymID().equals(gymToUpdate.getGymID())) {
                gymTable.set(i, gymToUpdate);
                return true;
            }
        }
        return false;
    }
}
