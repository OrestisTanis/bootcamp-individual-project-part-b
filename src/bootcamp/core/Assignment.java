package bootcamp.core;

import java.time.LocalDate;
import java.util.Objects;
import interfaces.DateFormatable;

public class Assignment implements DateFormatable {
    /* Fields */
    private String title;
    private String description;
    private LocalDate subDateTime;
    private int oralMark;
    private int totalMark;
    
    /* Constructor */ 
    public Assignment(String title, String description, LocalDate subDateTime, int totalMark){
        this.title = title;
        this.description = description;
        this.subDateTime = subDateTime;
        //this.oralMark = oralMark;
        this.totalMark = totalMark;        
    }
    
    /* Accessor Properties */
    public String getTitle(){
        return title;
    }    
    public void setTitle(String title){
        this.title = title;
    }    
    
    public String getDescription(){
        return description;
    }    
    public void setDescription(String description){
        this.description = description;
    }
    
    public LocalDate getSubDateTime(){
        return subDateTime;
    }
    public void setSubDateTime(LocalDate subDateTime){
        this.subDateTime = subDateTime;
    }
    
    public int getOralMark(){
        return oralMark;
    }
    public void setOralMark(int oralMark){
        this.oralMark = oralMark;
    }
    
    public int getTotalMark(){
        return totalMark;
    }
    public void setTotalMark(int totalMark){
        this.totalMark = totalMark;
    }

    /* Methods */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.title);
//        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + Objects.hashCode(this.subDateTime);
        //hash = 79 * hash + this.oralMark;
        hash = 79 * hash + this.totalMark;
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
        final Assignment other = (Assignment) obj;
//        if (this.oralMark != other.oralMark) {
//            return false;
//        }
        if (this.totalMark != other.totalMark) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
//        if (!Objects.equals(this.description, other.description)) {
//            return false;
//        }
        if (!Objects.equals(this.subDateTime, other.subDateTime)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Title: %s, Desc.: %s, Sub.Date: %s %s, Mark Needed: %d", title, description, subDateTime.getDayOfWeek().toString().substring(0, 3) ,subDateTime.format(formatter), totalMark);
    }
    
}
