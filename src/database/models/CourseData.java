/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.models;

import bootcamp.core.Course;
import database.Database;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 *
 * @author orestis
 */
public class CourseData extends Course {
    /* Fields */
    private int id;

    /* Properties */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    /* Constructor */
    public CourseData(String title, String stream, String type, LocalDate start_date, LocalDate end_date) {
        super(title, stream, type, start_date, end_date);
    } 

    public CourseData(int id, String title, String stream, String type, LocalDate start_date, LocalDate end_date) {
        super(title, stream, type, start_date, end_date);
        this.id = id;
    }
    
    /* Methods */
    public boolean insertRecordToCourses(Database db){
        String sql = new StringBuilder()
                        .append("INSERT INTO `courses`(`title`, `stream`, `type`, `start_date`, `end_date`)")
                        .append("VALUES(?, ?, ?, ?, ?);").toString();
        try {
            db.setPreparedStatementWithKeys(sql);
            PreparedStatement pst =  db.getPreparedStatement();
            pst.setString(1, getTitle());
            pst.setString(2, getStream());
            pst.setString(3, getType());
            pst.setString(4, getStartDate().toString());
            pst.setString(5, getEndDate().toString());
            int rowsAffected = pst.executeUpdate();
            System.out.println(rowsAffected + " rows(s) inserted in table 'courses'");
            ResultSet rs = pst.getGeneratedKeys();
            rs.last();
            setId(rs.getInt(1));
            System.out.println("NEW COURSE ID: " + getId());
            return true;
            //System.out.printf("\nCourse \"%s\" was successfuly created\n!", getTitle());
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
            //System.exit(0);
        }
    }
}
