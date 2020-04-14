package database.models;

import bootcamp.core.Assignment;
import database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AssignmentData extends Assignment {
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
    public AssignmentData(String title, String description, LocalDate subDateTime, int grade) {
        super(title, description, subDateTime, grade);
    }
    public AssignmentData(int id, String title, String description, LocalDate subDateTime, int grade) {
        super(title, description, subDateTime, grade);
        this.id = id;
    }
    
    /* Methods */
    public boolean insertRecordToAssignments(Database db){
         String sql = new StringBuilder()
                        .append("INSERT INTO `assignments`(`title`, `description`, `due_date`, `passing_grade`)")
                        .append("VALUES(?, ?, ?, ?);").toString();
        try {
            // Execute query
            db.setPreparedStatementWithKeys(sql);
            PreparedStatement pst =  db.getPreparedStatement();
            pst.setString(1, getTitle());
            pst.setString(2, getDescription());
            pst.setString(3, getSubDate().toString());
            pst.setInt(4, getGrade());
            int rowsAffected = pst.executeUpdate();
            //System.out.println(rowsAffected + " rows(s) inserted in table 'assignments'");
            
            // Add ID to local object
            ResultSet rs = pst.getGeneratedKeys();
            rs.last();
            setId(rs.getInt(1));
            //System.out.println("NEW ASSIGNMENT ID: " + getId());
            
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
}
