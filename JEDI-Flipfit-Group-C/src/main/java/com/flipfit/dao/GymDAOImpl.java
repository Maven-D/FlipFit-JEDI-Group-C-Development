package com.flipfit.dao;

import com.flipfit.bean.Gym;
import com.flipfit.exception.DataAccessException;
import com.flipfit.exception.GymNotFoundException;
import com.flipfit.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GymDAOImpl implements GymDAO {

    @Override
    public void save(Gym gym) {
        String sql = "INSERT INTO gyms (gym_id, name, address, owner_id, is_approved) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gym.getGymID());
            pstmt.setString(2, gym.getName());
            pstmt.setString(3, gym.getAddress());
            pstmt.setString(4, gym.getGymOwnerID());
            pstmt.setString(5, gym.getApprovalStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error saving gym: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Gym> findByGymId(String gymId) {
        String sql = "SELECT * FROM gyms WHERE gym_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gymId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Gym gym = new Gym();
                gym.setGymID(rs.getString("gym_id"));
                gym.setName(rs.getString("name"));
                gym.setAddress(rs.getString("address"));
                gym.setGymOwnerID(rs.getString("owner_id"));
                gym.setApprovalStatus(rs.getString("is_approved"));
                return Optional.of(gym);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding gym by ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Gym> findByOwnerId(String ownerId) {
        String sql = "SELECT * FROM gyms WHERE owner_id = ?";
        List<Gym> gyms = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ownerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Gym gym = new Gym();
                gym.setGymID(rs.getString("gym_id"));
                gym.setName(rs.getString("name"));
                gym.setAddress(rs.getString("address"));
                gym.setGymOwnerID(rs.getString("owner_id"));
                gym.setApprovalStatus(rs.getString("is_approved"));
                gyms.add(gym);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding gyms by owner ID: " + e.getMessage(), e);
        }
        return gyms;
    }

    @Override
    public List<Gym> getAll() {
        String sql = "SELECT * FROM gyms";
        List<Gym> gyms = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Gym gym = new Gym();
                gym.setGymID(rs.getString("gym_id"));
                gym.setName(rs.getString("name"));
                gym.setAddress(rs.getString("address"));
                gym.setGymOwnerID(rs.getString("owner_id"));
                gym.setApprovalStatus(rs.getString("is_approved"));
                gyms.add(gym);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all gyms: " + e.getMessage(), e);
        }
        return gyms;
    }

    @Override
    public boolean remove(String gymId) {
        String sql = "DELETE FROM gyms WHERE gym_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gymId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new GymNotFoundException("Gym with ID " + gymId + " not found.");
            }
            return true;
        } catch (SQLException e) {
            throw new DataAccessException("Error removing gym: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean update(Gym gymToUpdate) {
        String sql = "UPDATE gyms SET name = ?, address = ?, owner_id = ?, is_approved = ?, gst_number = ? WHERE gym_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gymToUpdate.getName());
            pstmt.setString(2, gymToUpdate.getAddress());
            pstmt.setString(3, gymToUpdate.getGymOwnerID());
            pstmt.setString(4, gymToUpdate.getApprovalStatus());
            pstmt.setString(6, gymToUpdate.getGymID());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new GymNotFoundException("Gym with ID " + gymToUpdate.getGymID() + " not found for update.");
            }
            return true;
        } catch (SQLException e) {
            throw new DataAccessException("Error updating gym: " + e.getMessage(), e);
        }
    }
}
