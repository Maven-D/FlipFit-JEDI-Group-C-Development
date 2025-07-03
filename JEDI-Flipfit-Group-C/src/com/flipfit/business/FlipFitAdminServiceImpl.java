package com.flipfit.business;

import com.flipfit.bean.BaseUser;
import com.flipfit.bean.Customer;
import com.flipfit.bean.Gym;
import com.flipfit.dao.GymDAO;
import com.flipfit.dao.GymDAOImpl;
import com.flipfit.dao.UserDAO;
import com.flipfit.dao.UserDAOImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling business logic for a System Administrator.
 */
public class FlipFitAdminServiceImpl implements FlipFitAdminServiceInterface {

    // In-memory lists to simulate database tables.
    private UserDAO userDAO = new UserDAOImpl();
//    private static List<BaseUser> allUsers = new ArrayList<>();
    private GymDAO gymDAO = new GymDAOImpl();
//    private List<Gym> allGyms = gymDAO.getAll();

    /**
     * Adds a new customer to the system.
     * @param customer The Customer object to add.
     */
    @Override
    public void addCustomer(Customer customer) {
//        allUsers.add(customer);
        userDAO.saveUser(customer);
        System.out.println("Admin added a new customer: " + customer.getName());
    }

    /**
     * Removes a customer from the system.
     * @param customer The Customer object to remove.
     */
    @Override
    public void removeCustomer(Customer customer) {
        System.out.println("Admin attempting to remove customer: " + customer.getName());
        userDAO.removeUser(customer);
    }

    /**
     * Adds a new gym to the system.
     * @param gym The Gym object to add.
     */
    @Override
    public void addGym(Gym gym) {
        gymDAO.save(gym);
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
        gymDAO.remove(gym.getGymID());
        return true;
    }
}
