package com.flipfit.dao;

import com.flipfit.bean.TimeSlot;
import com.flipfit.util.DBConnectionUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the TimeSlotDAO interface.
 */
public class TimeSlotDAOImpl implements TimeSlotDAO {

    private TimeSlot mapResultSetToTimeSlot(ResultSet rs) throws SQLException {
        TimeSlot slot = new TimeSlot();
        slot.setSlotID(rs.getString("slot_id"));
        slot.setGymID(rs.getString("gym_id"));
        slot.setAvailableSeats(rs.getInt("available_seats"));

        // Convert SQL Timestamp to LocalDateTime, then split into Date and Time
        Timestamp startTimeStamp = rs.getTimestamp("start_time");
        if (startTimeStamp != null) {
            LocalDateTime startDateTime = startTimeStamp.toLocalDateTime();
            slot.setDate(startDateTime.toLocalDate());
            slot.setStartTime(startDateTime.toLocalTime());
        }

        Timestamp endTimeStamp = rs.getTimestamp("end_time");
        if (endTimeStamp != null) {
            slot.setEndTime(endTimeStamp.toLocalDateTime().toLocalTime());
        }

        return slot;
    }


    @Override
    public void save(TimeSlot timeSlot) {
        String sql = "INSERT INTO time_slots (slot_id, gym_id, start_time, end_time, available_seats) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, timeSlot.getSlotID());
            pstmt.setString(2, timeSlot.getGymID());
            // Combine LocalDate and LocalTime into a single Timestamp for the database
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.of(timeSlot.getDate(), timeSlot.getStartTime())));
            pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.of(timeSlot.getDate(), timeSlot.getEndTime())));
            pstmt.setInt(5, timeSlot.getAvailableSeats());

            pstmt.executeUpdate();
            System.out.println("DAO: Successfully saved time slot " + timeSlot.getSlotID());

        } catch (SQLException e) {
            System.err.println("Error saving time slot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Optional<TimeSlot> findBySlotId(String slotId) {
        String sql = "SELECT * FROM time_slots WHERE slot_id = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, slotId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToTimeSlot(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding time slot by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public List<TimeSlot> findAvailableByGymIdAndDate(String gymId, LocalDate date) {
        List<TimeSlot> availableSlots = new ArrayList<>();
        // Note: The DATE() function is specific to MySQL. For other databases like PostgreSQL, use CAST(start_time AS DATE).
        String sql = "SELECT * FROM time_slots WHERE gym_id = ? AND DATE(start_time) = ? AND available_seats > 0";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, gymId);
            pstmt.setDate(2, Date.valueOf(date)); // Convert LocalDate to sql.Date

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    availableSlots.add(mapResultSetToTimeSlot(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding available slots: " + e.getMessage());
            e.printStackTrace();
        }
        return availableSlots;
    }

    @Override
    public boolean update(TimeSlot slotToUpdate) {
        // Typically, only the available seats would be updated.
        String sql = "UPDATE time_slots SET available_seats = ? WHERE slot_id = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, slotToUpdate.getAvailableSeats());
            pstmt.setString(2, slotToUpdate.getSlotID());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating time slot: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String slotId) {
        String sql = "DELETE FROM time_slots WHERE slot_id = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, slotId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting time slot: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<TimeSlot> getAll() {
        List<TimeSlot> allSlots = new ArrayList<>();
        String sql = "SELECT * FROM time_slots";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                allSlots.add(mapResultSetToTimeSlot(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all time slots: " + e.getMessage());
            e.printStackTrace();
        }
        return allSlots;
    }
}
