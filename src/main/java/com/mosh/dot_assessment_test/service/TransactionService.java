package com.mosh.dot_assessment_test.service;

import com.mosh.dot_assessment_test.apimodel.TransactionRequest;
import com.mosh.dot_assessment_test.enums.Status;
import com.mosh.dot_assessment_test.exception.CustomRuntimeException;
import com.mosh.dot_assessment_test.model.DailyTransactionSummary;
import com.mosh.dot_assessment_test.model.Transaction;
import com.mosh.dot_assessment_test.repository.DailyTransactionSummaryRepository;
import com.mosh.dot_assessment_test.repository.TransactionRepository;
import com.mosh.dot_assessment_test.response.ApiError;
import com.mosh.dot_assessment_test.util.DateConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author mosh
 * @role software engineer
 * @createdOn 06 Fri Sep, 2024
 */

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    private final DailyTransactionSummaryRepository dailyTransactionSummaryRepository;


    @Value("${modeled.account.balance:100000}")
    private double accountBalance;

    public Transaction processTransfer(TransactionRequest request) {

        Transaction transaction = new Transaction();
        try {

            transaction.setTransactionReference(UUID.randomUUID().toString());
            transaction.setAmount(BigDecimal.valueOf(request.getAmount()));
            transaction.setDescription(request.getNarration());
            transaction.setDateCreated(LocalDateTime.now());
            transaction.setSourceAccountNumber(request.getSourceAccountNumber());
            transaction.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());
            transaction.setStatus(Status.PENDING);

            // Calculate the transaction fee (0.5% of amount, capped at 100)
            double transactionFee = Math.min(request.getAmount() * 0.005, 100);
            transaction.setTransactionFee(transactionFee);
            transaction.setBilledAmount(BigDecimal.valueOf(request.getAmount() + transactionFee));

            if(request.getSourceAccountNumber().equalsIgnoreCase(request.getBeneficiaryAccountNumber())){
               throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, new ApiError("Source and Beneficiary account cannot be the same", "400"));
            }

            // Check if the balance is sufficient
            if (!isBalanceSufficient(request.getSourceAccountNumber(), request.getAmount() + transactionFee)) {
                transaction.setStatus(Status.INSUFFICIENT_FUND);
                transaction.setStatusMessage("Insufficient funds");
                return transactionRepository.save(transaction);
            }

            // Mark the transaction as successful
            transaction.setStatus(Status.SUCCESSFUL);
            transaction.setStatusMessage("Transfer completed successfully");
        }catch(CustomRuntimeException e) {
            throw e;
        }catch
         (Exception e) {
            transaction.setStatus(Status.FAILED);
            transaction.setStatusMessage("Error processing transfer: " + e.getMessage());
        }

        return transactionRepository.save(transaction);
    }

    private boolean isBalanceSufficient(String accountNumber, double amount) {
       return  accountBalance >= amount;
    }

    public Page<Transaction> getTransactions(Status status, String accountNumber, LocalDateTime startDate, LocalDateTime endDate, int page, int pageSize) {
        return transactionRepository.findByStatusAndOrSourceAccountNumberAndOrDateCreatedBetween(status, accountNumber, startDate, endDate,  PageRequest.of(page,pageSize));
    }

    public void analyzeTransactionsForCommission() {
        List<Transaction> transactions = transactionRepository.findByDateCreatedBetween(
                LocalDateTime.now().minusDays(1), LocalDateTime.now());

        for (Transaction transaction : transactions) {
            if (transaction.getStatus().equals("SUCCESSFUL") && transaction.getCommissionWorthy() != null && transaction.getCommissionWorthy()) {
                transaction.setCommission(transaction.getTransactionFee() * 0.20 ); // 20/100 = 0.2
                transaction.setCommissionWorthy(true);
                transactionRepository.save(transaction);
            }
        }
    }

    public DailyTransactionSummary generateTransactionSummary(String dateStr) {

        LocalDate date = DateConverterUtil.convertToLocalDate(dateStr);

        LocalDate startDate = date.atStartOfDay().toLocalDate();
        LocalDate endDate = date.plusDays(1).atStartOfDay().toLocalDate();

        List<Transaction> transactions = transactionRepository.findByDateCreatedBetween(
                startDate.atStartOfDay(), endDate.atStartOfDay());

        BigDecimal totalAmount = BigDecimal.ZERO;
        double totalTransactionFee = 0.0;
        double totalCommission = 0.0;

        for (Transaction transaction : transactions) {
            totalAmount = totalAmount.add(transaction.getAmount());
            totalTransactionFee += transaction.getTransactionFee();
            if (transaction.getCommissionWorthy() != null && transaction.getCommissionWorthy()) {
                totalCommission += transaction.getCommission();
            }
        }

        DailyTransactionSummary summary  = new DailyTransactionSummary();
            summary.setDate(startDate);


        summary.setTotalAmount(totalAmount);
        summary.setTotalTransactionFee(BigDecimal.valueOf(totalTransactionFee));
        summary.setTotalCommission(BigDecimal.valueOf(totalCommission));

        return summary;

    }

    public void generateDailyTransactionSummary() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.atStartOfDay().toLocalDate();
        LocalDate endDate = today.plusDays(1).atStartOfDay().toLocalDate();

        List<Transaction> transactions = transactionRepository.findByDateCreatedBetween(
                startDate.atStartOfDay(), endDate.atStartOfDay());

        BigDecimal totalAmount = BigDecimal.ZERO;
        double totalTransactionFee = 0.0;
        double totalCommission = 0.0;

        for (Transaction transaction : transactions) {
            totalAmount = totalAmount.add(transaction.getAmount());
            totalTransactionFee += transaction.getTransactionFee();
            if (transaction.getCommissionWorthy() != null && transaction.getCommissionWorthy()) {
                totalCommission += transaction.getCommission();
            }
        }

        DailyTransactionSummary summary = dailyTransactionSummaryRepository.findByDate(today);
        if (summary == null) {
            summary = new DailyTransactionSummary();
            summary.setDate(today);
        }

        summary.setTotalAmount(totalAmount);
        summary.setTotalTransactionFee(BigDecimal.valueOf(totalTransactionFee));
        summary.setTotalCommission(BigDecimal.valueOf(totalCommission));

        dailyTransactionSummaryRepository.save(summary);
    }
}
