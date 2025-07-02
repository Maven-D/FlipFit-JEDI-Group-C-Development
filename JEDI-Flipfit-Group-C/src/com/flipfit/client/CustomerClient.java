package com.flipfit.client;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Customer;
import com.flipfit.bean.TimeSlot;
import com.flipfit.business.FlipFitBookingServiceImpl;
import com.flipfit.business.FlipFitCustomerServiceImpl;
import com.flipfit.business.FlipFitGymServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class CustomerClient {

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

            int choice = scanner.nextInt();
            scanner.nextLine();

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
                	cancelBooking(scanner);
                	break;
                case 5:
                	scanner.close();
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewAvailableSlots(Scanner scanner) {
        System.out.println("\n-- View Available Slots --");
        System.out.print("Enter Gym ID: ");
        String gymId = scanner.nextLine();
        System.out.print("Enter Date (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);
        scanner.close();

        List<TimeSlot> slots = flipFitGymServiceImpl.getAvailability(gymId, date);
        if (slots.isEmpty()) {
            System.out.println("No available slots for this gym on the selected date.");
        } else {
            System.out.println("Available Slots:");
            System.out.printf("%-10s %-12s %-12s %-10s%n", "Slot ID", "Start Time", "End Time", "Seats");
            for (TimeSlot slot : slots) {
                System.out.printf("%-10s %-12s %-12s %-10d%n",
                        slot.getSlotID(),
                        slot.getStartTime(),
                        slot.getEndTime(),
                        slot.getAvailableSeats());
            }
        }
    }

    private void bookSlot(Scanner scanner, Customer customer) {
        System.out.println("\n-- Book a Slot --");
        System.out.print("Enter Slot ID to book: ");
        String slotId = scanner.nextLine();
        
//        scanner.close();

        TimeSlot slotToBook = new TimeSlot();
        slotToBook.setSlotID(slotId);

        Booking booking = flipFitBookingServiceImpl.makeBooking(customer, slotToBook);
        if (booking != null) {
            System.out.println("Booking successful! Your Booking ID is: " + booking.getBookingID());
        } else {
            System.out.println("Booking failed. Please check the Slot ID and availability.");
        }
    }

    private void viewMyBookings(Customer customer) {
        System.out.println("\n-- My Bookings --");
        List<Booking> bookings = flipFitCustomerServiceImpl.viewBookings();
        if (bookings.isEmpty()) {
            System.out.println("You have no bookings.");
        } else {
            System.out.printf("%-38s %-25s %-15s%n", "Booking ID", "Booking Time", "Status");
            for (Booking booking : bookings) {
                System.out.printf("%-38s %-25s %-15s%n",
                        booking.getBookingID(),
                        booking.getBookingTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        booking.getStatus());
            }
        }
    }
    
    
    private void cancelBooking(Scanner scanner) {
    	 System.out.print("Enter Booking ID: ");
    	 String bookingId = scanner.nextLine();
    	 System.out.println("Cancelling Booking with ID "+ bookingId);
    	 flipFitCustomerServiceImpl.cancelBooking(bookingId);
    }
}
