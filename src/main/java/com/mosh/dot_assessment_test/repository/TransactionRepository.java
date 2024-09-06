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

    @Query("SELECT t FROM Transaction t WHERE " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:sourceAccountNumber IS NULL OR t.sourceAccountNumber = :sourceAccountNumber) AND " +
            "(:startDate IS NULL OR :endDate IS NULL OR t.dateCreated BETWEEN :startDate AND :endDate)")
    Page<Transaction> findByStatusAndOrSourceAccountNumberAndOrDateCreatedBetween(
            @Param("status") Status status,
            @Param("sourceAccountNumber") String sourceAccountNumber,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate, Pageable pageable);

    List<Transaction> findByDateCreatedBetween(LocalDateTime startDate, LocalDateTime endDate);
}
