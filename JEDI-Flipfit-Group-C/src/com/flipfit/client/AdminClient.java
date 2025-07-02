package com.flipfit.client;

import com.flipfit.bean.Customer;
import com.flipfit.bean.Gym;
import com.flipfit.bean.SystemAdmin;
import com.flipfit.business.FlipFitAdminService;

import java.util.Scanner;
import java.util.UUID;

/**
 * Client view for the System Administrator.
 * Provides a menu-driven interface for admin-specific actions.
 */
public class AdminClient {

    private FlipFitAdminService flipFitAdminService = new FlipFitAdminService();

    /**
     * Displays the main menu for the System Administrator and handles input.
     * @param scanner The Scanner object for user input.
     * @param admin The currently logged-in SystemAdmin.
     */
    public void showAdminMenu(Scanner scanner, SystemAdmin admin) {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("Logged in as: " + admin.getName());
            System.out.println("1. Add a new Gym");
            System.out.println("2. Add a new Customer");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addGym(scanner);
                    break;
                case 2:
                    addCustomer(scanner);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return; // Exit the admin menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addGym(Scanner scanner) {
        System.out.println("\n-- Add New Gym --");
        System.out.print("Enter Gym Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Gym Address: ");
        String address = scanner.nextLine();

        Gym newGym = new Gym();
        newGym.setGymID(UUID.randomUUID().toString());
        newGym.setName(name);
        newGym.setAddress(address);

        flipFitAdminService.addGym(newGym);
        System.out.println("Gym '" + name + "' added successfully!");
    }

    private void addCustomer(Scanner scanner) {
        System.out.println("\n-- Add New Customer --");
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Customer Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Customer Password: ");
        String password = scanner.nextLine();

        Customer newCustomer = new Customer();
        newCustomer.setUserID(UUID.randomUUID().toString());
        newCustomer.setName(name);
        newCustomer.setEmail(email);
        newCustomer.setPasswordHash(password); // In real app, hash this

        flipFitAdminService.addCustomer(newCustomer);
        System.out.println("Customer '" + name + "' added successfully!");
    }
}
