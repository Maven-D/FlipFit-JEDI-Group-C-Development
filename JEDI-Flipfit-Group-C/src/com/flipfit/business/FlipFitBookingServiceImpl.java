package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Customer;
import com.flipfit.bean.TimeSlot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service class for handling the creation and management of bookings.
 */
public class FlipFitBookingServiceImpl implements FlipFitBookingServiceInterface {

//    private static List<Booking> allBookings = new ArrayList<>();
//    private static List<TimeSlot> allTimeSlots = new ArrayList<>();
<<<<<<< HEAD
    List<TimeSlot> allTimeSlots = FlipFitGymServiceImpl.getAllTimeSlots();
=======
    List<TimeSlot> allTimeSlots = FlipFitGymServiceInterface.getAllTimeSlots();
>>>>>>> 4678108a044592849fba849c12eb21ae5b8698e0

    /**
     * Creates a new booking for a customer for a specific time slot.
     * It checks for availability and decrements the seat count.
     *
     * @param customer The customer making the booking.
     * @param slot The time slot being booked.
     * @return The created Booking object, or null if booking failed.
     */
    @Override
<<<<<<< HEAD
	public Booking makeBooking(Customer customer, TimeSlot slot) {
=======
    public Booking makeBooking(Customer customer, TimeSlot slot) {
>>>>>>> 4678108a044592849fba849c12eb21ae5b8698e0
        if (customer == null || slot == null) {
            System.out.println("Customer or Slot cannot be null.");
            return null;
        }

        // Get the master list of time slots from the Gym Service

        // Find the actual timeslot in our "database" to ensure it's the correct instance
        TimeSlot targetSlot = null;
        for (TimeSlot s : allTimeSlots) {
            if (s.getSlotID().equals(slot.getSlotID())) {
                targetSlot = s;
                break;
            }
        }

//        if (targetSlot != null && targetSlot.getAvailableSeats() > 0) {
//            // Decrement seat count
//            targetSlot.setAvailableSeats(targetSlot.getAvailableSeats() - 1);
//
//            // Create a new booking
//            Booking newBooking = new Booking();
//            newBooking.setBookingID(UUID.randomUUID().toString()); // Generate a unique ID
//            newBooking.setBookingTime(LocalDateTime.now());
//            newBooking.setStatus("Confirmed");
//            newBooking.setSlot(targetSlot);
//            // To make this fully functional, Booking bean would need customerId, gymId, and slotId fields.
//
//            Bobocustomer.getBookingList().add(newBooking);
////            for(Booking i: allBookings) {
////            	System.out.println(i.getBookingID());
////            }
//            System.out.println("Booking successful for " + customer.getName() + " at gym " + targetSlot.getGymID());
//            return newBooking;
//        } else {
//            System.out.println("Booking failed. Slot " + slot.getSlotID() + " is either full or does not exist.");
//            return null;
//        }
        if (targetSlot != null && targetSlot.getAvailableSeats() > 0) {
            // --- CLASH DETECTION LOGIC ---
            // Iterate through the customer's existing bookings to find and cancel clashes.
            for (Booking existingBooking : customer.getBookingList()) {
                if (existingBooking.getStatus().equals("Confirmed")) {
                    TimeSlot existingSlot = existingBooking.getSlot();
                    // Check for clash only if on the same date
                    if (existingSlot.getDate().isEqual(targetSlot.getDate())) {
                        // A clash occurs if (StartA < EndB) and (EndA > StartB)
                        boolean isOverlapping = targetSlot.getStartTime().isBefore(existingSlot.getEndTime()) &&
                                targetSlot.getEndTime().isAfter(existingSlot.getStartTime());

                        if (isOverlapping) {
                            existingBooking.setStatus("Cancelled");
                            System.out.println("Clash detected! Your previous booking " + existingBooking.getBookingID() + " has been cancelled.");
                            // Increment the seat count for the cancelled slot
                            existingSlot.setAvailableSeats(existingSlot.getAvailableSeats() + 1);
                        }
                    }
                }
            }

            // --- PROCEED WITH NEW BOOKING ---
            // Decrement seat count for the new slot
            targetSlot.setAvailableSeats(targetSlot.getAvailableSeats() - 1);

            // Create a new booking
            Booking newBooking = new Booking();
            newBooking.setBookingID(UUID.randomUUID().toString()); // Generate a unique ID
            newBooking.setBookingTime(LocalDateTime.now());
            newBooking.setStatus("Confirmed");
            newBooking.setSlot(targetSlot);

            // Add the new booking to the customer's personal list
            customer.getBookingList().add(newBooking);

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
    @Override
<<<<<<< HEAD
	public void addTimeSlot(TimeSlot slot) {
=======
    public void addTimeSlot(TimeSlot slot) {
>>>>>>> 4678108a044592849fba849c12eb21ae5b8698e0
        allTimeSlots.add(slot);
    }
}
