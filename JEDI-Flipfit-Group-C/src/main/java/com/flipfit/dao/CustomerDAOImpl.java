package com.flipfit.dao;

import com.flipfit.bean.Customer;
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

public class CustomerDAOImpl implements UserDAO<Customer> {
    @Override
    public void saveUser(Customer user) {
        String sql1 = "INSERT INTO users VALUES (?,?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO customers VALUES (?)";
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
            if ("23505".equals(e.getSQLState())) {
                throw new DuplicateUserException("Customer with ID " + user.getUserID() + " or email " + user.getEmail() + " already exists.");
            }
            throw new DataAccessException("Error saving customer: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findById(String userId) {
        String sql = "SELECT * FROM users WHERE user_id = ? AND role_id = 1";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Customer customer = new Customer();
                customer.setUserID(rs.getString("user_id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setPasswordHash(rs.getString("password_hash"));
                UserRole role = new UserRole(rs.getInt("role_id"), "Customer", "GYM CUSTOMER");
                customer.setRole(role);
                customer.setPhone(rs.getString("phone"));
                customer.setAdhaar(rs.getString("aadhaar_card"));
                customer.setAddress(rs.getString("address"));
                return Optional.of(customer);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding customer by ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Customer> getAll() {
        String sql = "SELECT * FROM users WHERE role_id = 1";
        List<Customer> list = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setUserID(rs.getString("user_id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setPasswordHash(rs.getString("password_hash"));
                UserRole role = new UserRole(rs.getInt("role_id"), "Customer", "GYM CUSTOMER");
                customer.setRole(role);
                customer.setPhone(rs.getString("phone"));
                customer.setAdhaar(rs.getString("aadhaar_card"));
                customer.setAddress(rs.getString("address"));
                list.add(customer);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all customers: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public void removeUser(Customer user) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUserID());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new UserNotFoundException("Customer with ID " + user.getUserID() + " not found.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error removing customer: " + e.getMessage(), e);
        }
    }
}
