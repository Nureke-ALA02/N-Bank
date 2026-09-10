package com.bank.model;

import java.time.LocalDateTime;

public class Transaction {
    private Long id;
    private String type;
    private double amount ;
    private LocalDateTime date;

    public Transaction(Long id, String type, double amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }
}

