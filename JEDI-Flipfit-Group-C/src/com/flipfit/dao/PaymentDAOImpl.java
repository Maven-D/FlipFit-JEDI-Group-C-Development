package com.flipfit.dao;

import com.flipfit.bean.PaymentDetails;
import com.flipfit.util.DBConnectionUtil;

import java.sql.*;
import java.util.Optional;

/**
 * JDBC implementation of the PaymentDAO interface.
 */
public class PaymentDAOImpl implements PaymentDAO {

    /**
     * Maps a row from a ResultSet to a PaymentDetails object.
     */
    private PaymentDetails mapResultSetToPayment(ResultSet rs) throws SQLException {
        PaymentDetails payment = new PaymentDetails();
        payment.setPaymentID(rs.getString("payment_id"));
        payment.setAmount(rs.getDouble("amount"));
        payment.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        payment.setStatus(rs.getString("status"));
        return payment;
    }

    @Override
    public void save(PaymentDetails payment,String bookingId) {
        // This query assumes the payments table does not need a booking_id for the initial insert.
        String sql = "INSERT INTO payments (payment_id, booking_id, amount, timestamp, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, payment.getPaymentID());
            pstmt.setString(2, bookingId);
            pstmt.setDouble(3, payment.getAmount());
            pstmt.setTimestamp(4, Timestamp.valueOf(payment.getTimestamp()));
            pstmt.setString(5, payment.getStatus());

            pstmt.executeUpdate();
            System.out.println("DAO: Successfully saved payment " + payment.getPaymentID());

        } catch (SQLException e) {
            System.err.println("Error saving payment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Optional<PaymentDetails> findByPaymentId(String paymentId) {
        String sql = "SELECT * FROM payments WHERE payment_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, paymentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToPayment(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding payment by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }
}