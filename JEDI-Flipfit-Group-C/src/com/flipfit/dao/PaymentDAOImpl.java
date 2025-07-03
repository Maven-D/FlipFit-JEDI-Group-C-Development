// File: com/flipfit/dao/PaymentDAOImpl.java
package com.flipfit.dao;

import com.flipfit.bean.PaymentDetails;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the PaymentDAO interface.
 */
public class PaymentDAOImpl implements PaymentDAO {

    private static final List<PaymentDetails> paymentTable = new ArrayList<>();

    static {
        paymentTable.add(new PaymentDetails("payment001", 300.0, LocalDateTime.now().minusHours(1), "Completed"));
    }

    @Override
    public void save(PaymentDetails payment) {
        System.out.println("DAO: Saving payment " + payment.getPaymentID());
        paymentTable.add(payment);
    }

    @Override
    public Optional<PaymentDetails> findByPaymentId(String paymentId) {
        System.out.println("DAO: Finding payment with ID: " + paymentId);
        return paymentTable.stream()
                .filter(p -> p.getPaymentID().equals(paymentId))
                .findFirst();
    }
}
