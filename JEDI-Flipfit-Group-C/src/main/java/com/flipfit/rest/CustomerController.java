package com.flipfit.rest;

import com.flipfit.bean.*;
import com.flipfit.business.FlipFitBookingServiceInterface;
import com.flipfit.business.FlipFitGymServiceInterface;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerController {

    private final FlipFitGymServiceInterface gymService;
    private final FlipFitBookingServiceInterface bookingService;

    // Inject all required services through the constructor
    public CustomerController(FlipFitGymServiceInterface gymService, FlipFitBookingServiceInterface bookingService) {
        this.gymService = gymService;
        this.bookingService = bookingService;
    }

    /**
     * API to view all approved gyms.
     * Replaces the first part of viewAvailableSlots().
     */
    @GET
    @Path("/gyms")
    public Response getApprovedGyms() {
        List<Gym> availableGyms = gymService.getAllGyms().stream()
                .filter(gym -> "CONFIRMED".equalsIgnoreCase(gym.getApprovalStatus()))
                .collect(Collectors.toList());

        return Response.ok(availableGyms).build();
    }

    /**
     * API to view available slots for a specific gym on a given date.
     * Replaces the second part of viewAvailableSlots().
     */
    @GET
    @Path("/gyms/{gymId}/slots")
    public Response getAvailableSlots(@PathParam("gymId") String gymId, @QueryParam("date") String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            List<TimeSlot> slots = gymService.getAvailability(gymId, date);
            return Response.ok(slots).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Invalid date format. Please use yyyy-MM-dd.\"}").build();
        }
    }

    /**
     * API for a customer to book a slot.
     * Replaces bookSlot().
     */
    @POST
    @Path("/{customerId}/bookings")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response bookSlot(@PathParam("customerId") String customerId, @NotNull @Valid BookingRequest bookingRequest) {
        // Create a temporary customer object just with the ID to pass to the service
        Customer customer = new Customer();
        customer.setUserID(customerId);

        Booking booking = bookingService.makeBooking(customer, bookingRequest.getSlotId());

        if (booking != null) {
            return Response.status(Response.Status.CREATED).entity(booking).build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\": \"Booking failed. Slot may be full or ID is incorrect.\"}").build();
        }
    }

    /**
     * API for a customer to view their own bookings.
     * Replaces viewMyBookings().
     */
    @GET
    @Path("/{customerId}/bookings")
    public Response viewMyBookings(@PathParam("customerId") String customerId) {
        List<Booking> bookings = bookingService.getBookingsForCustomerId(customerId);
        return Response.ok(bookings).build();
    }

    /**
     * API for a customer to cancel a booking.
     * Replaces cancelBooking().
     */
    @DELETE
    @Path("/{customerId}/bookings/{bookingId}")
    public Response cancelBooking(@PathParam("customerId") String customerId, @PathParam("bookingId") String bookingId) {
        Customer customer = new Customer();
        customer.setUserID(customerId);

        boolean isCancelled = bookingService.cancelBooking(customer, bookingId);

        if (isCancelled) {
            return Response.ok("{\"message\": \"Booking cancelled successfully.\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Failed to cancel booking. Check if the Booking ID is correct and belongs to you.\"}").build();
        }
    }
}