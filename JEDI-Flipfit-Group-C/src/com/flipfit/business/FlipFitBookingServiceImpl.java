package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Customer;
import com.flipfit.bean.PaymentDetails;
import com.flipfit.bean.TimeSlot;
import com.flipfit.dao.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FlipFitBookingServiceImpl implements FlipFitBookingServiceInterface {

    private final BookingDAO bookingDAO = new BookingDAOImpl();
    private final TimeSlotDAO timeSlotDAO = new TimeSlotDAOImpl();
    private final PaymentDAO paymentDAO = new PaymentDAOImpl();


    private Customer currentCustomer;

    @Override
    public void setCurrentCustomer(Customer customer) {
        this.currentCustomer = customer;
    }


    // The 'currentCustomer' field and 'setCurrentCustomer' method are removed.

    @Override
    public Booking makeBooking(Customer customer, String slotId) {
        if (customer == null) {
            System.out.println("SERVICE ERROR: Customer cannot be null. Cannot make a booking.");
            return null;
        }

        Optional<TimeSlot> targetSlotOptional = timeSlotDAO.findBySlotId(slotId);
        if (targetSlotOptional.isEmpty()) {
            System.out.println("Booking failed. Slot with ID " + slotId + " does not exist.");
            return null;
        }
        TimeSlot targetSlot = targetSlotOptional.get();

        if (targetSlot.getAvailableSeats() <= 0) {
            System.out.println("Booking failed. Slot " + slotId + " is full.");
            return null;
        }

        handleBookingClash(customer, targetSlot);

        targetSlotOptional = timeSlotDAO.findBySlotId(slotId);
        if (targetSlotOptional.isEmpty() || targetSlotOptional.get().getAvailableSeats() <= 0) {
            System.out.println("Booking failed. Slot became full during processing.");
            return null;
        }
        targetSlot = targetSlotOptional.get();

        PaymentDetails payment = new PaymentDetails();
        String paymentId = "PAY-" + UUID.randomUUID().toString().substring(0, 12);
        payment.setPaymentID(paymentId);
        payment.setAmount(500.00);
        payment.setTimestamp(LocalDateTime.now());
        payment.setStatus("Completed");


        System.out.println("Payment processed with ID: " + paymentId);

        targetSlot.setAvailableSeats(targetSlot.getAvailableSeats() - 1);
        timeSlotDAO.update(targetSlot);

        Booking newBooking = new Booking();
        newBooking.setBookingID(UUID.randomUUID().toString());
        newBooking.setUserID(customer.getUserID());
        newBooking.setSlotID(targetSlot.getSlotID());
        newBooking.setGymID(targetSlot.getGymID());
        newBooking.setStatus("Confirmed");
        newBooking.setBookingTime(LocalDateTime.now());

        bookingDAO.save(newBooking);
        paymentDAO.save(payment,newBooking.getBookingID());

        System.out.println("Booking successful for " + customer.getName() + " at gym " + targetSlot.getGymID());
        return newBooking;
    }

    @Override
    public boolean cancelBooking(Customer customer, String bookingId) {
        if (customer == null) {
            System.out.println("SERVICE ERROR: Customer cannot be null. Cannot cancel a booking.");
            return false;
        }

        Optional<Booking> bookingOptional = bookingDAO.findByBookingId(bookingId);
        if (bookingOptional.isEmpty()) {
            System.out.println("Cancellation failed. Booking with ID " + bookingId + " not found.");
            return false;
        }
        Booking booking = bookingOptional.get();

        if (!booking.getUserID().equals(customer.getUserID())) {
            System.out.println("Cancellation failed. You are not authorized to cancel this booking.");
            return false;
        }

        if ("Cancelled".equalsIgnoreCase(booking.getStatus())) {
            System.out.println("This booking has already been cancelled.");
            return false;
        }

        boolean isCancelled = bookingDAO.cancelBooking(bookingId);

        if (isCancelled) {
            Optional<TimeSlot> slotOptional = timeSlotDAO.findBySlotId(booking.getSlotID());
            if (slotOptional.isPresent()) {
                TimeSlot slot = slotOptional.get();
                slot.setAvailableSeats(slot.getAvailableSeats() + 1);
                timeSlotDAO.update(slot);
                System.out.println("Booking " + bookingId + " cancelled and slot has been released.");
                return true;
            } else {
                System.err.println("CRITICAL ERROR: Booking was cancelled but could not find slot " + booking.getSlotID() + " to release the seat.");
                return true;
            }
        }
        return false;
    }

    private void handleBookingClash(Customer customer, TimeSlot newSlot) {
        List<Booking> customerBookings = bookingDAO.findByUserId(customer.getUserID());

        for (Booking existingBooking : customerBookings) {
            if ("CONFIRMED".equalsIgnoreCase(existingBooking.getStatus())) {
                Optional<TimeSlot> existingSlotOptional = timeSlotDAO.findBySlotId(existingBooking.getSlotID());
                if (existingSlotOptional.isPresent()) {
                    TimeSlot existingSlot = existingSlotOptional.get();
                    if (existingSlot.getDate().isEqual(newSlot.getDate())) {
                        boolean isOverlapping = newSlot.getStartTime().isBefore(existingSlot.getEndTime()) &&
                                newSlot.getEndTime().isAfter(existingSlot.getStartTime());
                        if (isOverlapping) {
                            System.out.println("CLASH DETECTED: Your previous booking " + existingBooking.getBookingID() + " is being cancelled.");
                            // Pass the customer object to the cancelBooking method
                            this.cancelBooking(customer, existingBooking.getBookingID());
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<Booking> getBookingsForGym(String gymId) {
        System.out.println("SERVICE: Fetching bookings for Gym ID: " + gymId);
        return bookingDAO.findByGymId(gymId);
    }

    @Override
    public List<Booking> getBookingsForCustomerId(String customerId){
        return bookingDAO.findByUserId(customerId);
    }

}