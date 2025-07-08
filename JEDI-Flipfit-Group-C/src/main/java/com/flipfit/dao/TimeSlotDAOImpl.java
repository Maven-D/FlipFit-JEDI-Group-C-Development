package com.flipfit.dao;

import com.flipfit.bean.TimeSlot;
import com.flipfit.exception.DataAccessException;
import com.flipfit.exception.SlotNotFoundException;
import com.flipfit.util.DBConnectionUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TimeSlotDAOImpl implements TimeSlotDAO {

    private TimeSlot mapResultSetToTimeSlot(ResultSet rs) throws SQLException {
        TimeSlot slot = new TimeSlot();
        slot.setSlotID(rs.getString("slot_id"));
        slot.setGymID(rs.getString("gym_id"));
        slot.setAvailableSeats(rs.getInt("available_seats"));
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
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.of(timeSlot.getDate(), timeSlot.getStartTime())));
            pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.of(timeSlot.getDate(), timeSlot.getEndTime())));
            pstmt.setInt(5, timeSlot.getAvailableSeats());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error saving time slot: " + e.getMessage(), e);
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
            throw new DataAccessException("Error finding time slot by ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<TimeSlot> findAvailableByGymIdAndDate(String gymId, LocalDate date) {
        List<TimeSlot> availableSlots = new ArrayList<>();
        String sql = "SELECT * FROM time_slots WHERE gym_id = ? AND DATE(start_time) = ? AND available_seats > 0";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gymId);
            pstmt.setDate(2, Date.valueOf(date));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    availableSlots.add(mapResultSetToTimeSlot(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding available slots: " + e.getMessage(), e);
        }
        return availableSlots;
    }

    @Override
    public boolean update(TimeSlot slotToUpdate) {
        String sql = "UPDATE time_slots SET available_seats = ? WHERE slot_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, slotToUpdate.getAvailableSeats());
            pstmt.setString(2, slotToUpdate.getSlotID());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SlotNotFoundException("Time slot with ID " + slotToUpdate.getSlotID() + " not found for update.");
            }
            return true;
        } catch (SQLException e) {
            throw new DataAccessException("Error updating time slot: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(String slotId) {
        String sql = "DELETE FROM time_slots WHERE slot_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, slotId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SlotNotFoundException("Time slot with ID " + slotId + " not found for deletion.");
            }
            return true;
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting time slot: " + e.getMessage(), e);
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
            throw new DataAccessException("Error getting all time slots: " + e.getMessage(), e);
        }
        return allSlots;
    }
}
