package com.flipfit.client;

import com.flipfit.bean.GymOwner;
import com.flipfit.bean.TimeSlot;
import com.flipfit.business.FlipFitBookingServiceImpl;
import com.flipfit.business.FlipFitGymServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.UUID;

/**
 * Client view for the Gym Owner.
 * Provides a menu-driven interface for gym owner-specific actions.
 */
public class GymOwnerClient {

    private FlipFitGymServiceImpl flipFitGymServiceImpl = new FlipFitGymServiceImpl();
    private FlipFitBookingServiceImpl flipFitBookingServiceImpl = new FlipFitBookingServiceImpl(); // To add slots to booking business

    /**
     * Displays the main menu for the Gym Owner and handles input.
     * @param scanner The Scanner object for user input.
     * @param owner The currently logged-in GymOwner.
     */
    public void showGymOwnerMenu(Scanner scanner, GymOwner owner) {
        while (true) {
            System.out.println("\n--- Gym Owner Menu ---");
            System.out.println("Logged in as: " + owner.getName());
            System.out.println("1. Add a new Time Slot");
            System.out.println("2. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addTimeSlot(scanner);
                    break;
                case 2:
                    System.out.println("Logging out...");
                    return; // Exit the gym owner menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addTimeSlot(Scanner scanner) {
        System.out.println("\n-- Add New Time Slot --");
        System.out.print("Enter Gym ID for the slot: ");
        String gymId = scanner.nextLine();
        System.out.print("Enter Date (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.print("Enter Start Time (HH:mm): ");
        LocalTime startTime = LocalTime.parse(scanner.nextLine());
        System.out.print("Enter End Time (HH:mm): ");
        LocalTime endTime = LocalTime.parse(scanner.nextLine());
        System.out.print("Enter Number of Available Seats: ");
        int seats = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        TimeSlot newSlot = new TimeSlot();
        newSlot.setSlotID(UUID.randomUUID().toString());
        newSlot.setGymID(gymId);
        newSlot.setDate(date);
        newSlot.setStartTime(startTime);
        newSlot.setEndTime(endTime);
        newSlot.setAvailableSeats(seats);

        flipFitGymServiceImpl.addTimeSlot(newSlot);
        flipFitBookingServiceImpl.addTimeSlot(newSlot); // Ensure booking business is aware of the new slot
        System.out.println("Time slot added successfully!");
    }
}
