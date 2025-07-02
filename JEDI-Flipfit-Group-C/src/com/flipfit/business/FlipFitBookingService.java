package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Customer;
import com.flipfit.bean.TimeSlot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service class for handling the creation and management of bookings.
 */
public class FlipFitBookingService {

    private static List<Booking> allBookings = new ArrayList<>();
    private static List<TimeSlot> allTimeSlots = new ArrayList<>();

    /**
     * Creates a new booking for a customer for a specific time slot.
     * It checks for availability and decrements the seat count.
     *
     * @param customer The customer making the booking.
     * @param slot The time slot being booked.
     * @return The created Booking object, or null if booking failed.
     */
    public Booking makeBooking(Customer customer, TimeSlot slot) {
        if (customer == null || slot == null) {
            System.out.println("Customer or Slot cannot be null.");
            return null;
        }

        // Find the actual timeslot in our "database" to ensure it's the correct instance
        TimeSlot targetSlot = null;
        for (TimeSlot s : allTimeSlots) {
            if (s.getSlotID().equals(slot.getSlotID())) {
                targetSlot = s;
                break;
            }
        }

        if (targetSlot != null && targetSlot.getAvailableSeats() > 0) {
            // Decrement seat count
            targetSlot.setAvailableSeats(targetSlot.getAvailableSeats() - 1);

            // Create a new booking
            Booking newBooking = new Booking();
            newBooking.setBookingID(UUID.randomUUID().toString()); // Generate a unique ID
            newBooking.setBookingTime(LocalDateTime.now());
            newBooking.setStatus("Confirmed");
            // To make this fully functional, Booking bean would need customerId, gymId, and slotId fields.

            customer.getBookingList().add(newBooking);
            for(Booking i: allBookings) {
            	System.out.println(i.getBookingID());
            }
            System.out.println("Booking successful for " + customer.getName() + " at gym " + targetSlot.getGymID());
            return newBooking;
        } else {
            System.out.println("Booking failed. Slot " + slot.getSlotID() + " is either full or does not exist.");
            return null;
        }
    }

    /**
     * Helper method to add a timeslot to the system.
     * @param slot The TimeSlot to add.
     */
    public void addTimeSlot(TimeSlot slot) {
        allTimeSlots.add(slot);
    }
}
