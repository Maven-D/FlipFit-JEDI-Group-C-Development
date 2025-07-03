package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling business logic for a System Administrator.
 * This class provides a concrete implementation of the FlipFitAdminServiceInterface,
 * using in-memory lists to simulate a database and aligning with the provided
 * bean structures.
 */
public class FlipFitAdminServiceImpl implements FlipFitAdminServiceInterface {

    // In-memory lists to simulate database tables for Gym Owners and Gyms.
    private static final List<GymOwner> allGymOwners = new ArrayList<>();
    private static final List<Gym> allGyms = new ArrayList<>();

    // Static block to seed initial data for demonstration purposes.
    // NOTE: This assumes constructors exist in your bean classes for initialization.
    static {
        // Create and add Gym Owners
        GymOwner owner1 = new GymOwner();
        owner1.setUserID("owner1");
        owner1.setName("John Doe");
        owner1.setEmail("john.doe@example.com");
        owner1.setPasswordHash("hashed_pass_1");
        // Updated to use the UserRole class
        owner1.setRole(new UserRole(2, "GYM_OWNER", "Manages gyms and bookings"));
        owner1.setApprovalStatus("APPROVED");
        allGymOwners.add(owner1);

        GymOwner owner2 = new GymOwner();
        owner2.setUserID("owner2");
        owner2.setName("Jane Smith");
        owner2.setEmail("jane.smith@example.com");
        owner2.setPasswordHash("hashed_pass_2");
        // Updated to use the UserRole class
        owner2.setRole(new UserRole(2, "GYM_OWNER", "Manages gyms and bookings"));
        owner2.setApprovalStatus("PENDING");
        allGymOwners.add(owner2);

        // Create and add Gyms
        Gym gym1 = new Gym();
        gym1.setGymID("gym1");
        gym1.setName("Pumping Iron");
        gym1.setAddress("Downtown");
        gym1.setGymOwnerID("owner1");
        gym1.setGstNumber("GSTIN123");
        gym1.setApprovalStatus("APPROVED");
        allGyms.add(gym1);

        Gym gym2 = new Gym();
        gym2.setGymID("gym2");
        gym2.setName("Cardio Central");
        gym2.setAddress("Uptown");
        gym2.setGymOwnerID("owner2");
        gym2.setGstNumber("GSTIN456");
        gym2.setApprovalStatus("PENDING");
        allGyms.add(gym2);

        Gym gym3 = new Gym();
        gym3.setGymID("gym3");
        gym3.setName("Flex Fitness");
        gym3.setAddress("Midtown");
        gym3.setGymOwnerID("owner1");
        gym3.setGstNumber("GSTIN789");
        gym3.setApprovalStatus("APPROVED");
        allGyms.add(gym3);
    }

    /**
     * Adds a new gym to the system. The gym will be in PENDING status by default.
     * @param gym The Gym object to add.
     */
    public void addGym(Gym gym) {
        // New gyms are added with a pending status by default
        gym.setApprovalStatus("PENDING");
        allGyms.add(gym);
        System.out.println("New gym added for approval: " + gym.getName());
    }

    /**
     * Adds a new gym owner to the system. The owner will be in PENDING status by default.
     * @param owner The GymOwner object to add.
     */
    public void addGymOwner(GymOwner owner) {
        // New owners are added with a pending status by default
        owner.setApprovalStatus("PENDING");
        allGymOwners.add(owner);
        System.out.println("New gym owner added for approval: " + owner.getName());
    }

    @Override
    public List<Gym> getPendingGyms() {
        System.out.println("Fetching gyms pending approval...");
        return allGyms.stream()
                .filter(gym -> "PENDING".equalsIgnoreCase(gym.getApprovalStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Gym> getAllGyms() {
        System.out.println("Fetching all approved gyms...");
        return allGyms.stream()
                .filter(gym -> "APPROVED".equalsIgnoreCase(gym.getApprovalStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public void approveGym(String gymId) {
        allGyms.stream()
                .filter(gym -> gym.getGymID().equals(gymId))
                .findFirst()
                .ifPresent(gym -> {
                    gym.setApprovalStatus("APPROVED");
                    System.out.println("Gym approved: " + gym.getName());
                });
    }

    @Override
    public void rejectGym(String gymId) {
        // Rejection implies removing the pending request
        boolean removed = allGyms.removeIf(gym -> gym.getGymID().equals(gymId) && "PENDING".equalsIgnoreCase(gym.getApprovalStatus()));
        if (removed) {
            System.out.println("Pending gym request rejected and removed for ID: " + gymId);
        }
    }

    @Override
    public void removeGym(String gymId) {
        // This removes an already approved gym
        boolean removed = allGyms.removeIf(gym -> gym.getGymID().equals(gymId) && "APPROVED".equalsIgnoreCase(gym.getApprovalStatus()));
        if (removed) {
            System.out.println("Approved gym removed successfully for ID: " + gymId);
        }
    }

    @Override
    public List<GymOwner> getPendingGymOwners() {
        System.out.println("Fetching gym owners pending approval...");
        return allGymOwners.stream()
                .filter(owner -> "PENDING".equalsIgnoreCase(owner.getApprovalStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GymOwner> getAllGymOwners() {
        System.out.println("Fetching all approved gym owners...");
        return allGymOwners.stream()
                .filter(owner -> "APPROVED".equalsIgnoreCase(owner.getApprovalStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public void approveGymOwner(String ownerId) {
        allGymOwners.stream()
                .filter(owner -> owner.getUserID().equals(ownerId))
                .findFirst()
                .ifPresent(owner -> {
                    owner.setApprovalStatus("APPROVED");
                    System.out.println("Gym Owner approved: " + owner.getName());
                });
    }

    @Override
    public void rejectGymOwner(String ownerId) {
        // Rejection implies removing the pending request
        boolean removed = allGymOwners.removeIf(owner -> owner.getUserID().equals(ownerId) && "PENDING".equalsIgnoreCase(owner.getApprovalStatus()));
        if (removed) {
            System.out.println("Pending gym owner request rejected and removed for ID: " + ownerId);
        }
    }

    @Override
    public void removeGymOwner(String ownerId) {
        // This removes an already approved gym owner
        boolean removed = allGymOwners.removeIf(owner -> owner.getUserID().equals(ownerId) && "APPROVED".equalsIgnoreCase(owner.getApprovalStatus()));
        if (removed) {
            System.out.println("Approved gym owner removed successfully for ID: " + ownerId);
        }
    }
}
