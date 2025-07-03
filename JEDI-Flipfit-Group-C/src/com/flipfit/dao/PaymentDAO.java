package com.flipfit.dao;

import com.flipfit.bean.PaymentDetails;
import java.util.Optional;

/**
 * Data Access Object interface for Payment operations.
 */
public interface PaymentDAO {
    void save(PaymentDetails payment);
    Optional<PaymentDetails> findByPaymentId(String paymentId);
}