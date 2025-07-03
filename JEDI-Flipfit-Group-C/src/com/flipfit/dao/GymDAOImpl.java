package com.flipfit.dao;

import com.flipfit.bean.Gym;
import com.flipfit.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC-based implementation of the GymDAO interface.
 * Handles all database operations for Gym entities.
 */
public class GymDAOImpl implements GymDAO {

    @Override
    public void save(Gym gym) {
        // Corrected column names to match the schema
        String sql = "INSERT INTO gyms (gym_id, name, address, owner_id, is_approved, gst_number) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, gym.getGymID());
            pstmt.setString(2, gym.getName());
            pstmt.setString(3, gym.getAddress());
            pstmt.setString(4, gym.getGymOwnerID());
            pstmt.setString(5, gym.getApprovalStatus());
            pstmt.setString(6, gym.getGstNumber());

            pstmt.executeUpdate();
            System.out.println("DAO: Successfully saved gym: " + gym.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Gym> findByGymId(String gymId) {
        // Corrected column name in WHERE clause
        String sql = "SELECT * FROM gyms WHERE gym_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, gymId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Gym gym = new Gym();
                // Corrected column names for ResultSet
                gym.setGymID(rs.getString("gym_id"));
                gym.setName(rs.getString("name"));
                gym.setAddress(rs.getString("address"));
                gym.setGymOwnerID(rs.getString("owner_id"));
                gym.setApprovalStatus(rs.getString("is_approved"));
                gym.setGstNumber(rs.getString("gst_number"));
                return Optional.of(gym);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Gym> findByOwnerId(String ownerId) {
        // Corrected column name in WHERE clause
        String sql = "SELECT * FROM gyms WHERE owner_id = ?";
        List<Gym> gyms = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ownerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Gym gym = new Gym();
                // Corrected column names for ResultSet
                gym.setGymID(rs.getString("gym_id"));
                gym.setName(rs.getString("name"));
                gym.setAddress(rs.getString("address"));
                gym.setGymOwnerID(rs.getString("owner_id"));
                gym.setApprovalStatus(rs.getString("is_approved"));
                gym.setGstNumber(rs.getString("gst_number"));
                gyms.add(gym);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                // Corrected column names for ResultSet
                gym.setGymID(rs.getString("gym_id"));
                gym.setName(rs.getString("name"));
                gym.setAddress(rs.getString("address"));
                gym.setGymOwnerID(rs.getString("owner_id"));
                gym.setApprovalStatus(rs.getString("is_approved"));
                gym.setGstNumber(rs.getString("gst_number"));
                gyms.add(gym);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gyms;
    }

    @Override
    public boolean remove(String gymId) {
        // Corrected column name in WHERE clause
        String sql = "DELETE FROM gyms WHERE gym_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, gymId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Gym gymToUpdate) {
        // Corrected column names in SET and WHERE clauses
        String sql = "UPDATE gyms SET name = ?, address = ?, owner_id = ?, is_approved = ?, gst_number = ? WHERE gym_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, gymToUpdate.getName());
            pstmt.setString(2, gymToUpdate.getAddress());
            pstmt.setString(3, gymToUpdate.getGymOwnerID());
            pstmt.setString(4, gymToUpdate.getApprovalStatus());
            pstmt.setString(5, gymToUpdate.getGstNumber());
            pstmt.setString(6, gymToUpdate.getGymID());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
