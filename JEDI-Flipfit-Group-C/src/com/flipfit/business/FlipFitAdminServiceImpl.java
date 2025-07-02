package com.flipfit.business;

import com.flipfit.bean.BaseUser;
import com.flipfit.bean.Customer;
import com.flipfit.bean.Gym;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling business logic for a System Administrator.
 */
public class FlipFitAdminServiceImpl implements FlipFitAdminServiceInterface {

    // In-memory lists to simulate database tables.
    private static List<BaseUser> allUsers = new ArrayList<>();
    private static List<Gym> allGyms = new ArrayList<>();

    /**
     * Adds a new customer to the system.
     * @param customer The Customer object to add.
     */
    @Override
    public void addCustomer(Customer customer) {
        allUsers.add(customer);
        System.out.println("Admin added a new customer: " + customer.getName());
    }

    /**
     * Removes a customer from the system.
     * @param customer The Customer object to remove.
     * @return true if removal was successful, false otherwise.
     */
    @Override
    public boolean removeCustomer(Customer customer) {
        System.out.println("Admin attempting to remove customer: " + customer.getName());
        return allUsers.removeIf(u -> u.getUserID().equals(customer.getUserID()));
    }

    /**
     * Adds a new gym to the system.
     * @param gym The Gym object to add.
     */
    @Override
    public void addGym(Gym gym) {
        allGyms.add(gym);
        System.out.println("Admin added a new gym: " + gym.getName());
    }

    /**
     * Removes a gym from the system.
     * @param gym The Gym object to remove.
     * @return true if removal was successful, false otherwise.
     */
    @Override
    public boolean removeGym(Gym gym) {
        System.out.println("Admin attempting to remove gym: " + gym.getName());
        return allGyms.removeIf(g -> g.getGymID().equals(gym.getGymID()));
    }
}
