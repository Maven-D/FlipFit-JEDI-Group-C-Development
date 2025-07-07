package com.flipfit.dao;

import com.flipfit.bean.GymOwner;
import com.flipfit.bean.UserRole;
import com.flipfit.exception.DataAccessException;
import com.flipfit.exception.DuplicateUserException;
import com.flipfit.exception.UserNotFoundException;
import com.flipfit.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GymOwnerDAOImpl implements UserDAO<GymOwner> {
    @Override
    public void saveUser(GymOwner user) {
        String sql1 = "INSERT INTO users VALUES (?,?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO gym_owners VALUES (?,?,?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql1);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {

            conn.setAutoCommit(false);

            pstmt.setString(1, user.getUserID());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPasswordHash());
            pstmt.setInt(5, user.getRole().getRoleId());
            pstmt.setString(6, user.getPhone());
            pstmt.setString(7, user.getAddress());
            pstmt.setString(8, user.getAdhaar());
            pstmt.executeUpdate();

            pstmt2.setString(1, user.getUserID());
            pstmt2.setString(2, user.getPan());
            pstmt2.setString(3, user.getApprovalStatus());
            pstmt2.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new DuplicateUserException("Gym Owner with ID " + user.getUserID() + " or email " + user.getEmail() + " already exists.");
            }
            throw new DataAccessException("Error saving gym owner: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<GymOwner> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<GymOwner> findById(String userId) {
        String sql = "SELECT * FROM users u JOIN gym_owners o ON u.user_id = o.user_id WHERE u.user_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                GymOwner owner = new GymOwner();
                owner.setUserID(rs.getString("user_id"));
                owner.setName(rs.getString("name"));
                owner.setEmail(rs.getString("email"));
                owner.setPasswordHash(rs.getString("password_hash"));
                UserRole role = new UserRole(rs.getInt("role_id"), "GymOwner", "GYM OWNER");
                owner.setRole(role);
                owner.setPhone(rs.getString("phone"));
                owner.setAdhaar(rs.getString("aadhaar_card"));
                owner.setAddress(rs.getString("address"));
                owner.setPan(rs.getString("pancard"));
                owner.setApprovalStatus(rs.getString("is_approved"));
                return Optional.of(owner);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding gym owner by ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<GymOwner> getAll() {
        String sql = "SELECT * FROM users u JOIN gym_owners o ON u.user_id = o.user_id WHERE u.role_id=2";
        List<GymOwner> list = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                GymOwner owner = new GymOwner();
                owner.setUserID(rs.getString("user_id"));
                owner.setName(rs.getString("name"));
                owner.setEmail(rs.getString("email"));
                owner.setPasswordHash(rs.getString("password_hash"));
                UserRole role = new UserRole(rs.getInt("role_id"), "GymOwner", "GYM OWNER");
                owner.setRole(role);
                owner.setPhone(rs.getString("phone"));
                owner.setAdhaar(rs.getString("aadhaar_card"));
                owner.setAddress(rs.getString("address"));
                owner.setPan(rs.getString("pancard"));
                owner.setApprovalStatus(rs.getString("is_approved"));
                list.add(owner);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all gym owners: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public void removeUser(GymOwner user) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUserID());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new UserNotFoundException("Gym owner with ID " + user.getUserID() + " not found.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error removing gym owner: " + e.getMessage(), e);
        }
    }

    public boolean update(GymOwner user) {
        String updateUserSQL = "UPDATE users SET name = ?, email = ?, password_hash = ?, phone = ?, address = ?, aadhaar_card = ? WHERE user_id = ?";
        String updateGymOwnerSQL = "UPDATE gym_owners SET pancard = ?, is_approved = ? WHERE user_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmtUser = conn.prepareStatement(updateUserSQL);
                 PreparedStatement pstmtOwner = conn.prepareStatement(updateGymOwnerSQL)) {

                pstmtUser.setString(1, user.getName());
                pstmtUser.setString(2, user.getEmail());
                pstmtUser.setString(3, user.getPasswordHash());
                pstmtUser.setString(4, user.getPhone());
                pstmtUser.setString(5, user.getAddress());
                pstmtUser.setString(6, user.getAdhaar());
                pstmtUser.setString(7, user.getUserID());
                int userRows = pstmtUser.executeUpdate();

                if (userRows == 0) {
                    throw new UserNotFoundException("Gym owner with ID " + user.getUserID() + " not found in users table.");
                }

                pstmtOwner.setString(1, user.getPan());
                pstmtOwner.setString(2, user.getApprovalStatus());
                pstmtOwner.setString(3, user.getUserID());
                pstmtOwner.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw new DataAccessException("Error updating gym owner: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error managing transaction for gym owner update: " + e.getMessage(), e);
        }
    }
}
