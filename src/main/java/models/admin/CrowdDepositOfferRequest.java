package models.admin;

public class CrowdDepositOfferRequest {
    private double amount;
    private int period;
    private String  startDate;
    private String  maturityDate;
    private double apy;
    private int id;

    public CrowdDepositOfferRequest(double amount, int period, String  startDate, String  maturityDate, double apy) {
        this.amount = amount;
        this.period = period;
        this.startDate = startDate;
        this.maturityDate = maturityDate;
        this.apy = apy;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String  getStartDate() {
        return startDate;
    }

    public void setStartDate(String  startDate) {
        this.startDate = startDate;
    }

    public String  getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String  maturityDate) {
        this.maturityDate = maturityDate;
    }

    public double getApy() {
        return apy;
    }

    public void setApy(double apy) {
        this.apy = apy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
