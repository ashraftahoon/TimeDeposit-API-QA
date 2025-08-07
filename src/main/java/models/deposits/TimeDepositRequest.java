package models.deposits;

public class TimeDepositRequest {

    private double amount;
    private int durationInDays;

    public TimeDepositRequest(double amount, int durationInDays) {
        this.amount = amount;
        this.durationInDays = durationInDays;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
    }
}
