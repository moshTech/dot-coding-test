package com.mosh.dot_assessment_test.background;

import com.mosh.dot_assessment_test.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author mosh
 * @role software engineer
 * @createdOn 06 Fri Sep, 2024
 */
@Component
@RequiredArgsConstructor
public class BackgroundTasks {
    private final TransactionService transactionService;
    @Scheduled(cron = "0 0 2 * * ?")
    public void analyzeTransactions() {
        transactionService.analyzeTransactionsForCommission();
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void generateDailySummary() {
        transactionService.generateDailyTransactionSummary();
    }
}
