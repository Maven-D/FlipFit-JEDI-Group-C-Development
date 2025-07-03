package com.flipfit.client;

import com.flipfit.bean.*;
import com.flipfit.business.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * The main entry point for the FlipFit application.
 * This class initializes the system with some dummy data and handles the
 * main user interaction loop for login and registration.
 */
public class FlipFitApplication {

//    // Business Logic Handlers
    private static FlipFitAuthenticationServiceImpl authBusiness = new FlipFitAuthenticationServiceImpl();
    private static FlipFitAdminServiceImpl flipFitAdminServiceImpl = new FlipFitAdminServiceImpl();
//    private static GymOwnerBusiness gymOwnerBusiness = new GymOwnerBusiness();
//    private static CustomerBusiness customerBusiness = new CustomerBusiness();
    private static FlipFitGymServiceImpl flipFitGymServiceImpl = new FlipFitGymServiceImpl();
    private static FlipFitBookingServiceImpl flipFitBookingServiceImpl = new FlipFitBookingServiceImpl();

    // Client Views
    private static FlipFitAdminMenu flipFitAdminMenu = new FlipFitAdminMenu();
    private static FlipFitGymOwnerMenu flipFitGymOwnerMenu = new FlipFitGymOwnerMenu();
    private static FlipFitCustomerMenu flipFitCustomerMenu = new FlipFitCustomerMenu();

    public static void main(String[] args) {
        System.out.println("Welcome to FlipFit Application!");

        // Pre-load some data for demonstration purposes
//        setupInitialData();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Login");
            System.out.println("2. Registration of Gym Customer");
            System.out.println("3. Registration of Gym Owner");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if(choice == 4) {
                System.out.println("Thank you for using FlipFit. Goodbye!");
                break;
            }

            if (choice == 1) {


                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                BaseUser user = authBusiness.verifyCredentials(email, password);

                if (user != null) {
                    System.out.println("Login Successful! Welcome, " + user.getName());
                    // Redirect to the appropriate client view based on user type
                    if (user instanceof SystemAdmin systemAdmin) {
                        flipFitAdminMenu.showAdminMenu(scanner, systemAdmin);
                    } else if (user instanceof GymOwner gymOwner) {
                        flipFitGymOwnerMenu.showGymOwnerMenu(scanner, gymOwner);
                    } else if (user instanceof Customer customer) {
                        flipFitCustomerMenu.showCustomerMenu(scanner, customer);
                    }
                } else {
                    System.out.println("Login Failed. Invalid email or password.");
                }
            }
            else if(choice == 2) {
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                Customer customer = new Customer();
                customer.setUserID(name+email);
                customer.setName(name);
                customer.setEmail(email);
                customer.setPasswordHash(password);
                customer.setRole(new UserRole(3, "CUSTOMER", "End User"));
                authBusiness.registerUser(customer);



            }
            else if(choice == 3) {
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                GymOwner owner = new GymOwner();
                owner.setUserID(name+email);
                owner.setName(name);
                owner.setEmail(email);
                owner.setPasswordHash(password);
                owner.setRole(new UserRole(2, "GYM_OWNER", "Gym Owner"));
                authBusiness.registerUser(owner);

            }
            else {
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
        admin.setRole(new UserRole(1, "ADMIN", "System Administrator"));
        authBusiness.registerUser(admin);

        GymOwner owner = new GymOwner();
        owner.setUserID("owner01");
        owner.setName("John's Gyms");
        owner.setEmail("owner@flipfit.com");
        owner.setPasswordHash("owner123");
        owner.setRole(new UserRole(2, "GYM_OWNER", "Gym Owner"));
        authBusiness.registerUser(owner);

        Customer customer = new Customer();
        customer.setUserID("cust01");
        customer.setName("Alice");
        customer.setEmail("customer@flipfit.com");
        customer.setPasswordHash("cust123");
        customer.setRole(new UserRole(3, "CUSTOMER", "End User"));
        authBusiness.registerUser(customer);

        // Create a gym managed by the owner
        Gym gym = new Gym();
        gym.setGymID("gym01");
        gym.setName("Flex Fitness");
        gym.setAddress("123 Muscle St, Workout City");
        flipFitAdminServiceImpl.addGym(gym); // Admin approves/adds the gym

        // Create time slots for the gym
        TimeSlot slot1 = new TimeSlot();
        slot1.setSlotID("slot01");
        slot1.setGymID("gym01");
        slot1.setDate(LocalDate.now());
        slot1.setStartTime(LocalTime.of(9, 0));
        slot1.setEndTime(LocalTime.of(10, 0));
        slot1.setAvailableSeats(10);
        flipFitGymServiceImpl.addTimeSlot(slot1);
//        flipFitBookingServiceImpl.addTimeSlot(slot1); // Also add to booking business's list

        TimeSlot slot2 = new TimeSlot();
        slot2.setSlotID("slot02");
        slot2.setGymID("gym01");
        slot2.setDate(LocalDate.now());
        slot2.setStartTime(LocalTime.of(10, 0));
        slot2.setEndTime(LocalTime.of(11, 0));
        slot2.setAvailableSeats(5);
        flipFitGymServiceImpl.addTimeSlot(slot2);
//        flipFitBookingServiceImpl.addTimeSlot(slot2);

        // Create time slots for the gym
        TimeSlot slot3 = new TimeSlot();
        slot3.setSlotID("slot03");
        slot3.setGymID("gym02");
        slot3.setDate(LocalDate.now());
        slot3.setStartTime(LocalTime.of(9, 30));
        slot3.setEndTime(LocalTime.of(10, 30));
        slot3.setAvailableSeats(10);
        flipFitGymServiceImpl.addTimeSlot(slot3);
//        flipFitBookingServiceImpl.addTimeSlot(slot3); // Also add to booking business's list


        System.out.println("\nInitial data setup complete.\n");
    }
}
