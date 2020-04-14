package bootcamp.core;

import java.time.LocalDate;
import java.util.Objects;
import interfaces.DateFormatable;

public class Assignment implements DateFormatable {
    /* Fields */
    private String title;
    private String description;
    private LocalDate subDate;
    private int grade;
    
    /* Constructors */
    public Assignment() {
    }
    public Assignment(String title, String description, LocalDate subDateTime, int grade){
        this.title = title;
        this.description = description;
        this.subDate = subDateTime;
        //this.oralMark = oralMark;
        this.grade = grade;        
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
    
    public LocalDate getSubDate(){
        return subDate;
    }
    public void setSubDate(LocalDate subDate){
        this.subDate = subDate;
    }
    
    public int getGrade(){
        return grade;
    }
    public void setGrade(int grade){
        this.grade = grade;
    }

    /* Methods */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.title);
//        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + Objects.hashCode(this.subDate);
        //hash = 79 * hash + this.oralMark;
        hash = 79 * hash + this.grade;
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
//        if (this.grade != other.grade) {
//            return false;
//        }
        if (this.grade != other.grade) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
//        if (!Objects.equals(this.description, other.description)) {
//            return false;
//        }
        if (!Objects.equals(this.subDate, other.subDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Title: %s, Desc.: %s, Due Date: %s %s, Base Grade: %d", title, description, subDate.getDayOfWeek().toString().substring(0, 3) ,subDate.format(formatter), grade);
    }
    
}
