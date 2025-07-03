package com.flipfit.bean;

/**
 * Represents a gym facility in the FlipFit system.
 * It contains details about the gym such as its ID, name, and address.
 */
public class Gym {

    private String gymID;
    private String name;
    private String address;
    private String gymOwnerID;
    private String approvalStatus;
    private String gstNumber;


    /**
	 * @return the gstNumber
	 */
	public String getGstNumber() {
		return gstNumber;
	}

	/**
	 * @param gstNumber the gstNumber to set
	 */
	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	/**
	 * @return the approvalStatus
	 */
	public String getApprovalStatus() {
		return approvalStatus;
	}

	/**
	 * @param approvalStatus the approvalStatus to set
	 */
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

    public String getGymOwnerID() {
        return gymOwnerID;
    }

    public void setGymOwnerID(String gymOwnerID) {
        this.gymOwnerID = gymOwnerID;
    }

    // Getters and Setters

    /**
     * Gets the unique identifier for the gym.
     * @return A string representing the gym ID.
     */
    public String getGymID() {
        return gymID;
    }

    /**
     * Sets the unique identifier for the gym.
     * @param gymID A string containing the gym ID.
     */
    public void setGymID(String gymID) {
        this.gymID = gymID;
    }

    /**
     * Gets the name of the gym.
     * @return A string representing the gym's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the gym.
     * @param name A string containing the gym's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the physical address of the gym.
     * @return A string representing the gym's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the physical address of the gym.
     * @param address A string containing the gym's address.
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
