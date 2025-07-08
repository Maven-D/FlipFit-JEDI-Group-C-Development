package com.flipfit.bean;

/**
 * Represents a system administrator in the FlipFit system.
 * This class inherits all its properties from the BaseUser class.
 * A SystemAdmin has privileges to manage users and gyms across the platform.
 */
public class SystemAdmin extends BaseUser {
    // This class inherits all fields (userID, name, email, passwordHash)
    // and their getters/setters from BaseUser.
    // According to the class diagram, it does not have any additional
    // attributes of its own. Business logic related to a SystemAdmin
    // will be handled in the service/business layer.
}
