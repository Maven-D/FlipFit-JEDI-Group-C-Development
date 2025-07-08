package com.flipfit.business;

import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import com.flipfit.dao.GymDAO;
import com.flipfit.dao.GymDAOImpl;
import com.flipfit.dao.GymOwnerDAOImpl;
import com.flipfit.dao.UserDAO; // Assuming a generic UserDAO interface exists

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for handling business logic for a System Administrator.
 * This class delegates all data access operations to the DAO layer.
 */
public class FlipFitAdminServiceImpl implements FlipFitAdminServiceInterface {

    // DAO instances to handle data persistence
    private final GymDAO gymDao = new GymDAOImpl();
    // Updated to use the new JDBC-based GymOwnerDAOImpl
    private final GymOwnerDAOImpl gymOwnerDao = new GymOwnerDAOImpl();

    @Override
    public List<Gym> getPendingGyms() {
        System.out.println("Service: Fetching gyms pending approval...");
        return gymDao.getAll().stream()
                .filter(gym -> "PENDING".equalsIgnoreCase(gym.getApprovalStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Gym> getAllGyms() {
        System.out.println("Service: Fetching all approved gyms...");
        return gymDao.getAll().stream()
                .filter(gym -> "CONFIRMED".equalsIgnoreCase(gym.getApprovalStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public void approveGym(String gymId) {
        Optional<Gym> gymOptional = gymDao.findByGymId(gymId);

        if (gymOptional.isPresent()) {
            Gym gymToUpdate = gymOptional.get();
            // Corrected to match the ENUM value in the database schema
            gymToUpdate.setApprovalStatus("CONFIRMED");

            // Delegate the update operation to the DAO
            boolean isUpdated = gymDao.update(gymToUpdate);

            if (isUpdated) {
                System.out.println("Service: Gym approved successfully -> " + gymToUpdate.getName());
            } else {
                System.out.println("Service: Failed to update and approve gym with ID: " + gymId);
            }
        } else {
            System.out.println("Service: Could not find gym with ID " + gymId + " to approve.");
        }
    }

    @Override
    public void rejectGym(String gymId) {
        Optional<Gym> gymOptional = gymDao.findByGymId(gymId);
        if (gymOptional.isPresent() && "PENDING".equalsIgnoreCase(gymOptional.get().getApprovalStatus())) {
            Gym gymToUpdate = gymOptional.get();
            // Corrected to match the ENUM value in the database schema
            gymToUpdate.setApprovalStatus("REJECTED");
            gymDao.update(gymToUpdate);
            System.out.println("Service: Pending gym request rejected for ID: " + gymId);
        } else {
            System.out.println("Service: Gym not found or not in pending state for ID: " + gymId);
        }
    }

    @Override
    public void removeGym(String gymId) {
        boolean removed = gymDao.remove(gymId);
        if (removed) {
            System.out.println("Service: Approved gym removed successfully for ID: " + gymId);
        } else {
            System.out.println("Service: Failed to remove gym with ID: " + gymId);
        }
    }

    @Override
    public List<GymOwner> getPendingGymOwners() {
        System.out.println("Service: Fetching gym owners pending approval...");
        return gymOwnerDao.getAll().stream()
                .filter(owner -> "PENDING".equalsIgnoreCase(owner.getApprovalStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GymOwner> getAllGymOwners() {
        System.out.println("Service: Fetching all approved gym owners...");
        return gymOwnerDao.getAll().stream()
                .filter(owner -> "CONFIRMED".equalsIgnoreCase(owner.getApprovalStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public void approveGymOwner(String ownerId) {
        Optional<GymOwner> ownerOptional = gymOwnerDao.findById(ownerId);

        if (ownerOptional.isPresent()) {
            GymOwner ownerToUpdate = ownerOptional.get();
            // Corrected to match the ENUM value in the database schema
            ownerToUpdate.setApprovalStatus("CONFIRMED");

            // Delegate the update to the DAO method
            boolean isUpdated = gymOwnerDao.update(ownerToUpdate);

            if (isUpdated) {
                System.out.println("Service: Gym Owner approved successfully -> " + ownerToUpdate.getName());
            } else {
                System.out.println("Service: Failed to approve gym owner with ID: " + ownerId);
            }
        } else {
            System.out.println("Service: Could not find gym owner with ID " + ownerId + " to approve.");
        }
    }

    @Override
    public void rejectGymOwner(String ownerId) {
        Optional<GymOwner> ownerOptional = gymOwnerDao.findById(ownerId);
        if (ownerOptional.isPresent()) {
            GymOwner owner = ownerOptional.get();
            if ("PENDING".equalsIgnoreCase(owner.getApprovalStatus())) {
                // Corrected to match the ENUM value in the database schema
                owner.setApprovalStatus("REJECTED");
                gymOwnerDao.update(owner);
                System.out.println("Service: Pending gym owner request rejected for ID: " + ownerId);
            }
        } else {
            System.out.println("Service: Gym owner not found or not in pending state for ID: " + ownerId);
        }
    }

    @Override
    public void removeGymOwner(String ownerId) {
        Optional<GymOwner> ownerOptional = gymOwnerDao.findById(ownerId);
        if (ownerOptional.isPresent()) {
            GymOwner owner = ownerOptional.get();
            if ("CONFIRMED".equalsIgnoreCase(owner.getApprovalStatus())) {
                gymOwnerDao.removeUser(owner);
                System.out.println("Service: Approved gym owner removed for ID: " + ownerId);
            }
        } else {
            System.out.println("Service: Gym owner not found or not in approved state for ID: " + ownerId);
        }
    }
}
