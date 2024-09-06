package com.mosh.dot_assessment_test;

import com.mosh.dot_assessment_test.apimodel.TransactionRequest;
import com.mosh.dot_assessment_test.enums.Status;
import com.mosh.dot_assessment_test.model.Transaction;
import com.mosh.dot_assessment_test.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author mosh
 * @role software engineer
 * @createdOn 06 Fri Sep, 2024
 */
@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private  TransactionService transactionService;

    @Test
    public void testSuccessfulTransaction() {
        // Test the case for successful transaction with enough balance
        TransactionRequest request = new TransactionRequest();
        request.setAmount(1000.0);
        request.setBeneficiaryAccountNumber("0142273992");
        request.setSourceAccountNumber("3020202211");
        request.setNarration("Test insufficient");
        Transaction transaction = transactionService.processTransfer(request);

        assertEquals(Status.SUCCESSFUL, transaction.getStatus(), "Transaction status must be SUCCESSFUL");

    }

    @Test
    public void testInsufficientFunds() {
        // Test the case where there are insufficient funds
        TransactionRequest request = new TransactionRequest();
        request.setAmount(10000000.0);
        request.setBeneficiaryAccountNumber("0142273992");
        request.setSourceAccountNumber("3020202211");
        request.setNarration("Test insufficient");
        Transaction transaction = transactionService.processTransfer(request);

        assertEquals(Status.INSUFFICIENT_FUND, transaction.getStatus(), "Transaction status should be FAILED due to insufficient funds");
    }
}
