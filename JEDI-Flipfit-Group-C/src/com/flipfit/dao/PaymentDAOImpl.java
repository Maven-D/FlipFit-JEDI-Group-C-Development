package com.flipfit.dao;

import com.flipfit.bean.PaymentDetails;
import com.flipfit.exception.DataAccessException;
import com.flipfit.exception.PaymentFailedException;
import com.flipfit.util.DBConnectionUtil;

import java.sql.*;
import java.util.Optional;

public class PaymentDAOImpl implements PaymentDAO {

    private PaymentDetails mapResultSetToPayment(ResultSet rs) throws SQLException {
        PaymentDetails payment = new PaymentDetails();
        payment.setPaymentID(rs.getString("payment_id"));
        payment.setAmount(rs.getDouble("amount"));
        payment.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        payment.setStatus(rs.getString("status"));
        return payment;
    }

    @Override
    public void save(PaymentDetails payment, String bookingId) {
        String sql = "INSERT INTO payments (payment_id, booking_id, amount, timestamp, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, payment.getPaymentID());
            pstmt.setString(2, bookingId);
            pstmt.setDouble(3, payment.getAmount());
            pstmt.setTimestamp(4, Timestamp.valueOf(payment.getTimestamp()));
            pstmt.setString(5, payment.getStatus());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new PaymentFailedException("Creating payment failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new PaymentFailedException("Error saving payment: " + e.getMessage(), e);
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
            throw new DataAccessException("Error finding payment by ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }
}
