package com.mosh.dot_assessment_test.apimodel;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


/**
 * @author mosh
 * @role software engineer
 * @createdOn 06 Fri Sep, 2024
 */

@Getter
@Setter
@ToString
public class TransactionRequest {
    @NotNull(message = "sourceAccountNumber is required")
    @NotEmpty(message = "sourceAccountNumber is required")
    private String sourceAccountNumber;
    @NotNull(message = "beneficiaryAccountNumber is required")
    @NotEmpty(message = "beneficiaryAccountNumber is required")
    private String beneficiaryAccountNumber;
    @NotNull(message = "Amount is required")
    @NotEmpty(message = "Amount is required")
    @Min(value = 1, message = "Amount must be greater than or equal to 1")
    private Double amount;
    private String narration;
}
