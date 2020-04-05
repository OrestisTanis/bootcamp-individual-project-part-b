package interfaces;

import java.time.format.DateTimeFormatter;

public interface DateFormatable {
    String dateFormatStr = "dd/MM/yyyy";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatStr);
}
