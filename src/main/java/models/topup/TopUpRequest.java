package models.topup;

public class TopUpRequest {
    private double amount;

    // Constructors
    public TopUpRequest() {}

    public TopUpRequest(double amount) {
        this.amount = amount;
    }

    // Getters and Setters
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}