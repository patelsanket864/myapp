package com.employee.myapp.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Account account;
    private LocalDateTime transactionDate;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType type; // DEBIT or CREDIT
    private String description;
}

enum TransactionType {
    DEBIT, CREDIT
}
