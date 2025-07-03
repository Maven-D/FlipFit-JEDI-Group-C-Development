package com.flipfit.dao;

import com.flipfit.bean.Customer;
import com.flipfit.bean.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements UserDAO<Customer> {
    @Override
    public void saveUser(Customer user) {
        String sql1 = "INSERT INTO users VALUES (?,?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO customers VALUES (?)";
        try (Connection conn = com.flipfit.utils.DBConnectionUtil.getConnection();
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


            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findById(String userId) {
        return Optional.empty();
    }

    @Override
    public List<Customer> getAll() {
        return List.of();
    }

    @Override
    public List<Customer> getByRole(UserRole role) {
        return List.of();
    }

    @Override
    public void removeUser(Customer user) {

    }
}
