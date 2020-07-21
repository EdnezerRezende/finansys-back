package br.com.finansys.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataUtil {
    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate dataStringForLocalDate(final String data) {

        return LocalDate.parse(data, formatter);
    }

    public static String dataLocalDateForString(final LocalDate data) {
        return data.format(formatter).toString();
    }
}