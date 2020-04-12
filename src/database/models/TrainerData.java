/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.models;

import bootcamp.core.Trainer;
import database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author orestis
 */
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
            db.setPreparedStatementWithKeys(sql);
            PreparedStatement pst =  db.getPreparedStatement();
            pst.setString(1, getFirstName());
            pst.setString(2, getLastName());
            pst.setString(3, getSubject());
            int rowsAffected = pst.executeUpdate();// Add ID to local object
            System.out.println(rowsAffected + " rows(s) inserted in table 'trainers'");
            ResultSet rs = pst.getGeneratedKeys();
            rs.last();
            setId(rs.getInt(1));
            System.out.println("NEW TRAINER ID: " + getId());
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
//            System.exit(0);
            return false;
        }
    }
}
