package com.mosh.dot_assessment_test.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

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

    public static LocalDateTime parseDateTime(String dateTimeString) {
        List<DateTimeFormatter> formatters = Arrays.asList(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy")
        );

        for (DateTimeFormatter formatter : formatters) {
            System.out.println(formatter.toString());
            try {
                // Handle formats with only date
                if (formatter.toString().contains("yyyy-MM-dd") || formatter.toString().contains("dd-MM-yyyy")) {
                    LocalDate localDate = LocalDate.parse(dateTimeString, formatter);
                    return localDate.atStartOfDay();
                } else {
                    return LocalDateTime.parse(dateTimeString, formatter);
                }
            } catch (DateTimeParseException e) {
                // Continue to the next formatter
            }
        }

        throw new IllegalArgumentException("Invalid date-time format: " + dateTimeString);
    }

}
