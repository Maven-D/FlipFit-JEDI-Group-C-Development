package com.flipfit.client;

import com.flipfit.bean.*;
import com.flipfit.business.*;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FlipFitCustomerMenu {


    private FlipFitGymServiceInterface flipFitGymService = new FlipFitGymServiceImpl();
    private FlipFitBookingServiceInterface flipFitBookingService = new FlipFitBookingServiceImpl();
    private FlipFitCustomerServiceInterface flipFitCustomerService = new FlipFitCustomerServiceImpl();

    private FlipFitGymServiceImpl flipFitGymServiceImpl = new FlipFitGymServiceImpl();
    private FlipFitBookingServiceImpl flipFitBookingServiceImpl = new FlipFitBookingServiceImpl();
    private FlipFitCustomerServiceImpl flipFitCustomerServiceImpl = new FlipFitCustomerServiceImpl();



    public void showCustomerMenu(Scanner scanner, Customer customer) {

        flipFitCustomerServiceImpl.setCustomer(customer);
        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("Logged in as: " + customer.getName());
            System.out.println("1. View Available Gym Slots");
            System.out.println("2. Book a Slot");
            System.out.println("3. View My Bookings");
            System.out.println("4. Cancel a booking");
            System.out.println("5. Logout");
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

            switch (choice) {
                case 1:
                    viewAvailableSlots(scanner);
                    break;
                case 2:
                    bookSlot(scanner, customer);
                    break;
                case 3:
                    viewMyBookings(customer);
                    break;
                case 4:
                	cancelBooking(scanner,customer);
                	break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private boolean viewAvailableSlots(Scanner scanner) {
        System.out.println("\n-- View Available Gyms --");
        List<Gym> allGyms = flipFitGymService.getAllGyms();

        if (allGyms == null) {
            System.out.println("Sorry, there are no gyms available at the moment.");
            return false;
        }

        List<Gym> availableGyms = allGyms.stream()
                .filter(gym -> "CONFIRMED".equalsIgnoreCase(gym.getApprovalStatus()))
                .collect(Collectors.toList());

        if (availableGyms.isEmpty()) {
            System.out.println("Sorry, there are no approved gyms available at the moment.");
            return false;
        }

        System.out.println("\n--- List of Available Gyms ---");
        System.out.printf("%-10s | %-25s | %-30s%n", "Gym ID", "Gym Name", "Address");
        System.out.println("-----------------------------------------------------------------");
        for (Gym gym : availableGyms) {
            System.out.printf("%-10s | %-25s | %-30s%n", gym.getGymID(), gym.getName(), gym.getAddress());
        }
        System.out.println("-----------------------------------------------------------------");

        System.out.print("\nEnter the Gym ID to view its available slots: ");
        String gymId = scanner.nextLine();

        boolean isValidGymId = availableGyms.stream().anyMatch(gym -> gym.getGymID().equals(gymId));
        if (!isValidGymId) {
            System.out.println("Error: You have entered an invalid Gym ID from the list above.");
            return false;
        }

        try {
            System.out.print("Enter Date (yyyy-MM-dd): ");
            LocalDate date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);

            List<TimeSlot> slots = flipFitGymService.getAvailability(gymId, date);
            if (slots.isEmpty()) {
                System.out.println("\nNo available slots found for Gym " + gymId + " on " + date);
                return false; // Return false as no slots were shown
            }

            System.out.println("\n--- Available Slots for Gym " + gymId + " on " + date + " ---");
            System.out.printf("%-38s | %-12s | %-12s | %-10s%n", "Slot ID", "Start Time", "End Time", "Seats");
            System.out.println("---------------------------------------------------------------------------------");
            for (TimeSlot slot : slots) {
                System.out.printf("%-38s | %-12s | %-12s | %-10d%n",
                        slot.getSlotID(),
                        slot.getStartTime(),
                        slot.getEndTime(),
                        slot.getAvailableSeats());
            }
            System.out.println("---------------------------------------------------------------------------------");
            return true; // IMPORTANT: Return true because slots were successfully displayed.

        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Please use yyyy-MM-dd.");
            return false;
        }
    }

    private void bookSlot(Scanner scanner,Customer customer) {
        System.out.println("\n-- Book a Slot --");
        System.out.println("First, find an available slot from the list below.");
        System.out.println("-----------------------------------------------");

        // Step 1: Call the viewing method to show the user the available slots.
        boolean slotsWereDisplayed = viewAvailableSlots(scanner);

        // Step 2: Only proceed to ask for a Slot ID if slots were actually shown.
        if (slotsWereDisplayed) {
            System.out.print("\nEnter the Slot ID from the list above to book: ");
            String slotId = scanner.nextLine();

            Booking booking = flipFitBookingService.makeBooking(customer,slotId);
            if (booking != null) {
                System.out.println("Booking successful! Your Booking ID is: " + booking.getBookingID());
            } else {
                System.out.println("Booking failed. The slot may be full or the ID is incorrect.");
            }
        }

    }

    private boolean viewMyBookings(Customer customer) {
        System.out.println("\n-- My Bookings --");
        List<Booking> bookings = flipFitBookingService.getBookingsForCustomerId(customer.getUserID());

        if (bookings.isEmpty()) {
            System.out.println("You have no active bookings.");
            return false; // Return false because there's nothing to cancel
        }

        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%-38s | %-10s | %-38s | %-15s%n", "Booking ID", "Gym ID", "Slot ID", "Status");
        System.out.println("-------------------------------------------------------------------------------------------------");
        for (Booking booking : bookings) {
            // Only show bookings that can be cancelled
            if("CONFIRMED".equalsIgnoreCase(booking.getStatus())) {
                System.out.printf("%-38s | %-10s | %-38s | %-15s%n",
                        booking.getBookingID(),
                        booking.getGymID(),
                        booking.getSlotID(),
                        booking.getStatus());
            }
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
        return true; // Return true as bookings were displayed
    }



    private void cancelBooking(Scanner scanner, Customer customer) {
        System.out.println("\n-- Cancel a Booking --");
        System.out.println("Here is a list of your current confirmed bookings:");

        // Step 1: Display the user's bookings and check if there are any to cancel.
        boolean hasBookings = viewMyBookings(customer);

        // Step 2: Only proceed if the user actually has bookings.
        if (hasBookings) {
            System.out.print("\nEnter the Booking ID from the list above to cancel: ");
            String bookingId = scanner.nextLine();

            // The service call needs the customer object for the ownership check
            boolean isCancelled = flipFitBookingService.cancelBooking(customer, bookingId);

            // Step 3: Provide clear feedback based on the result.
            if (isCancelled) {
                System.out.println("Booking with ID " + bookingId + " has been successfully cancelled.");
            } else {
                System.out.println("Failed to cancel booking. Please check that the Booking ID is correct and from the list above.");
            }
        }
        // If hasBookings is false, the viewMyBookings() method already printed the "no bookings" message.
    }
}
