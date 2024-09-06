package com.mosh.dot_assessment_test.repository;

import com.mosh.dot_assessment_test.enums.Status;
import com.mosh.dot_assessment_test.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author mosh
 * @role software engineer
 * @createdOn 06 Fri Sep, 2024
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value= "SELECT * FROM transactions WHERE " +
            "(:status IS NULL OR status = :status) AND " +
            "(:sourceAccountNumber IS NULL OR source_account_number = :sourceAccountNumber) AND " +
            "(:beneficiaryAccountNumber IS NULL OR beneficiary_account_number = :beneficiaryAccountNumber) AND " +
            "(:startDate IS NULL OR :endDate IS NULL OR date_created BETWEEN :startDate AND :endDate)",
            nativeQuery = true)
    Page<Transaction> findByStatusAndOrSourceAccountNumberAndOrDateCreatedBetween(
            @Param("status") String status,
            @Param("sourceAccountNumber") String sourceAccountNumber,
            @Param("beneficiaryAccountNumber") String beneficiaryAccountNumber,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            Pageable pageable);

    List<Transaction> findByDateCreatedBetween(LocalDateTime startDate, LocalDateTime endDate);
}
