package com.flipfit.dao;

import com.flipfit.bean.BaseUser;
import com.flipfit.bean.Customer;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.SystemAdmin;
import com.flipfit.bean.UserRole;
import com.flipfit.exception.DataAccessException;
import com.flipfit.exception.DuplicateUserException;
import com.flipfit.exception.UserNotFoundException;
import com.flipfit.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminDAOImpl implements UserDAO<SystemAdmin> {
    @Override
    public void saveUser(SystemAdmin user) {
        String sql1 = "INSERT INTO users VALUES (?,?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO system_admins VALUES (?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql1);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {

            pstmt.setString(1, user.getUserID());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPasswordHash());
            pstmt.setInt(5, user.getRole().getRoleId());
            pstmt.setString(6, user.getPhone());
            pstmt.setString(7, user.getAddress());
            pstmt.setString(8, user.getAdhaar());
            pstmt2.setString(1, user.getUserID());

            pstmt.executeUpdate();
            pstmt2.executeUpdate();

        } catch (SQLException e) {
            // Assuming 23505 is the SQL state for unique violation in your database (e.g., PostgreSQL)
            if ("23505".equals(e.getSQLState())) {
                throw new DuplicateUserException("Admin with ID " + user.getUserID() + " or email " + user.getEmail() + " already exists.");
            }
            throw new DataAccessException("Error saving admin user: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<SystemAdmin> findByEmail(String email) {
        // Implementation would be similar to findById, but querying by email
        return Optional.empty();
    }

    @Override
    public Optional<SystemAdmin> findById(String userId) {
        String sql = "SELECT * FROM users WHERE user_id = ? AND role_id = 3";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                SystemAdmin admin = new SystemAdmin();
                admin.setUserID(rs.getString("user_id"));
                admin.setName(rs.getString("name"));
                admin.setEmail(rs.getString("email"));
                admin.setPasswordHash(rs.getString("password_hash"));
                UserRole role = new UserRole(rs.getInt("role_id"), "Admin", "SYSTEM ADMIN");
                admin.setRole(role);
                admin.setPhone(rs.getString("phone"));
                admin.setAdhaar(rs.getString("aadhaar_card"));
                admin.setAddress(rs.getString("address"));
                return Optional.of(admin);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding admin by ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<SystemAdmin> getAll() {
        String sql = "SELECT * FROM users WHERE role_id = 3";
        List<SystemAdmin> list = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                SystemAdmin admin = new SystemAdmin();
                admin.setUserID(rs.getString("user_id"));
                admin.setName(rs.getString("name"));
                admin.setEmail(rs.getString("email"));
                admin.setPasswordHash(rs.getString("password_hash"));
                UserRole role = new UserRole(rs.getInt("role_id"), "Admin", "SYSTEM ADMIN");
                admin.setRole(role);
                admin.setPhone(rs.getString("phone"));
                admin.setAdhaar(rs.getString("aadhaar_card"));
                admin.setAddress(rs.getString("address"));
                list.add(admin);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all admins: " + e.getMessage(), e);
        }
        return list;
    }

    public List<BaseUser> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<BaseUser> list = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                BaseUser user;
                UserRole role = new UserRole();
                int roleId = rs.getInt("role_id");
                role.setRoleId(roleId);

                if (roleId == 3) {
                    role.setRoleName("SystemAdmin");
                    role.setDescription("SYSTEM ADMIN");
                    user = new SystemAdmin();
                } else if (roleId == 2) {
                    role.setRoleName("Gym Owner");
                    role.setDescription("GYM OWNER");
                    user = new GymOwner();
                } else {
                    role.setRoleName("Customer");
                    role.setDescription("GYM CUSTOMER");
                    user = new Customer();
                }

                user.setUserID(rs.getString("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setRole(role);
                user.setPhone(rs.getString("phone"));
                user.setAdhaar(rs.getString("aadhaar_card"));
                user.setAddress(rs.getString("address"));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all users: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public void removeUser(SystemAdmin user) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUserID());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new UserNotFoundException("Admin with ID " + user.getUserID() + " not found.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error removing admin user: " + e.getMessage(), e);
        }
    }
}
