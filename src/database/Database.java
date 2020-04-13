package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static final String DB_URL = "localhost:3306";
    private static final String FULL_DB_URL = "jdbc:mysql://" + DB_URL + "/bootcamp?zeroDateTimeBehavior=CONVERT_TO_NULL&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWD = "1a2b3c4d5f6g";
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    
    public Database() {
        getConnection();
    }
    
    public static Connection getConnection(){
        try {
            return connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWD);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
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
    
    public static void setStatement(){
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Statement getStatement(){
        return statement;
    }
    
    public static void setPreparedStatement(String sql){
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void setPreparedStatementWithKeys(String sql){
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static PreparedStatement getPreparedStatement(){
        return  preparedStatement;
    }
    
    public static ResultSet getResults(String sql){
        try {
            setStatement();
            return statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.print("ERROR: Cannot get data.\n" +
                              "Reason: There was an error communicating with the database.\n");
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static ResultSet getOneResultByID(String tableName, int id){
        try {
            setStatement();
            String sql = String.format("SELECT * FROM `%s` WHERE `id` = '%s';", tableName, id);
            return statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.print("ERROR: Cannot get data.\n" +
                              "Reason: There was an error communicating with the database.\n");
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static ResultSet getOneResultByField(String tableName, String fieldName, String fieldValue){
        try {
            setStatement();
            String sql = String.format("SELECT * FROM `%s` WHERE `%s` = '%s';", tableName, fieldName, fieldValue);
            return statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.print("ERROR: Cannot get data.\n" +
                              "Reason: There was an error communicating with the database.\n");
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static ResultSet getAllResultsByTablename(String tableName){
        try {
            setStatement();
            String sql = String.format("SELECT * FROM `%s`;", tableName);
            return statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.print("ERROR: Cannot get data.\n" +
                              "Reason: There was an error communicating with the database.\n");
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static boolean tableIsEmpty(String tableName){
        try {
            setStatement();
            String sql = String.format("SELECT COUNT(1) FROM `%s`;", tableName);
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            if (resultSet.getInt(1) == 0){
                return true;
            }
            return false;
        } catch (SQLException ex) {
            System.out.println("ERROR: Cannot retrieve data from database.");
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        }
    }
}
