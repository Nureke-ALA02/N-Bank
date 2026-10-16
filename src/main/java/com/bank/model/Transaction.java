package com.bank.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id",nullable = false)
    private Account account;
    @Column(nullable = false,length = 30)
    private String type;
    @Column(nullable = false)
    private double amount ;
    @Column(nullable = false)
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

