/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.models;

import bootcamp.core.Student;
import database.Database;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author orestis
 */
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
            db.setPreparedStatementWithKeys(sql);
            PreparedStatement pst =  db.getPreparedStatement();
            pst.setString(1, getFirstName());
            pst.setString(2, getLastName());
            pst.setString(3, getDateOfBirth().toString());
            pst.setString(4, getFees().toString());
            int rowsAffected = pst.executeUpdate();
            System.out.println(rowsAffected + " rows(s) inserted in table 'students'");
            // Add ID to local object
            ResultSet rs = pst.getGeneratedKeys();
            rs.last();
            setId(rs.getInt(1));
            System.out.println("NEW STUDENT ID: " + getId());
            return true;
            //System.out.printf("\nStudent \"%s %s\" was successfuly created!", getFirstName(), getLastName());
        } catch (SQLException ex) {
//            System.out.printf("ERROR: Cannot create student.\n" +
//                              "Reason: A student with firstname \"%s\", lastname \"%s\", and birth date \"%s\" already exists!\n",  
//                               getFirstName(), getLastName(), getDateOfBirth());
            System.out.println(ex.toString());
            return false;
            //System.exit(0);
        }
    }
}
