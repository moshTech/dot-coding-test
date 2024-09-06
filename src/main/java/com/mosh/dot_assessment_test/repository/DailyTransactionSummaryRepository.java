package com.mosh.dot_assessment_test.repository;

import com.mosh.dot_assessment_test.model.DailyTransactionSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * @author mosh
 * @role software engineer
 * @createdOn 06 Fri Sep, 2024
 */
@Repository
public interface DailyTransactionSummaryRepository extends JpaRepository<DailyTransactionSummary, Long> {

    DailyTransactionSummary findByDate(LocalDate date);
}
