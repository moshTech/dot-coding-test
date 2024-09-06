package com.mosh.dot_assessment_test.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author mosh
 * @role software engineer
 * @createdOn 06 Fri Sep, 2024
 */
public class DateConverterUtil {

    // Define an array of possible date formats
    private static final DateTimeFormatter[] FORMATTERS = {
            DateTimeFormatter.ISO_LOCAL_DATE,
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
    };

    public static LocalDate convertToLocalDate(String dateString) {
        LocalDate date = null;
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                date = LocalDate.parse(dateString, formatter);
                break;
            } catch (DateTimeParseException e) {
               //
            }
        }
        return date;
    }

}
