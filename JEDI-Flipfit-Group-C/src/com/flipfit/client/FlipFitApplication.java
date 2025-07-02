package com.flipfit.client;

import com.flipfit.bean.*;
import com.flipfit.business.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.UUID;

/**
 * The main entry point for the FlipFit application.
 * This class initializes the system with some dummy data and handles the
 * main user interaction loop for login and registration.
 */
public class FlipFitApplication {

//    // Business Logic Handlers
    private static AuthenticationBusiness authBusiness = new AuthenticationBusiness();
    private static AdminBusiness adminBusiness = new AdminBusiness();
//    private static GymOwnerBusiness gymOwnerBusiness = new GymOwnerBusiness();
//    private static CustomerBusiness customerBusiness = new CustomerBusiness();
    private static GymBusiness gymBusiness = new GymBusiness();
    private static BookingBusiness bookingBusiness = new BookingBusiness();

    // Client Views
    private static AdminClient adminClient = new AdminClient();
    private static GymOwnerClient gymOwnerClient = new GymOwnerClient();
    private static CustomerClient customerClient = new CustomerClient();

    public static void main(String[] args) {
        System.out.println("Welcome to FlipFit Application!");

        // Pre-load some data for demonstration purposes
        setupInitialData();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                BaseUser user = authBusiness.verifyCredentials(email, password);

                if (user != null) {
                    System.out.println("Login Successful! Welcome, " + user.getName());
                    // Redirect to the appropriate client view based on user type
                    if (user instanceof SystemAdmin) {
                        adminClient.showAdminMenu(scanner, (SystemAdmin) user);
                    } else if (user instanceof GymOwner) {
                        gymOwnerClient.showGymOwnerMenu(scanner, (GymOwner) user);
                    } else if (user instanceof Customer) {
                        customerClient.showCustomerMenu(scanner, (Customer) user);
                    }
                } else {
                    System.out.println("Login Failed. Invalid email or password.");
                }
            } else if (choice == 2) {
                System.out.println("Thank you for using FlipFit. Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    /**
     * Sets up initial dummy data for users, gyms, and time slots.
     */
    private static void setupInitialData() {
        // Create users
        SystemAdmin admin = new SystemAdmin();
        admin.setUserID("admin01");
        admin.setName("Main Admin");
        admin.setEmail("admin@flipfit.com");
        admin.setPasswordHash("admin123"); // In real app, this would be a hash
        authBusiness.registerUser(admin);

        GymOwner owner = new GymOwner();
        owner.setUserID("owner01");
        owner.setName("John's Gyms");
        owner.setEmail("owner@flipfit.com");
        owner.setPasswordHash("owner123");
        authBusiness.registerUser(owner);

        Customer customer = new Customer();
        customer.setUserID("cust01");
        customer.setName("Alice");
        customer.setEmail("customer@flipfit.com");
        customer.setPasswordHash("cust123");
        authBusiness.registerUser(customer);

        // Create a gym managed by the owner
        Gym gym = new Gym();
        gym.setGymID("gym01");
        gym.setName("Flex Fitness");
        gym.setAddress("123 Muscle St, Workout City");
        adminBusiness.addGym(gym); // Admin approves/adds the gym

        // Create time slots for the gym
        TimeSlot slot1 = new TimeSlot();
        slot1.setSlotID("slot01");
        slot1.setGymID("gym01");
        slot1.setDate(LocalDate.now());
        slot1.setStartTime(LocalTime.of(9, 0));
        slot1.setEndTime(LocalTime.of(10, 0));
        slot1.setAvailableSeats(10);
        gymBusiness.addTimeSlot(slot1);
        bookingBusiness.addTimeSlot(slot1); // Also add to booking business's list

        TimeSlot slot2 = new TimeSlot();
        slot2.setSlotID("slot02");
        slot2.setGymID("gym01");
        slot2.setDate(LocalDate.now());
        slot2.setStartTime(LocalTime.of(10, 0));
        slot2.setEndTime(LocalTime.of(11, 0));
        slot2.setAvailableSeats(5);
        gymBusiness.addTimeSlot(slot2);
        bookingBusiness.addTimeSlot(slot2);

        // Create time slots for the gym
        TimeSlot slot3 = new TimeSlot();
        slot3.setSlotID("slot03");
        slot3.setGymID("gym02");
        slot3.setDate(LocalDate.now());
        slot3.setStartTime(LocalTime.of(9, 0));
        slot3.setEndTime(LocalTime.of(10, 0));
        slot3.setAvailableSeats(10);
        gymBusiness.addTimeSlot(slot3);
        bookingBusiness.addTimeSlot(slot3); // Also add to booking business's list

        System.out.println("\nInitial data setup complete.\n");
    }
}
