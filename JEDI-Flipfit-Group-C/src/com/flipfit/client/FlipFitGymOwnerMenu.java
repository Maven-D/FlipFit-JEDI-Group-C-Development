package com.flipfit.client;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.TimeSlot;
import com.flipfit.business.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Client view for the Gym Owner.
 * Implements a direct workflow-oriented menu for all owner actions.
 */
public class FlipFitGymOwnerMenu {

    // Service layer interfaces to interact with business logic
    private final FlipFitGymOwnerServiceInterface gymOwnerService = new FlipFitGymOwnerServiceImpl();
    private final FlipFitGymServiceInterface gymService = new FlipFitGymServiceImpl();
    private final FlipFitBookingServiceInterface bookingService = new FlipFitBookingServiceImpl();

    /**
     * Displays the main menu for the Gym Owner and handles navigation based on the specified workflow.
     * @param scanner The Scanner object for user input.
     * @param owner The currently logged-in GymOwner.
     */
    public void showGymOwnerMenu(Scanner scanner, GymOwner owner) {
        Optional<GymOwner> ownerOptional = gymOwnerService.getGymOwner(owner.getUserID());

        if (ownerOptional.isPresent()) {
            owner = ownerOptional.get();
        }

        while (true) {
            System.out.println("\n--- Gym Owner Menu ---");
            System.out.println("Logged in as: " + owner.getName());
            System.out.println("1. Register a New Gym");
            System.out.println("2. Add a Time Slot");
            System.out.println("3. List My Gyms");
            System.out.println("4. View Bookings for a Gym");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            } finally {
                scanner.nextLine(); // Consume the newline character
            }

            switch (choice) {
                case 1:
                    registerNewGym(scanner, owner); // Calling the rewritten function
                    break;
                case 2:
                    addTimeSlot(scanner, owner);
                    break;
                case 3:
                    listMyGyms(owner);
                    break;
                case 4:
                    viewGymBookings(scanner, owner);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // --- REWRITTEN FUNCTION AS PER YOUR REQUEST ---
    /**
     * Guides the owner through registering a new gym, collecting all required details.
     * This function is updated to match the new Gym bean structure.
     * @param scanner Scanner for input.
     * @param owner The logged-in GymOwner.
     */
    private void registerNewGym(Scanner scanner, GymOwner owner) {

        if (!"CONFIRMED".equalsIgnoreCase(owner.getApprovalStatus())) {
            System.out.println("\nYour account is still " + owner.getApprovalStatus() + ".");
            System.out.println("You cannot register a new gym until your account has been approved by an admin.");
            return; // Exit the function immediately if not confirmed.
        }

        System.out.println("\n--- Register a New Gym ---");
        System.out.print("Enter Gym Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Gym Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter GST Number: ");
        String gstNumber = scanner.nextLine();

        Gym newGym = new Gym();
        // Set details from user input
        newGym.setName(name);
        newGym.setAddress(address);
        newGym.setGstNumber(gstNumber);

        // Set details programmatically
        newGym.setGymID(UUID.randomUUID().toString().substring(0, 8)); // Generate a short, readable ID
        newGym.setGymOwnerID(owner.getUserID()); // Link to the current owner
        newGym.setApprovalStatus("PENDING"); // Set the initial status

        try {
            gymOwnerService.registerNewGym(newGym);
            System.out.println("Gym '" + name + "' registered successfully! It is now pending admin approval.");
        }
        catch(Exception e){
            System.out.println("Error Registering Gym");
        }
    }


    /**
     * Workflow for viewing bookings: lists gyms, then asks for which one to view.
     * @param scanner Scanner for input.
     * @param owner The logged-in owner.
     */
    /**
     * A self-contained workflow to view gym bookings.
     * This method lists the gyms owned by the user, prompts for a selection,
     * validates ownership, and then directly fetches and displays the bookings.
     *
     * @param scanner The Scanner object for user input.
     * @param owner The currently logged-in GymOwner.
     */
    private void viewGymBookings(Scanner scanner, GymOwner owner) {
        System.out.println("\n--- View Gym Bookings ---");

        // Step 1: List all gyms belonging to the owner.
        List<Gym> myGyms = listMyGyms(owner);
        if (myGyms == null || myGyms.isEmpty()) {
            System.out.println("You have no gyms to view bookings for.");
            return;
        }

        // Step 2: Prompt for user input after the list is displayed.
        System.out.print("\nEnter the Gym ID to view bookings for: ");
        String gymId = scanner.nextLine();

        // Step 3: Validate that the entered Gym ID belongs to the owner.
        Optional<Gym> selectedGymOptional = myGyms.stream()
                .filter(gym -> gym.getGymID().equals(gymId))
                .findFirst();

        // Step 4: First, verify ownership.
        if (selectedGymOptional.isPresent()) {
            Gym selectedGym = selectedGymOptional.get();

            // Step 5: Now, check if the owned gym is actually confirmed.
            if ("CONFIRMED".equalsIgnoreCase(selectedGym.getApprovalStatus())) {
                // --- This part runs only if the gym is owned AND confirmed ---
                System.out.println("Fetching bookings for approved gym: " + selectedGym.getName() + "...");
                List<Booking> bookings = bookingService.getBookingsForGym(gymId);

                if (bookings == null || bookings.isEmpty()) {
                    System.out.println("No bookings found for this gym.");
                } else {
                    System.out.println("\n-- Bookings for Gym ID: " + gymId + " --");
                    // Note: It would be better to display the Slot Date/Time instead of SlotID for readability.
                    // This requires joining bookings with slots in the service/DAO layer.
                    System.out.printf("%-38s | %-15s | %-12s%n", "Booking ID", "Customer ID", "Slot ID");
                    System.out.println("-------------------------------------------------------------------------");
                    for (Booking booking : bookings) {
                        System.out.printf("%-38s | %-15s | %-12s%n",
                                booking.getBookingID(),
                                booking.getUserID(),
                                booking.getSlotID());
                    }
                    System.out.println("-------------------------------------------------------------------------");
                }
            } else {
                // Handle the case where the gym is owned but not yet approved.
                System.out.println("Error: Cannot view bookings. Gym '" + selectedGym.getName() +
                        "' has a status of: " + selectedGym.getApprovalStatus() + ".");
            }
        } else {
            // Handle the case where the Gym ID is not valid or doesn't belong to the owner.
            System.out.println("Error: You do not own a gym with ID '" + gymId + "'.");
        }
    }

    /**
     * Helper method to display a formatted list of all gyms owned by the current user.
     * @param owner The logged-in GymOwner.
     * @return A list of Gym objects owned by the user.
     */
    private List<Gym> listMyGyms(GymOwner owner) {
        System.out.println("\n--- My Registered Gyms ---");
        List<Gym> myGyms = gymOwnerService.getAllGymsOfOwner(owner.getUserID());
        if (myGyms == null || myGyms.isEmpty()) {
            System.out.println("You have not registered any gyms yet.");
        } else {
            System.out.printf("%-12s | %-20s | %-25s | %s%n", "Gym ID", "Gym Name", "Address", "Status");
            System.out.println("--------------------------------------------------------------------------------");
            for (Gym gym : myGyms) {
                System.out.printf("%-12s | %-20s | %-25s | %s%n", gym.getGymID(), gym.getName(), gym.getAddress(), gym.getApprovalStatus());
            }
            System.out.println("--------------------------------------------------------------------------------");
        }
        return myGyms;
    }


    private void addTimeSlot(Scanner scanner, GymOwner owner) {

        if (!"CONFIRMED".equalsIgnoreCase(owner.getApprovalStatus())) {
            System.out.println("\nYour account is still " + owner.getApprovalStatus() + ".");
            System.out.println("You cannot register a new gym until your account has been approved by an admin.");
            return; // Exit the function immediately if not confirmed.
        }

        System.out.println("\n--- Add a New Time Slot ---");

        // Step 1: List all gyms owned by the user.
        List<Gym> myGyms = listMyGyms(owner);
        if (myGyms == null || myGyms.isEmpty()) {
            System.out.println("You must register and get a gym approved before you can add a time slot.");
            return;
        }

        // Step 2: Get the Gym ID from the owner.
        System.out.print("\nEnter the Gym ID to add a slot to: ");
        String gymId = scanner.nextLine();

        // Step 3: Validate that the selected gym belongs to the owner.
        boolean ownsGym = myGyms.stream()
                .anyMatch(gym -> gym.getGymID().equals(gymId) && "CONFIRMED".equalsIgnoreCase(gym.getApprovalStatus()));

        if (ownsGym) {
            System.out.println("\n-- Adding Slot for Gym ID: " + gymId + " --");
            try {
                // Step 4: Get all slot details from the user.
                System.out.print("Enter Date (yyyy-MM-dd): ");
                LocalDate date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);

                System.out.print("Enter Start Time (HH:mm): ");
                LocalTime startTime = LocalTime.parse(scanner.nextLine());

                LocalTime endTime = startTime.plusHours(1);

                System.out.print("Enter Number of Available Seats: ");
                int seats = scanner.nextInt();
                scanner.nextLine(); // Consume the rest of the line after reading an int.

                // Step 5: Create and save the new TimeSlot object.
                TimeSlot newSlot = new TimeSlot();
                newSlot.setSlotID(UUID.randomUUID().toString()); // Generate a unique ID
                newSlot.setGymID(gymId);
                newSlot.setDate(date);
                newSlot.setStartTime(startTime);
                newSlot.setEndTime(endTime);
                newSlot.setAvailableSeats(seats);

                gymService.addTimeSlot(newSlot); // Assumes gymService is available
                System.out.println("Time slot added successfully!");

            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date/time format. Please use yyyy-MM-dd and HH:mm.");
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input for seats. Please enter a whole number.");
                scanner.nextLine(); // Important: Clear the scanner's invalid input.
            }
            // --- End of merged logic ---
        } else {
            // This is the error case from the original function.
            System.out.println("Error: You do not own an approved gym with ID '" + gymId + "'.");
        }
    }
}