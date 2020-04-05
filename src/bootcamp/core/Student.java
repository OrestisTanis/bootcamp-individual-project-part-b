package bootcamp.core;

import java.time.LocalDate;
import java.util.Objects;
import interfaces.DateFormatable;

public class Student implements DateFormatable {
    /* Fields */
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Double tuitionFees;
    
    /* Constructor */
    public Student(String firstName, String lastName, LocalDate dateOfBirth, Double tuitionFees){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.tuitionFees = tuitionFees;
    }
    
    /* Accessor Properties */
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public LocalDate getDateOfBirth(){
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }
    
    public Double getFees(){
        return tuitionFees;
    }
    public void setTFees(Double tuitionFees){
        this.tuitionFees = tuitionFees;
    }

    /* Methods */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.firstName);
        hash = 23 * hash + Objects.hashCode(this.lastName);
        hash = 23 * hash + Objects.hashCode(this.dateOfBirth);
//        hash = 23 * hash + Objects.hashCode(this.tuitionFees);
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
        final Student other = (Student) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.dateOfBirth, other.dateOfBirth)) {
            return false;
        }
//        if (!Objects.equals(this.tuitionFees, other.tuitionFees)) {
//            return false;
//        }
        return true;
    }

    @Override
    public String toString() {
        //return firstName + " " + lastName + ", Birth Date: " + dateOfBirth.format(formatter) + "";
        return String.format("%s, DOB: %s, TF: %.2f€", getFullName(), dateOfBirth.format(formatter), tuitionFees);
    }

    public String getFullName(){
        return String.format(this.firstName + " " + this.lastName);
    }
    
}
