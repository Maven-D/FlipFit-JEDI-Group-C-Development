package com.flipfit.dao;

import com.flipfit.bean.Booking;
import com.flipfit.util.DBConnectionUtil; // Assuming this utility class exists

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of the BookingDAO interface.
 * This class handles all database operations related to bookings.
 */
public class BookingDAOImpl implements BookingDAO {

    /**
     * Maps a row from a ResultSet to a Booking object.
     * @param rs The ResultSet to map.
     * @return A populated Booking object.
     * @throws SQLException if a database access error occurs.
     */
    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingID(rs.getString("booking_id"));
        booking.setUserID(rs.getString("user_id"));
        booking.setSlotID(rs.getString("slot_id"));
        booking.setPaymentID(rs.getString("payment_id"));
        booking.setStatus(rs.getString("status"));
        return booking;
    }

    @Override
    public void save(Booking booking) {
        // SQL statement to insert a new record into the bookings table.
        String sql = "INSERT INTO bookings (booking_id, user_id, slot_id, payment_id, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, booking.getBookingID());
            pstmt.setString(2, booking.getUserID());
            pstmt.setString(3, booking.getSlotID());
            pstmt.setString(4, booking.getPaymentID());
            pstmt.setString(5, booking.getStatus());

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("DAO: " + rowsAffected + " booking row(s) inserted.");

        } catch (SQLException e) {
            System.err.println("Error saving booking: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(Booking booking) {
        // SQL to update an existing booking. Typically, only status or payment might change.
        String sql = "UPDATE bookings SET status = ?, payment_id = ? WHERE booking_id = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, booking.getStatus());
            pstmt.setString(2, booking.getPaymentID());
            pstmt.setString(3, booking.getBookingID());

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("DAO: " + rowsAffected + " booking row(s) updated.");
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating booking: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Booking> findByBookingId(String bookingId) {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bookingId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToBooking(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding booking by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Booking> findByUserId(String userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bookings.add(mapResultSetToBooking(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding bookings by user ID: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> findByGymId(String gymId) {
        List<Booking> bookings = new ArrayList<>();
        // This query joins bookings with time_slots to filter by the gym's ID.
        String sql = "SELECT b.* FROM bookings b JOIN time_slots ts ON b.slot_id = ts.slot_id WHERE ts.gym_id = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, gymId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = mapResultSetToBooking(rs);
                    booking.setGymID(gymId); // Manually set the gym ID since we know it from the query context
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding bookings by gym ID: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> findBySlotId(String slotId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE slot_id = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, slotId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bookings.add(mapResultSetToBooking(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding bookings by slot ID: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        // This is a specific update operation to change the booking status.
        String sql = "UPDATE bookings SET status = 'Cancelled' WHERE booking_id = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bookingId);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("DAO: " + rowsAffected + " booking row(s) marked as cancelled.");
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error cancelling booking: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";

        try (Connection conn = DBConnectionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all bookings: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }
}