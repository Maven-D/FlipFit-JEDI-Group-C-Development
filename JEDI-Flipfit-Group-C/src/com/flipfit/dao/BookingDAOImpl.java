package com.flipfit.dao;

import com.flipfit.bean.Booking;
import com.flipfit.exception.BookingFailedException;
import com.flipfit.exception.BookingNotFoundException;
import com.flipfit.exception.DataAccessException;
import com.flipfit.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingDAOImpl implements BookingDAO {

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
        String sql = "INSERT INTO bookings (booking_id, user_id, slot_id, payment_id, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, booking.getBookingID());
            pstmt.setString(2, booking.getUserID());
            pstmt.setString(3, booking.getSlotID());
            pstmt.setString(4, booking.getPaymentID());
            pstmt.setString(5, booking.getStatus());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new BookingFailedException("Creating booking failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new BookingFailedException("Error saving booking: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean update(Booking booking) {
        String sql = "UPDATE bookings SET status = ?, payment_id = ? WHERE booking_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, booking.getStatus());
            pstmt.setString(2, booking.getPaymentID());
            pstmt.setString(3, booking.getBookingID());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error updating booking: " + e.getMessage(), e);
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
            throw new DataAccessException("Error finding booking by ID: " + e.getMessage(), e);
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
            throw new DataAccessException("Error finding bookings by user ID: " + e.getMessage(), e);
        }
        return bookings;
    }

    @Override
    public List<Booking> findByGymId(String gymId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.* FROM bookings b JOIN time_slots ts ON b.slot_id = ts.slot_id WHERE ts.gym_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gymId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = mapResultSetToBooking(rs);
                    booking.setGymID(gymId);
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding bookings by gym ID: " + e.getMessage(), e);
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
            throw new DataAccessException("Error finding bookings by slot ID: " + e.getMessage(), e);
        }
        return bookings;
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        String sql = "UPDATE bookings SET status = 'Cancelled' WHERE booking_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookingId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new BookingNotFoundException("Booking with ID " + bookingId + " not found for cancellation.");
            }
            return true;
        } catch (SQLException e) {
            throw new DataAccessException("Error cancelling booking: " + e.getMessage(), e);
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
            throw new DataAccessException("Error finding all bookings: " + e.getMessage(), e);
        }
        return bookings;
    }
}
