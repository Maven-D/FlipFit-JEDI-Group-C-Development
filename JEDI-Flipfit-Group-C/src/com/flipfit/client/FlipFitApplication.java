package com.flipfit.client;

import com.flipfit.bean.*;
import com.flipfit.business.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * The main entry point for the FlipFit application.
 * This class initializes the system with some dummy data and handles the
 * main user interaction loop for login and registration.
 */
public class FlipFitApplication {

//    // Business Logic Handlers

    private static FlipFitAuthenticationServiceInterface authBusiness = new FlipFitAuthenticationServiceImpl();
    private static FlipFitAdminServiceInterface flipFitAdminService = new FlipFitAdminServiceImpl();
//    private static GymOwnerBusiness gymOwnerBusiness = new GymOwnerBusiness();
//    private static CustomerBusiness customerBusiness = new CustomerBusiness();
    private static FlipFitGymServiceInterface flipFitGymServiceImpl = new FlipFitGymServiceImpl();
    private static FlipFitBookingServiceInterface flipFitBookingServiceImpl = new FlipFitBookingServiceImpl();


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

            int choice = -1; // Initialize with a default invalid value

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue; // Skip the rest of the loop and show the menu again
            } finally {
                scanner.nextLine(); // Consume the newline character after a valid number is entered
            }

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
                authBusiness.registerCustomer(customer);



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
                authBusiness.registerGymOwner(owner);

            }
            else {
                System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}
