package com.mosh.dot_assessment_test.model;

import com.mosh.dot_assessment_test.enums.Status;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mosh
 * @role software engineer
 * @createdOn 06 Fri Sep, 2024
 */

@Entity
@Table(name="transactions")
@Getter
@Setter
@ToString
public class Transaction extends Auditable<String> implements Serializable {
    private String transactionReference;
    private String sourceAccountNumber;
    private String beneficiaryAccountNumber;
    private String beneficiaryBankCode;
    private BigDecimal amount;
    private Double transactionFee;
    private BigDecimal billedAmount;
    private String description;
    private LocalDateTime dateCreated;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String statusMessage;
    private Boolean commissionWorthy;
    private Double commission;

    public Boolean getCommissionWorthy() {
        return commissionWorthy == null ? false : commissionWorthy;
    }
}
