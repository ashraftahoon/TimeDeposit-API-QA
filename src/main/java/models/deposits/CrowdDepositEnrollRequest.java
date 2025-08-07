package models.deposits;

import java.time.LocalDateTime;

public class CrowdDepositEnrollRequest {
    private int offerId;
    private double amount;
    private String rolloverUntilDate;



    public CrowdDepositEnrollRequest(int offerId, double amount, String rolloverUntilDate) {
        this.offerId = offerId;
        this.amount = amount;
        this.rolloverUntilDate = rolloverUntilDate;
    }

    // Getters and Setters
    public int getOfferId() { return offerId; }
    public void setOfferId(int offerId) { this.offerId = offerId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getRolloverUntilDate() { return rolloverUntilDate; }
    public void setRolloverUntilDate(String rolloverUntilDate) {
        this.rolloverUntilDate = rolloverUntilDate;
    }
}