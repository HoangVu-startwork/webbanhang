package com.example.webbanhang.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long amount;
    private String bankCode;
    private String cardType;
    private LocalDateTime createDate;
    private String currency;

    @Column(nullable = false)
    private String mahd;

    private String status;

    @Column(nullable = false, unique = true)
    private String transactionId;

    private String vnpResponseCode;
    private String vnpTransactionNo;
    private String vnpOrderInfo;

    private String email;
    private String diachi;
    private String ghichu;

    private String productIds;
}
