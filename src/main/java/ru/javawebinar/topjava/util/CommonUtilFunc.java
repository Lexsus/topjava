package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtilFunc {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss");

    public static String getFormatDateTime(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }
}
