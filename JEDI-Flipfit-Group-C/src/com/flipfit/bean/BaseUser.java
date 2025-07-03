package com.flipfit.bean;

/**
 * BaseUser is an abstract class that provides the common attributes
 * for all types of users in the FlipFit system, such as User,
 * SystemAdmin, and GymOwner.
 * It encapsulates shared properties like user ID, name, email, and password.
 */
public abstract class BaseUser {

    private String userID;
    private String name;
    private String email;
    private String passwordHash;
    private UserRole role;
    private String phone;

    public String getAdhaar() {
        return adhaar;
    }

    public void setAdhaar(String adhaar) {
        this.adhaar = adhaar;
    }

    private String adhaar;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }



    // Getters and Setters

    /**
     * Gets the unique identifier for the user.
     * @return A string representing the user ID.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the unique identifier for the user.
     * @param userID A string containing the user ID.
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Gets the name of the user.
     * @return A string representing the user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * @param name A string containing the user's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address of the user.
     * @return A string representing the user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     * @param email A string containing the user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the hashed password for the user.
     * @return A string representing the hashed password.
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the hashed password for the user.
     * @param passwordHash A string containing the hashed password.
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}