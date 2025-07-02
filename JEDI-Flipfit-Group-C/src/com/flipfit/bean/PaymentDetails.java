package com.flipfit.bean;

import java.time.LocalDateTime;

/**
 */
public class PaymentDetails {

    private String paymentID;
    private double amount;
    private LocalDateTime timestamp;
    private String status;


    public PaymentDetails() {
    }

    public PaymentDetails(String paymentID, double amount, LocalDateTime timestamp, String status) {
        this.paymentID = paymentID;
        this.amount = amount;
        this.timestamp = timestamp;
        this.status = status;
    }

    // --- Getters and Setters ---

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}