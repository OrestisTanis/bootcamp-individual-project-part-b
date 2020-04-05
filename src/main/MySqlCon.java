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
    
    /* WRITE OPERATIONS */
    public static void createCourse(String in_title, String in_stream, String in_type, String in_start_date, String in_end_date){
        String sql = new StringBuilder()
                        .append("INSERT INTO `courses`(`title`, `stream`, `type`, `start_date`, `end_date`)")
                        .append("VALUES(?, ?, ?, ?, ?);").toString();
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, in_title);
            statement.setString(2, in_stream);
            statement.setString(3, in_type);
            statement.setString(4, in_start_date);
            statement.setString(4, in_end_date);
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows(s) inserted in table 'courses'");
        } catch (SQLException ex) {
            System.out.println("ERROR: CANNOT EXECUTE INSERT STATEMENT");
            System.out.println(ex.toString());
            System.exit(0);
        }
    }
    
    public static void createStudent(String in_first_name, String in_last_name, String in_date_of_birth, String in_tuition_fees){
        String sql = new StringBuilder()
                        .append("INSERT INTO `students`(`first_name`, `last_name`, `date_of_birth`, `tuition_fees`)")
                        .append("VALUES(?, ?, ?, ?);").toString();
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, in_first_name);
            statement.setString(2, in_last_name);
            statement.setString(3, in_date_of_birth);
            statement.setString(4, in_tuition_fees);
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows(s) inserted in table 'students'");
        } catch (SQLException ex) {
            System.out.println("ERROR: CANNOT EXECUTE INSERT STATEMENT");
            System.out.println(ex.toString());
            System.exit(0);
        }
    }
    
    public static void createTrainer(String in_first_name, String in_last_name, String in_subject){
        String sql = new StringBuilder()
                        .append("INSERT INTO `trainers`(`first_name`, `last_name`, `subject`)")
                        .append("VALUES(?, ?, ?);").toString();
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, in_first_name);
            statement.setString(2, in_last_name);
            statement.setString(3, in_subject);
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows(s) inserted in table 'trainers'");
        } catch (SQLException ex) {
            System.out.println("ERROR: CANNOT EXECUTE INSERT STATEMENT");
            System.out.println(ex.toString());
            System.exit(0);
        }
    }
    
    public static void createAssignment(String in_title, String in_description, String in_submission_date, String in_mark_needed){
        String sql = new StringBuilder()
                        .append("INSERT INTO `assignments`(`title`, `description`, `submission_date`, `mark_needed`)")
                        .append("VALUES(?, ?, ?, ?, ?);").toString();
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, in_title);
            statement.setString(2, in_description);
            statement.setString(3, in_submission_date);
            statement.setString(4, in_mark_needed);
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows(s) inserted in table 'assignments'");
        } catch (SQLException ex) {
            System.out.println("ERROR: CANNOT EXECUTE INSERT STATEMENT");
            System.out.println(ex.toString());
            System.exit(0);
        }
    }
    
    /* ENROLLMENTS */
    public static void enrollStudentToCourse(String in_students_id, String in_courses_id){
        String sql = new StringBuilder()
                        .append("INSERT INTO `enrollments_students`(`students_id`, `courses_id`)")
                        .append("VALUES(?, ?);").toString();
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, in_students_id);
            statement.setString(2, in_courses_id);
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows(s) inserted in table 'enrollments_students'");
        } catch (SQLException ex) {
            System.out.println("ERROR: CANNOT EXECUTE INSERT STATEMENT");
            System.out.println(ex.toString());
            System.exit(0);
        }
    }
    
    public static void enrollTrainerToCourse(String in_trainers_id, String in_courses_id){
        String sql = new StringBuilder()
                        .append("INSERT INTO `enrollments_trainers`(`trainers_id`, `courses_id`)")
                        .append("VALUES(?, ?);").toString();
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, in_trainers_id);
            statement.setString(2, in_courses_id);
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows(s) inserted in table 'enrollments_trainers'");
        } catch (SQLException ex) {
            System.out.println("ERROR: CANNOT EXECUTE INSERT STATEMENT");
            System.out.println(ex.toString());
            System.exit(0);
        }
    }
    
    public static void enrollAssignmentToCourse(String in_assignments_id, String in_courses_id){
        String sql = new StringBuilder()
                        .append("INSERT INTO `enrollments_assignments`(`assignments_id`, `courses_id`)")
                        .append("VALUES(?, ?);").toString();
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, in_assignments_id);
            statement.setString(2, in_courses_id);
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows(s) inserted in table 'enrollments_assignments'");
        } catch (SQLException ex) {
            System.out.println("ERROR: CANNOT EXECUTE INSERT STATEMENT");
            System.out.println(ex.toString());
            System.exit(0);
        }
    }
    
    
    /* READ OPERATIONS */
      public static void getListOfAllCourses() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `courses`;");
            while (resultSet.next()) {
                System.out.println("\n****** GETTING LIST OF ALL COURSES *******");
                System.out.println(resultSet.getString(1) + "." + resultSet.getString(2) + " " + resultSet.getString(3) + ", Start Date: " + resultSet.getString(4) + ", End Date: " + resultSet.getString(5));
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
    
    public static void getListOfAllStudents() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `students`;");
            //if(resultSet.first()) return true;
            while (resultSet.next()) {
                System.out.println("\n****** GETTING LIST OF ALL STUDENTS *******");
                System.out.println(resultSet.getString(1) + "." + resultSet.getString(2) + ", Birth Date: " + resultSet.getString(3) + ", Tuition Fees: " + resultSet.getString(4));
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
    
    public static void getListOfAllTrainers() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `trainers`;");
            while (resultSet.next()) {
                System.out.println("\n****** GETTING LIST OF ALL TRAINERS *******");
                System.out.println(resultSet.getString(1) + "." + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4));
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
    
    public static void getListOfAllAssignments() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `assignments`;");
            while (resultSet.next()) {
                System.out.println("\n****** GETTING LIST OF ALL ASSIGNMENTS *******");
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
    
    public static void getListOfStudentsPerCourse(){
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String sql = new StringBuilder()
                        .append("SELECT CONCAT(`courses`.`title`, ' ', `courses`.`stream`, ' ', `courses`.`type`) AS `course_full_name`, GROUP_CONCAT(`students`.`first_name`, ' ', `students`.`last_name`) AS `list_of_students` FROM `students`")
                        .append("JOIN `enrollments_students` on `enrollments_students`.`students_id` = `students`.`id`")
                        .append("JOIN `courses` on `courses`.`id` = `enrollments_students`.`courses_id`")
                        .append("GROUP BY `enrollments_students`.`courses_id`;")
                        .toString();
                    
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("\n****** GETTING LIST OF STUDENTS PER COURSE *******");
                System.out.println(resultSet.getString(1) + " : " + resultSet.getString(2));
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
    
    public static void getListOfTrainersPerCourse(){
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String sql = new StringBuilder()
                        .append("SELECT CONCAT(`courses`.`title`, ' ', `courses`.`stream`, ' ', `courses`.`type`) AS `course_full_name`, GROUP_CONCAT(`trainers`.`first_name`, ' ', `trainers`.`last_name`) FROM `trainers``")
                        .append("JOIN `enrollments_trainers` on `enrollments_trainers`.`trainers_id` = `trainers`.`id`")
                        .append("JOIN `courses` on `courses`.`id` = `enrollments_trainers`.`courses_id`")
                        .append("GROUP BY `enrollments_trainers`.`courses_id`;")
                        .toString();
                    
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("\n****** GETTING LIST OF TRAINERS PER COURSE *******");
                System.out.println(resultSet.getString(1) + " : " + resultSet.getString(2));
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
    
    public static void getListOfAssignmentsPerCourse(){
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String sql = new StringBuilder()
                        .append("SELECT CONCAT(`courses`.`title`, ' ', `courses`.`stream`, ' ', `courses`.`type`) AS `course_full_name`, GROUP_CONCAT(`assignments`.`title`, ' - ', `assignments`.`submission_date`) AS `list_of_assignments` FROM `assignments`")
                        .append("JOIN `enrollments_assignments` on `enrollments_assignments`.`assignments_id` = `assignments`.`id`")
                        .append(" JOIN `courses` on `courses`.`id` = `enrollments_assignments`.`courses_id`")
                        .append(" GROUP BY `enrollments_assignments`.`courses_id`;")
                        .toString();
                    
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("\n****** GETTING LIST OF ASSIGNMENTS PER COURSE *******");
                System.out.println(resultSet.getString(1) + " : " + resultSet.getString(2));
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
    
    public static void getListOfAssignmentsPerCoursePerStudent(){
        String sql = new StringBuilder()
                        .append("CREATE TEMPORARY TABLE `assignments_per_courses`(")
                        .append("	`courses_id` INT,")
                        .append("       `courses_fullname` VARCHAR(135),")
                        .append("`list_of_assignments` TEXT")
                        .append(");")
                        .append("INSERT INTO `assignments_per_courses`")
                        .append("SELECT `courses`.`id`, CONCAT(`courses`.`title`, ' ', `courses`.`stream`, ' ', `courses`.`type`), GROUP_CONCAT(`assignments`.`title`, ' - ', `assignments`.`submission_date`) AS `list_of_assignments` FROM `assignments`")
                        .append("JOIN `enrollments_assignments` on `enrollments_assignments`.`assignments_id` = `assignments`.`id`")
                        .append("JOIN `courses` on `courses`.`id` = `enrollments_assignments`.`courses_id`")
                        .append("GROUP BY `enrollments_assignments`.`courses_id`;")
                        .toString();
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int rowsAffected = statement.executeUpdate(sql);
            System.out.println(rowsAffected + " rows(s) inserted in the temporary table 'assignments_per_courses'");
        } catch (SQLException ex) {
            System.out.println("ERROR: CANNOT CREATE OR INSERT INTO TEMPORARY TABLE assignments_per_courses");
            System.out.println(ex.toString());
            System.exit(0);
        }
        
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            sql = new StringBuilder()
                        .append("SELECT CONCAT(`students`.`first_name`, ' ', `students`.`last_name`) AS `student_name`,`assignments_per_courses`.`courses_fullname`, `assignments_per_courses`.`list_of_assignments` FROM `students`")
                        .append("JOIN `enrollments_students` on `enrollments_students`.`students_id` = `students`.`id`")
                        .append("JOIN `courses` on `courses`.`id` = `enrollments_students`.`courses_id`")
                        .append("JOIN `assignments_per_courses` on`courses`.`id` = `assignments_per_courses`.`courses_id`")
                        .append("ORDER BY `students`.`last_name`;")
                        .toString();
                    
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("\n****** GETTING LIST OF ASSIGNMENTS PER COURSE PER STUDENT *******");
                System.out.println(resultSet.getString(1) + ", Course:  " + resultSet.getString(2)+ ", List of Assignments: " + resultSet.getString(2));
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
        
        // MPOREI NA THELEI RESULTSET K STATEMENT -> BLOCK SCOPED
        // DROP TEMPORARY TABLE
        sql = new StringBuilder()
                        .append("DROP TEMPORARY TABLE `assignments_per_courses`;")
                        .toString();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("SUCCESSFULLY DELETED TEMPORARY TABLE 'assignments_per_courses'");
        } catch (SQLException ex) {
            System.out.println("ERROR: CANNOT CREATE OR INSERT INTO TEMPORARY TABLE assignments_per_courses");
            System.out.println(ex.toString());
            System.exit(0);
        }
        finally {
            try {
                statement.close();
            } catch (SQLException ex) {
            }
        }
    }
    
    
    public static void getListOfStudentsBelongingToMoreThanOneCourse(){
    
    }
    
    
    
    
    
}
