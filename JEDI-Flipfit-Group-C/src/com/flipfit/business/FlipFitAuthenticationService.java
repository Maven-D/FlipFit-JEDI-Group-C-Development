package com.flipfit.business;

import com.flipfit.bean.BaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class to handle user authentication logic.
 * This class provides methods to verify user credentials and manage tokens.
 */
public class FlipFitAuthenticationService {

    // In-memory list to simulate a database of users.
    private static List<BaseUser> users = new ArrayList<>();

    /**
     * Verifies user credentials against the stored user data.
     * In a real application, this would involve checking a database and using a
     * proper password hashing library like BCrypt.
     *
     * @param email The user's email address.
     * @param password The user's plain-text password.
     * @return The BaseUser object if credentials are valid, otherwise null.
     */
    public BaseUser verifyCredentials(String email, String password) {
        System.out.println("Attempting to verify credentials for email: " + email);
        for (BaseUser user : users) {
            // In a real app, compare password with a hashed version.
            // For this example, we'll do a simple string comparison on the hash.
            // A real implementation: passwordEncoder.matches(password, user.getPasswordHash())
            if (user.getEmail().equalsIgnoreCase(email) && user.getPasswordHash().equals(password)) {
                System.out.println("Credentials verified successfully.");
                return user;
            }
        }
        System.out.println("Invalid credentials.");
        return null;
    }

    /**
     * Validates a user token to authorize actions.
     * This is a placeholder for a real token validation system (e.g., JWT).
     *
     * @param token The token string to validate.
     * @return The BaseUser associated with the token if valid, otherwise null.
     */
    public BaseUser validateToken(String token) {
        // In a real application, you would decode a JWT or look up a session token.
        // For this example, we'll assume the token is the user's ID.
        System.out.println("Validating token: " + token);
        for (BaseUser user : users) {
            if (user.getUserID().equals(token)) {
                System.out.println("Token is valid for user: " + user.getName());
                return user;
            }
        }
        System.out.println("Token is invalid.");
        return null;
    }

    /**
     * Helper method to add a user to our simulated database.
     * This would be handled by a data access layer in a real app.
     * @param user The user to add.
     */
    public void registerUser(BaseUser user) {
        users.add(user);
        System.out.println("User registered: " + user.getName());
    }
}
