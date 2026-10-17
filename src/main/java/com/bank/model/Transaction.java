package com.bank.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false, length = 30)
    private String type;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected Transaction() {
    }

    public Transaction(
            Long id,
            Account account,
            String type,
            BigDecimal amount
    ) {
        this.id = id;
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}