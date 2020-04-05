package bootcamp.creators;

import java.time.format.DateTimeFormatter;
import interfaces.DateFormatable;

public abstract class Creator implements DateFormatable {
    protected String nameInvalidMsg;
    protected String nameRegex;
    protected String titleInvalidMsg;
    protected String titleRegex;
    protected String dateInvalidMsg;
    
    public Creator() {
        nameInvalidMsg = "Only letters are allowed. Please enter a new value: ";
        nameRegex = "[a-zA-Z]+(\\s+[a-zA-Z]+)*";
        titleInvalidMsg = "Only letters, numbers, dashes, underscores and sharps are allowed. Please enter a new value: ";
        titleRegex = "[\\w\\s\\_#\\-]+(\\s+[\\w\\s\\_#\\-]+)*";
    }
    protected DateTimeFormatter getFormatter() {
        return formatter;
    }
    protected String getDateFormat() {
        return dateFormatStr;
    }
    protected String getInvalidDateBetweenMsg(String minDateStr, String maxDateStr){
        return String.format("Invalid date. Enter a valid date between %s and %s (%s): ", minDateStr, maxDateStr, dateFormatStr);
    }
    
}
