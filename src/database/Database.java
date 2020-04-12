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
    
    // Extra methods for printing concatenated data, not used
//    public static void getListOfStudentsPerCourse(){
//        Statement statement = null;
//        ResultSet resultSet = null;
//        try {
//            statement = connection.createStatement();
//            String sql = new StringBuilder()
//                        .append("SELECT CONCAT(`courses`.`title`, ' ', `courses`.`stream`, ' ', `courses`.`type`) AS `course_full_name`, GROUP_CONCAT(`students`.`first_name`, ' ', `students`.`last_name`) AS `list_of_students` FROM `students`")
//                        .append("JOIN `enrollments_students` on `enrollments_students`.`students_id` = `students`.`id`")
//                        .append("JOIN `courses` on `courses`.`id` = `enrollments_students`.`courses_id`")
//                        .append("GROUP BY `enrollments_students`.`courses_id`;")
//                        .toString();
//                    
//            resultSet = statement.executeQuery(sql);
//            System.out.println("\n****** GETTING LIST OF STUDENTS PER COURSE *******");
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString(1) + " : " + resultSet.getString(2));
//            }
//        } catch (SQLException ex) {
//            System.out.println("ERROR: CANNOT GET LIST OF STUDENTS PER COURSE");
//            System.out.println(ex.toString());
//            System.exit(0);
//        } finally {
//            try {
//                resultSet.close();
//                statement.close();
//            } catch (SQLException ex) {
//            }
//        }
//    }
//    
//    public static void getListOfTrainersPerCourse(){
//        Statement statement = null;
//        ResultSet resultSet = null;
//        try {
//            statement = connection.createStatement();
//            String sql = new StringBuilder()
//                        .append("SELECT CONCAT(`courses`.`title`, ' ', `courses`.`stream`, ' ', `courses`.`type`) AS `course_full_name`, GROUP_CONCAT(`trainers`.`first_name`, ' ', `trainers`.`last_name`) FROM `trainers`")
//                        .append("JOIN `enrollments_trainers` on `enrollments_trainers`.`trainers_id` = `trainers`.`id`")
//                        .append("JOIN `courses` on `courses`.`id` = `enrollments_trainers`.`courses_id`")
//                        .append("GROUP BY `enrollments_trainers`.`courses_id`;")
//                        .toString();
//                    
//            resultSet = statement.executeQuery(sql);
//            System.out.println("\n****** GETTING LIST OF TRAINERS PER COURSE *******");
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString(1) + " : " + resultSet.getString(2));
//            }
//        } catch (SQLException ex) {
//            System.out.println("ERROR: CANNOT GET LIST OF TRAINERS PER COURSE");
//            System.out.println(ex.toString());
//            System.exit(0);
//        } finally {
//            try {
//                resultSet.close();
//                statement.close();
//            } catch (SQLException ex) {
//            }
//        }
//    }
//    
//    public static void getListOfAssignmentsPerCourse(){
//        Statement statement = null;
//        ResultSet resultSet = null;
//        try {
//            statement = connection.createStatement();
//            String sql = new StringBuilder()
//                        .append("SELECT CONCAT(`courses`.`title`, ' ', `courses`.`stream`, ' ', `courses`.`type`) AS `course_full_name`, GROUP_CONCAT(`assignments`.`title`, ' - ', `assignments`.`submission_date`) AS `list_of_assignments` FROM `assignments`")
//                        .append("JOIN `enrollments_assignments` on `enrollments_assignments`.`assignments_id` = `assignments`.`id`")
//                        .append(" JOIN `courses` on `courses`.`id` = `enrollments_assignments`.`courses_id`")
//                        .append(" GROUP BY `enrollments_assignments`.`courses_id`;")
//                        .toString();
//                    
//            resultSet = statement.executeQuery(sql);
//            System.out.println("\n****** GETTING LIST OF ASSIGNMENTS PER COURSE *******");
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString(1) + " : " + resultSet.getString(2));
//            }
//        } catch (SQLException ex) {
//            System.out.println("ERROR: CANNOT GET LIST OF ASSIGNMENTS PER COURSE");
//            System.out.println(ex.toString());
//            System.exit(0);
//        } finally {
//            try {
//                resultSet.close();
//                statement.close();
//            } catch (SQLException ex) {
//            }
//        }
//    }
//    
//    public static void getListOfAssignmentsPerCoursePerStudent(){
//        String sql;
//        Statement statement = null;
//        try {
//            statement = connection.createStatement();
//            sql = new StringBuilder()
//                        .append("CREATE TEMPORARY TABLE `assignments_per_courses`(")
//                        .append("`courses_id` INT,")
//                        .append("`courses_fullname` VARCHAR(135),")
//                        .append("`list_of_assignments` TEXT,")
//                        .append("PRIMARY KEY(`courses_id`)")
//                        .append(");").toString();
//            statement.executeUpdate(sql);
//            sql = new StringBuilder()
//                        .append("INSERT INTO `assignments_per_courses`")
//                        .append("SELECT `courses`.`id`, CONCAT(`courses`.`title`, ' ', `courses`.`stream`, ' ', `courses`.`type`), GROUP_CONCAT(`assignments`.`title`, ' - ', `assignments`.`submission_date`) AS `list_of_assignments` FROM `assignments`")
//                        .append("JOIN `enrollments_assignments` on `enrollments_assignments`.`assignments_id` = `assignments`.`id`")
//                        .append("JOIN `courses` on `courses`.`id` = `enrollments_assignments`.`courses_id`")
//                        .append("GROUP BY `enrollments_assignments`.`courses_id`;")
//                        .toString();
//            int rowsAffected = statement.executeUpdate(sql);
//            System.out.println(rowsAffected + " rows(s) inserted in the temporary table 'assignments_per_courses'");
//        } catch (SQLException ex) {
//            System.out.println("ERROR: CANNOT CREATE OR INSERT INTO TEMPORARY TABLE assignments_per_courses");
//            System.out.println(ex.toString());
//            System.exit(0);
//        }
//        
//        ResultSet resultSet = null;
//        try {
//            statement = connection.createStatement();
//            sql = new StringBuilder()
//                        .append("SELECT CONCAT(`students`.`first_name`, ' ', `students`.`last_name`) AS `student_name`,`assignments_per_courses`.`courses_fullname`, `assignments_per_courses`.`list_of_assignments` FROM `students`")
//                        .append("JOIN `enrollments_students` on `enrollments_students`.`students_id` = `students`.`id`")
//                        .append("JOIN `courses` on `courses`.`id` = `enrollments_students`.`courses_id`")
//                        .append("JOIN `assignments_per_courses` on`courses`.`id` = `assignments_per_courses`.`courses_id`")
//                        .append("ORDER BY `students`.`last_name`;")
//                        .toString();
//                    
//            resultSet = statement.executeQuery(sql);
//            System.out.println("\n****** GETTING LIST OF ASSIGNMENTS PER COURSE PER STUDENT *******");
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString(1) + ", Course:  " + resultSet.getString(2)+ ", List of Assignments: " + resultSet.getString(2));
//            }
//        } catch (SQLException ex) {
//            System.out.println("ERROR: CANNOT GET LIST OF ASSIGNMENTS PER COURSE PER STUDENT");
//            System.out.println(ex.toString());
//            System.exit(0);
//        } finally {
//            try {
//                resultSet.close();
//               // statement.close();
//            } catch (SQLException ex) {
//            }
//        }
//       
//        // DROP TEMPORARY TABLE
//        sql = new StringBuilder()
//                        .append("DROP TEMPORARY TABLE `assignments_per_courses`;")
//                        .toString();
//        try {
//            statement = connection.createStatement();
//            statement.executeUpdate(sql);
//            System.out.println("SUCCESSFULLY DROPPED TEMPORARY TABLE 'assignments_per_courses'");
//        } catch (SQLException ex) {
//            System.out.println("ERROR: CANNOT DROP TEMPORARY TABLE assignments_per_courses");
//            System.out.println(ex.toString());
//            System.exit(0);
//        }
//        finally {
//            try {
//                statement.close();
//            } catch (SQLException ex) {
//            }
//        }
//    }
//    
//    
//    public static void getListOfStudentsBelongingToMoreThanOneCourse(){
//         Statement statement = null;
//        ResultSet resultSet = null;
//        try {
//            statement = connection.createStatement();
//            String sql = new StringBuilder()
//                        .append("SELECT CONCAT(`students`.`first_name`,' ',`students`.`last_name`), COUNT(1) FROM `students`")
//                        .append("JOIN `enrollments_students` ON `students`.`id` = `enrollments_students`.`students_id`")
//                        .append("GROUP BY `enrollments_students`.`students_id` HAVING COUNT(1) > 1")
//                        .toString();
//                    
//            resultSet = statement.executeQuery(sql);
//              System.out.println("\n****** GETTING LIST OF STUDENS BELONGING TO MORE THAN ONE COURSE *******");
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString(1) + ", Number of courses: " + resultSet.getString(2));
//            }
//        } catch (SQLException ex) {
//            System.out.println("ERROR: CANNOT GET LIST OF STUDENTS BELONING TO MORE THAN ONE COURSE!");
//            System.out.println(ex.toString());
//            System.exit(0);
//        } finally {
//            try {
//                resultSet.close();
//                statement.close();
//            } catch (SQLException ex) {
//            }
//        }
//    }
//    
    
    
    
    
}
