package database.models;

import bootcamp.core.Trainer;
import database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainerData extends Trainer {
    /* Fields */
    private int id;
    
    /* Constructor */
    public TrainerData(String firstName, String lastName, String subject){
        super(firstName, lastName, subject);
    }
    public TrainerData(int id, String firstName, String lastName, String subject) {
        super(firstName, lastName, subject);
        this.id = id;
    }

    /* Properties */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    /* Methods */
    public boolean insertRecordToTrainers(Database db){
        String sql = new StringBuilder()
                        .append("INSERT INTO `trainers`(`first_name`, `last_name`, `subject`)")
                        .append("VALUES(?, ?, ?);").toString();
        try {
            // Execute query
            db.setPreparedStatementWithKeys(sql);
            PreparedStatement pst =  db.getPreparedStatement();
            pst.setString(1, getFirstName());
            pst.setString(2, getLastName());
            pst.setString(3, getSubject());
            int rowsAffected = pst.executeUpdate();
            System.out.println(rowsAffected + " rows(s) inserted in table 'trainers'");
            
            // Add ID to local object
            ResultSet rs = pst.getGeneratedKeys();
            rs.last();
            setId(rs.getInt(1)); 
            System.out.println("NEW TRAINER ID: " + getId());
            
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
}
