package votacao.backend.utils;

import org.springframework.http.HttpStatus;
import votacao.backend.exceptions.CustomException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateConverter {

    public static LocalDateTime stringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        try {
            if(dateString.equals(""))
                return null;
            return LocalDateTime.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            throw new CustomException("Erro ao converter a data.", HttpStatus.CONFLICT);
        }
    }
}
