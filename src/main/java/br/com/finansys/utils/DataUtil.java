package br.com.finansys.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataUtil {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate dataStringForLocalDate(final String data) {
        LocalDate dataConvertida;
        try {
            dataConvertida = dataStringForLocalDate(data, "dd/MM/yyyy");
        } catch (DateTimeParseException e) {
            String[] dataSplit = data.split("-");
            dataConvertida = LocalDate.now()
                                .withYear(Integer.parseInt(dataSplit[2]))
                                .withMonth(Integer.parseInt(dataSplit[1]))
                                .withDayOfMonth(Integer.parseInt(dataSplit[0]));
        }

        return dataConvertida;
    }

    public static LocalDate dataStringForLocalDate(final String data, final String formato) {
        formatter = DateTimeFormatter.ofPattern(formato);
        return LocalDate.parse(data, formatter);
    }

    public static String dataLocalDateForString(final LocalDate data) {
        return data.format(formatter).toString();
    }
    
    public static String converterStringLocalDate(final String data) {
    	DateTimeFormatter formatterString = 
    	        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	LocalDateTime date = LocalDateTime.parse(data.toString(), formatterString);
    	return dataLocalDateForString(date.toLocalDate());
    }    
}