package com.flipfit.client;

import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.SystemAdmin;
import com.flipfit.business.FlipFitAdminServiceImpl;
import com.flipfit.business.FlipFitAdminServiceInterface;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Client view for the System Administrator.
 * Provides a menu-driven interface for admin-specific actions.
 */
public class FlipFitAdminMenu {


    private FlipFitAdminServiceInterface flipFitAdminService = new FlipFitAdminServiceImpl();


    /**
     * Displays the main menu for the System Administrator and handles input.
     * @param scanner The Scanner object for user input.
     * @param admin The currently logged-in SystemAdmin.
     */
    public void showAdminMenu(Scanner scanner, SystemAdmin admin) {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("Logged in as: " + admin.getName());
            System.out.println("1. View Pending Gym approvals");
            System.out.println("2. View Pending Gym Owner approvals");
            System.out.println("3. Remove an Existing Gym");
            System.out.println("4. Remove an Existing Gym Owner");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewPendingGymApprovals(scanner);
                    break;
                case 2:
                    viewPendingGymOwnerApprovals(scanner);
                    break;
                case 3:
                    removeExistingGym(scanner);
                    break;
                case 4:
                    removeExistingGymOwner(scanner);
                    break; // Added missing break
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Fetches and displays pending gym approvals and allows the admin to approve or reject them.
     * @param scanner Scanner for user input
     */
    private void viewPendingGymApprovals(Scanner scanner) {
        System.out.println("\n--- Pending Gym Approvals ---");
        List<Gym> pendingGyms = flipFitAdminService.getPendingGyms();

        if (pendingGyms.isEmpty()) {
            System.out.println("No pending gym approvals.");
            return;
        }

        System.out.println("ID\t\tGym Name\tOwner Email");
        System.out.println("-------------------------------------------------");
        for (Gym gym : pendingGyms) {
            // Assuming Gym bean has getId(), getName(), and getOwnerEmail() methods
            System.out.println(gym.getGymID() + "\t" + gym.getName() + "\t\t" + gym.getGymOwnerID());
        }
        System.out.println("-------------------------------------------------");


        System.out.print("Enter the ID of the gym to process (or type 'exit' to return): ");
        String gymId = scanner.nextLine();

        if (gymId.equalsIgnoreCase("exit")) {
            return;
        }

        System.out.println("1. Approve");
        System.out.println("2. Reject");
        System.out.print("Choose an action: ");
        int action = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch(action) {
            case 1:
                flipFitAdminService.approveGym(gymId);
                System.out.println("Gym with ID " + gymId + " approved successfully.");
                break;
            case 2:
                flipFitAdminService.rejectGym(gymId);
                System.out.println("Gym with ID " + gymId + " rejected successfully.");
                break;
            default:
                System.out.println("Invalid action. Returning to menu.");
        }
    }

    /**
     * Fetches and displays pending gym owner approvals and allows the admin to approve or reject them.
     * @param scanner Scanner for user input
     */
    private void viewPendingGymOwnerApprovals(Scanner scanner) {
        System.out.println("\n--- Pending Gym Owner Approvals ---");
        List<GymOwner> pendingOwners = flipFitAdminService.getPendingGymOwners();

        if (pendingOwners.isEmpty()) {
            System.out.println("No pending gym owner approvals.");
            return;
        }

        System.out.println("ID\t\tOwner Name\tEmail");
        System.out.println("-------------------------------------------------");
        for (GymOwner owner : pendingOwners) {
            // Assuming GymOwner bean has getId(), getName(), and getEmail() methods
            System.out.println(owner.getUserID() + "\t" + owner.getName() + "\t\t" + owner.getEmail());
        }
        System.out.println("-------------------------------------------------");


        System.out.print("Enter the ID of the gym owner to process (or type 'exit' to return): ");
        String ownerId = scanner.nextLine();

        if (ownerId.equalsIgnoreCase("exit")) {
            return;
        }

        System.out.println("1. Approve");
        System.out.println("2. Reject");
        System.out.print("Choose an action: ");
        int action = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch(action) {
            case 1:
                flipFitAdminService.approveGymOwner(ownerId);
                System.out.println("Gym Owner with ID " + ownerId + " approved successfully.");
                break;
            case 2:
                flipFitAdminService.rejectGymOwner(ownerId);
                System.out.println("Gym Owner with ID " + ownerId + " rejected successfully.");
                break;
            default:
                System.out.println("Invalid action. Returning to menu.");
        }
    }

    /**
     * Displays all existing gyms and prompts the admin to remove one.
     * @param scanner Scanner for user input
     */
    private void removeExistingGym(Scanner scanner) {
        System.out.println("\n--- Remove an Existing Gym ---");
        List<Gym> allGyms = flipFitAdminService.getAllGyms();

        if (allGyms.isEmpty()) {
            System.out.println("There are no gyms in the system to remove.");
            return;
        }

        System.out.println("ID\t\tGym Name\tOwner Email");
        System.out.println("-------------------------------------------------");
        for (Gym gym : allGyms) {
            System.out.println(gym.getGymID() + "\t" + gym.getName() + "\t\t" + gym.getGymOwnerID());
        }
        System.out.println("-------------------------------------------------");

        System.out.print("Enter the ID of the gym to remove (or type 'exit' to return): ");
        String gymId = scanner.nextLine();

        if (gymId.equalsIgnoreCase("exit")) {
            return;
        }

        System.out.print("Are you sure you want to remove gym with ID " + gymId + "? (Y/N): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {
            flipFitAdminService.removeGym(gymId);
            System.out.println("Gym removed successfully.");
        } else {
            System.out.println("Gym removal cancelled.");
        }
    }

    /**
     * Displays all existing gym owners and prompts the admin to remove one.
     * @param scanner Scanner for user input
     */
    private void removeExistingGymOwner(Scanner scanner) {
        System.out.println("\n--- Remove an Existing Gym Owner ---");
        List<GymOwner> allOwners = flipFitAdminService.getAllGymOwners();

        if (allOwners.isEmpty()) {
            System.out.println("There are no gym owners in the system to remove.");
            return;
        }

        System.out.println("ID\t\tOwner Name\tEmail");
        System.out.println("-------------------------------------------------");
        for (GymOwner owner : allOwners) {
            System.out.println(owner.getUserID() + "\t" + owner.getName() + "\t\t" + owner.getEmail());
        }
        System.out.println("-------------------------------------------------");

        System.out.print("Enter the ID of the gym owner to remove (or type 'exit' to return): ");
        String ownerId = scanner.nextLine();

        if (ownerId.equalsIgnoreCase("exit")) {
            return;
        }

        System.out.print("Are you sure you want to remove gym owner with ID " + ownerId + "? (Y/N): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {
            flipFitAdminService.removeGymOwner(ownerId);
            System.out.println("Gym owner removed successfully.");
        } else {
            System.out.println("Gym owner removal cancelled.");
        }
    }
}