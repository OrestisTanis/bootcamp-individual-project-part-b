/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author orestis
 */
public class MySqlCon {
    private static final String DB_URL = "localhost:3306";
    private static final String FULL_DB_URL = "jdbc:mysql://" + DB_URL + "/bootcamp?zeroDateTimeBehavior=CONVERT_TO_NULL&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWD = "1a2b3c4d5f6g";
    private static Connection connection;
    
    
    public static void createConnection(){
        try {
            connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWD);
        } catch (SQLException ex) {
            System.out.println("Sorry, problems with the database connection!");
            System.out.println(ex.toString());
        }
    }
    
    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("ERROR when closing connection.");
            System.out.println(ex.toString());
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void getListOfStudents() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `students`;");
            //if(resultSet.first()) return true;
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + "." + resultSet.getString(2) + " " + resultSet.getString(3));
            }
        } catch (SQLException ex) {
            System.out.println("Sorry, problems with the database connection!");
            System.out.println(ex.toString());
            System.exit(0);
        } finally {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException ex) {
            }
        }
    }
    
    public static void insertIntoStudents(String in_first_name, String in_last_name, String in_date_of_birth, String in_tuition_fees){
        String sql = new StringBuilder()
                        .append("INSERT INTO `students`(`first_name`, `last_name`, `date_of_birth`, `tuition_fees`)")
                        .append("VALUES(?, ?, ?, ?);").toString();
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, in_first_name);
            statement.setString(2, in_last_name);
            statement.setString(3, in_date_of_birth);
            statement.setString(4, in_tuition_fees);
           int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows(s) inserted");
        } catch (SQLException ex) {
            System.out.println("ERROR: CANNOT EXECUTE INSERT STATEMENT");
            System.out.println(ex.toString());
            System.exit(0);
        }
     
    }

}
