package database.models;

import bootcamp.core.Student;
import database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class StudentData extends Student {
    /* Fields */
    private int id;

    /* Properties */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    /* Constructors */
    public StudentData(String firstName, String lastName, LocalDate dateOfBirth, Double tuitionFees){
        super(firstName, lastName, dateOfBirth, tuitionFees);
    }
    public StudentData(int id, String firstName, String lastName, LocalDate dateOfBirth, Double tuitionFees) {
        super(firstName, lastName, dateOfBirth, tuitionFees);
        this.id = id;
    }
    
    /* Methods */
    public boolean insertRecordToStudents(Database db){
        String sql = new StringBuilder()
                        .append("INSERT INTO `students`(`first_name`, `last_name`, `date_of_birth`, `tuition_fees`)")
                        .append("VALUES(?, ?, ?, ?);").toString();
        try {
            // Execute query
            db.setPreparedStatementWithKeys(sql);
            PreparedStatement pst =  db.getPreparedStatement();
            pst.setString(1, getFirstName());
            pst.setString(2, getLastName());
            pst.setString(3, getDateOfBirth().toString());
            pst.setString(4, getFees().toString());
            int rowsAffected = pst.executeUpdate();
            //System.out.println(rowsAffected + " rows(s) inserted in table 'students'");
            
            // Add ID to local object
            ResultSet rs = pst.getGeneratedKeys();
            rs.last();
            setId(rs.getInt(1));  
            //System.out.println("NEW STUDENT ID: " + getId());
            
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
}
