package com.starter.procedures.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DateFormatter {

    private static final Locale LOCALE_BR = new Locale("pt", "BR");

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm", LOCALE_BR);

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy", LOCALE_BR);

    private static final DateTimeFormatter DATE_TIME_FULL_FORMATTER =
            DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy 'às' HH:mm", LOCALE_BR);

    private DateFormatter() {
    }

    public static String format(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : null;
    }

    public static String format(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : null;
    }

    public static String formatFull(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_TIME_FULL_FORMATTER) : null;
    }
}