package com.example.softwaresystemapi.models;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionModel {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID transaction_id;
    @Column(nullable = false, length = 110)
    private String name;
    @Column(nullable = false, length = 110)
    private Boolean status;
    @Column(nullable = false, length = 40)
    private Float totalValue;
    @Column(nullable = false, length = 40)
    private String transactionNumber;
    @Column(nullable = false, length = 80)
    private Date createdAt;
    @Column(nullable = false, length = 80)
    private Time createdHour;

    public UUID getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(UUID transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Float getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Float totalValue) {
        this.totalValue = totalValue;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Time getCreatedHour() {
        return createdHour;
    }

    public void setCreatedHour(Time createdHour) {
        this.createdHour = createdHour;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }
}
