package com.mosh.dot_assessment_test.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author mosh
 * @role software engineer
 * @createdOn 06 Fri Sep, 2024
 */
@Entity
@Table(name="daily_transaction_summary")
@Getter
@Setter
@ToString
public class DailyTransactionSummary extends Auditable<String> implements Serializable {

    private LocalDate date;
    private BigDecimal totalAmount;
    private BigDecimal totalTransactionFee;
    private BigDecimal totalCommission;
}
