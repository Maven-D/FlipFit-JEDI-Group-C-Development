package com.flipfit.bean;

/**
 * Represents a gym owner in the FlipFit system.
 * This class inherits all its properties from the BaseUser class.
 * A GymOwner can manage the details of their gym(s) and view bookings.
 */
public class GymOwner extends BaseUser {
    // This class inherits all fields (userID, name, email, passwordHash)
    // and their getters/setters from BaseUser.
    // According to the class diagram, it does not have any additional
    // attributes of its own. Business logic related to a GymOwner
    // will be handled in the service/business layer.
}
