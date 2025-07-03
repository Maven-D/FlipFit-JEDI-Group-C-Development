// File: com/flipfit/dao/UserDAOImpl.java
package com.flipfit.dao;

import com.flipfit.bean.BaseUser;
import com.flipfit.bean.Customer;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.SystemAdmin;
import com.flipfit.bean.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the UserDAO interface.
 * Simulates a database with hardcoded initial data.
 */
public class UserDAOImpl implements UserDAO {

    private static final List<BaseUser> userTable = new ArrayList<>();

    static {
        // Roles
        UserRole adminRole = new UserRole(1, "ADMIN", "System Administrator");
        UserRole ownerRole = new UserRole(2, "GYM_OWNER", "Gym Owner");
        UserRole customerRole = new UserRole(3, "CUSTOMER", "End User");

        // Users
        SystemAdmin admin = new SystemAdmin();
        admin.setUserID("admin001");
        admin.setName("Super Admin");
        admin.setEmail("admin@flipfit.com");
        admin.setPasswordHash("adminpass");
        admin.setRole(adminRole);
        userTable.add(admin);

        GymOwner owner = new GymOwner();
        owner.setUserID("owner001");
        owner.setName("John's Gyms");
        owner.setEmail("owner@flipfit.com");
        owner.setPasswordHash("ownerpass");
        owner.setRole(ownerRole);
        userTable.add(owner);

        Customer customer = new Customer();
        customer.setUserID("cust001");
        customer.setName("Jane Doe");
        customer.setEmail("customer@flipfit.com");
        customer.setPasswordHash("custpass");
        customer.setRole(customerRole);
        userTable.add(customer);
    }

    @Override
    public void saveUser(BaseUser user) {
        System.out.println("DAO: Saving user " + user.getName());
        userTable.add(user);
    }

    @Override
    public Optional<BaseUser> findByEmail(String email) {
        System.out.println("DAO: Searching for user with email: " + email);
        return userTable.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public Optional<BaseUser> findById(String userId) {
        System.out.println("DAO: Searching for user with ID: " + userId);
        return userTable.stream()
                .filter(u -> u.getUserID().equals(userId))
                .findFirst();
    }

    @Override
    public List<BaseUser> getAll() {
        System.out.println("DAO: Fetching all users.");
        return new ArrayList<>(userTable);
    }

    @Override
    public List<BaseUser> getByRole(UserRole role) {
        System.out.println("DAO: Fetching all users with role: " + role.getRoleName());
        return userTable.stream()
                .filter(u -> u.getRole().getRoleId() == role.getRoleId())
                .collect(Collectors.toList());
    }

    @Override
    public void removeUser(BaseUser user) {
        userTable.removeIf(u -> u.getUserID().equals(user.getUserID()));
    }
}
