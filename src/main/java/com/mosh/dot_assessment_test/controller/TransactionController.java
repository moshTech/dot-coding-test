package com.mosh.dot_assessment_test.controller;

import com.mosh.dot_assessment_test.apimodel.TransactionRequest;
import com.mosh.dot_assessment_test.common.CommonUtils;
import com.mosh.dot_assessment_test.enums.Status;
import com.mosh.dot_assessment_test.model.Transaction;
import com.mosh.dot_assessment_test.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author mosh
 * @role software engineer
 * @createdOn 06 Fri Sep, 2024
 */

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<Object> transfer(@RequestBody TransactionRequest request) {
        Transaction transaction = transactionService.processTransfer(request);
        return CommonUtils.buildSuccessResponse(transaction,"Transfer successful");
    }

    @GetMapping
    public ResponseEntity<Object> getTransactions(
            @RequestParam(defaultValue = "SUCCESSFUL", required = false) Status status,
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0",required = false) int page,
            @RequestParam(defaultValue = "20",required = false) int pageSize) {
        Page<Transaction> transactions = transactionService.getTransactions(status, accountNumber, startDate, endDate,page,pageSize);
        return CommonUtils.buildSuccessResponse(transactions);
    }

    @GetMapping("/summary")
    public ResponseEntity<Object> getSummary(@RequestParam  String startDate) {

        return CommonUtils.buildSuccessResponse(transactionService.generateTransactionSummary( startDate));
    }
}
