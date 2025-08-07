package models.deposits;

import java.time.LocalDateTime;

// This class represents the response model for a all the deposits.
public class DepositResponse {

    private Long id;
    private Double amount;
    private Integer type; // 1 = Time Deposit, etc.
    private Integer period;
    private LocalDateTime startDate;
    private LocalDateTime maturityDate;
    private Double apy;
    private Integer status;
    private LocalDateTime rolloverUntil;
    private Long userId;
    private Object user; // Can be replaced with User model if needed

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDateTime maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Double getApy() {
        return apy;
    }

    public void setApy(Double apy) {
        this.apy = apy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getRolloverUntil() {
        return rolloverUntil;
    }

    public void setRolloverUntil(LocalDateTime rolloverUntil) {
        this.rolloverUntil = rolloverUntil;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }
}
