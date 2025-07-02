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

/**
 * Implementation of the UserDAO interface.
 * Simulates a database using an in-memory list.
 */
public class UserDAOImpl implements UserDAO {

    // In-memory list to simulate a database table for all users.
    private static final List<BaseUser> userTable = new ArrayList<>();

    // Static initializer block to pre-populate with hardcoded data.
    static {
        // System Admin
        SystemAdmin admin = new SystemAdmin();
        admin.setUserID("admin001");
        admin.setName("Super Admin");
        admin.setEmail("admin@flipfit.com");
        admin.setPasswordHash("adminpass"); // In a real app, this would be hashed
        userTable.add(admin);

        // Gym Owner
        GymOwner owner = new GymOwner();
        owner.setUserID("owner001");
        owner.setName("John's Gyms");
        owner.setEmail("owner@flipfit.com");
        owner.setPasswordHash("ownerpass");
        userTable.add(owner);

        // Customer
        Customer customer = new Customer();
        customer.setUserID("cust001");
        customer.setName("Jane Doe");
        customer.setEmail("customer@flipfit.com");
        customer.setPasswordHash("custpass");
        userTable.add(customer);
    }


    @Override
    public void save(BaseUser user) {
        // --- Database Call ---
        // String sql = "INSERT INTO users (userId, name, email, passwordHash, role) VALUES (?, ?, ?, ?, ?)";
        // PreparedStatement stmt = connection.prepareStatement(sql);
        // stmt.setString(1, user.getUserID());
        // ... set other parameters ...
        // stmt.executeUpdate();
        System.out.println("DAO: Saving user " + user.getName() + " to the database.");
        userTable.add(user);
    }

    @Override
    public Optional<BaseUser> findByEmail(String email) {
        // --- Database Call ---
        // String sql = "SELECT * FROM users WHERE email = ?";
        System.out.println("DAO: Searching for user with email: " + email);
        return userTable.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<BaseUser> findById(String userId) {
        // --- Database Call ---
        // String sql = "SELECT * FROM users WHERE userId = ?";
        System.out.println("DAO: Searching for user with ID: " + userId);
        return userTable.stream()
                .filter(u -> u.getUserID().equals(userId))
                .findFirst();
    }

    @Override
    public List<BaseUser> findAll() {
        // --- Database Call ---
        // String sql = "SELECT * FROM users";
        System.out.println("DAO: Fetching all users.");
        return new ArrayList<>(userTable);
    }
}
