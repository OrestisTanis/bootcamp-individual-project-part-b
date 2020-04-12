package bootcamp.core;

import java.time.LocalDate;
import java.util.Objects;
import interfaces.DateFormatable;

public class Course implements DateFormatable {
    /* Fields */
    private String title;
    private String stream;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    
    /* Constructor */
    public Course(){
    }
    public Course(String title, String stream, String type) {
        this.title = title;
        this.stream = stream;
        this.type = type;
    }
    public Course(String title, String stream, String type, LocalDate start_date, LocalDate end_date) {
        this.title = title;
        this.stream = stream;
        this.type = type;
        this.startDate = start_date;
        this.endDate = end_date;
    }
    
    /* Accessor Properties */
    public String getTitle(){
        return title;
    }    
    public void setTitle(String title){
        this.title = title;
    }    
    
    public String getStream(){
        return stream;
    }    
    public void setStream(String stream){
        this.stream = stream;
    }
    
    public String getType(){
        return type;
    }    
    public void setType(String type){
        this.type = type;
    }
    
    public LocalDate getStartDate(){
        return startDate;
    }    
    public void setStartDate(LocalDate start_date){
        this.startDate = start_date;
    }
    
    public LocalDate getEndDate(){
        return endDate;
    }    
    public void setEndDate(LocalDate end_date){
        this.endDate = end_date;
    }

    /* Methods */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.title);
        hash = 61 * hash + Objects.hashCode(this.stream);
        hash = 61 * hash + Objects.hashCode(this.type);
//        hash = 61 * hash + Objects.hashCode(this.startDate);
//        hash = 61 * hash + Objects.hashCode(this.endDate);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Course other = (Course) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.stream, other.stream)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
//        if (!Objects.equals(this.startDate, other.startDate)) {
//            return false;
//        }
//        if (!Objects.equals(this.endDate, other.endDate)) {
//            return false;
//        }
        return true;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Stream: " + stream + ", Type:  " + type + ", Start. date: " + startDate.format(formatter) + ", End date: " + endDate.format(formatter);
    }

    
    
}
