package main;

import interfaces.DateFormatable;
import bootcamp.core.Assignment;
import bootcamp.core.Course;
import bootcamp.core.Student;
import bootcamp.core.Trainer;
import bootcamp.lists.CourseAssignments;
import bootcamp.lists.CourseStudents;
import bootcamp.lists.CourseTrainers;
import database.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Printer implements DateFormatable {
    private final static int numberOfStars = 150;
            
    public Printer() {
    }
    
    public static void printEndOfProgram(){
        System.out.println("\n");
        printMessageAroundDoubleStars("Thank you for using myBootcamp!", numberOfStars);
        printMessageAroundStars("End of application", numberOfStars);
    }

    public static void printStarsBeforeAndAfterString(String message, int numberOfStars) {
        char[] charArray = message.toCharArray();
        numberOfStars -= 1;
        String result = "";
        for (char ch : charArray) {
            numberOfStars--;
        }
        for (int i = 0; i < numberOfStars; i++) {
            if (i != numberOfStars / 2) {
                result += '*';
            } else {
                result += String.format(" %s ", message);
            }
        }
        System.out.println(result);
    }
    
    public static void printSymbol(String symbol, int numberOftimes) {
        String result = "";
        for (int i = 0; i < numberOftimes; i++) {
           result += symbol;
        }
        System.out.println(result);
    }
    
    public static void printSymbolBeforeAndAfterString(String message, String symbol, int numberOftimes) {
        char[] charArray = message.toCharArray();
        numberOftimes -= 1;
        String result = "";
        for (char ch : charArray) {
            numberOftimes--;
        }
        for (int i = 0; i < numberOftimes; i++) {
            if (i != numberOftimes / 2) {
                result += symbol;
            } else {
                result += String.format(" %s ", message);
            }
        }
        System.out.println(result);
    }

    public static void printMessageAroundStars(String message, int numberOfStars) {
        printStars(numberOfStars);
        printStarsBeforeAndAfterString(message, numberOfStars);
        printStars(numberOfStars);
    }
    
    public static void printMessageAroundDoubleStars(String message, int numberOfStars){
        printStars(numberOfStars);
        printMessageAroundStars(message, numberOfStars);
        printStars(numberOfStars);
    }

    public static void printStars(int numberOfStars) {
        String result = "";
        for (int i = 0; i < numberOfStars; i++) {
            result += '*';
        }
        System.out.println(result);
    }

    public static void printWelcomeMessage() {
        printMessageAroundDoubleStars("M y  B o o t C a m p  v 1 . 0", numberOfStars);
        System.out.println("");
        System.out.println("Welcome to MyBootCamp!");
        System.out.println("");
    }

    public static void printingListsIndication() {
        System.out.println("");
        printMessageAroundStars("PRINTING LISTS", numberOfStars);
        System.out.println("");
    }

    
    // *********************************************************************** 
    // ************************ PRINT DATA FROM DB ***************************
    // *********************************************************************** 
    public static void printStudents() {
        try {
            
            String sql = new StringBuilder()
                    .append("SELECT * FROM `students`")
                    .append("ORDER BY `students`.`last_name`, `students`.`first_name`, `students`.`date_of_birth`, `students`.`tuition_fees`;").toString();
            
            ResultSet resultSet = Database.getResults(sql);
            
            if (!resultSet.next()) {
                System.out.println("No students listed.\n");
                return;
            }
            
            int numberOfDashes = 112;
            System.out.println("");
            printSymbol("-", numberOfDashes); 
            printSymbolBeforeAndAfterString("S T U D E N T S", " ", numberOfDashes);
            System.out.println("");
            printSymbol("-", numberOfDashes); 
            System.out.println(String.format("%-2s %-30s %-30s %-30s %s","#","LAST NAME", "FIRST NAME", "DATE OF BIRTH", "TUITION FEES(\u20ac)"));
            
            resultSet.beforeFirst();
            int i = 0;
            while (resultSet.next()){
               printSymbol("-", numberOfDashes); 
               System.out.printf("%d%s %-30s %-30s %-30s %s\n", ++i,".", resultSet.getString(3), resultSet.getString(2), resultSet.getString(4), resultSet.getString(5));
            }
            printSymbol("-", numberOfDashes); 
            System.out.println("\n");
        } catch (SQLException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printTrainers() {
        try {
            String sql = new StringBuilder()
                    .append("SELECT * FROM `trainers`")
                    .append("ORDER BY `trainers`.`last_name`, `trainers`.`first_name`, `trainers`.`subject`;").toString();
            
            ResultSet resultSet = Database.getResults(sql);
            
            if (!resultSet.next()) {
                System.out.println("No trainers listed.\n");
                return;
            }
            
            int numberOfDashes = 90;
            System.out.println("");
            printSymbol("-", numberOfDashes); 
            printSymbolBeforeAndAfterString("T R A I N E R S", " ", numberOfDashes);
            System.out.println("");
            printSymbol("-", numberOfDashes); 
            System.out.println(String.format("%-2s %-30s %-30s %s","#","LAST NAME", "FIRST NAME", "SUBJECT"));
            
            resultSet.beforeFirst();
            int i = 0;
            while (resultSet.next()){
                printSymbol("-", numberOfDashes); 
                System.out.printf("%d%s %-30s %-30s %s\n", ++i,".", resultSet.getString(3), resultSet.getString(2), getStringCutAtIndex(resultSet.getString(4), 26));
            }
            printSymbol("-", numberOfDashes); 
            System.out.println("\n");
        } catch (SQLException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printAssignments() {
         try {
            String sql = new StringBuilder()
                    .append("SELECT * FROM `assignments`")
                    .append("ORDER BY `assignments`.`title`, `assignments`.`due_date`, `assignments`.`passing_grade` DESC;").toString();
            
            ResultSet resultSet = Database.getResults(sql);
            
            if (!resultSet.next()) {
                System.out.println("No assignments listed.\n");
                return;
            }
            
            int numberOfDashes = 120;
            System.out.println("");
            printSymbol("-", numberOfDashes); 
            printSymbolBeforeAndAfterString("A S S I G N M E N T S", " ", numberOfDashes);
            System.out.println("");
            printSymbol("-", numberOfDashes); 
            System.out.println(String.format("%-2s %-40s %-40s %-20s %s","#","TITLE", "DESCRIPTION", "DUE DATE", "PASSING GRADE"));
            
            resultSet.beforeFirst();
            int i = 0;
            while (resultSet.next()){
                printSymbol("-", numberOfDashes); 
                 System.out.printf("%d%s %-40s %-40s %-20s %s\n", ++i,".", getStringCutAtIndex(resultSet.getString(2), 36), getStringCutAtIndex(resultSet.getString(3), 36), resultSet.getString(4), resultSet.getString(5));
            }
            printSymbol("-", numberOfDashes); 
            System.out.println("\n");
        } catch (SQLException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printCourses() {
       try {
            String sql = new StringBuilder()
                    .append("SELECT * FROM `courses`")
                    .append("ORDER BY `courses`.`title`, `courses`.`stream`, `courses`.`type`, `courses`.`start_date`, `courses`.`end_date`;").toString();
            
            ResultSet resultSet = Database.getResults(sql);
            
            if (!resultSet.next()) {
                System.out.println("No courses listed.\n");
                return;
            }
            
            int numberOfDashes = 130;
            int indexToCutStr = 26;
            System.out.println("");
            printSymbol("-", numberOfDashes); 
            printSymbolBeforeAndAfterString("C O U R S E S", " ", numberOfDashes);
            printSymbol("-", numberOfDashes); 
            System.out.println(String.format("%-2s %-30s %-30s %-30s %-20s %s","#","TITLE", "STREAM", "TYPE", "START DATE", "END DATE"));
            
            resultSet.beforeFirst();
            int i = 0;
            while (resultSet.next()){
                printSymbol("-", numberOfDashes); 
                 System.out.printf("%d%s %-30s %-30s %-30s %-20s %s\n", ++i,".", getStringCutAtIndex(resultSet.getString(2), indexToCutStr), getStringCutAtIndex(resultSet.getString(3), indexToCutStr), getStringCutAtIndex(resultSet.getString(4), indexToCutStr), resultSet.getString(5), resultSet.getString(6));
            }
            printSymbol("-", numberOfDashes); 
            System.out.println("\n");
        } catch (SQLException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printStudentsPerCourse(int course_id) {
       try {
            String sql = String.format("SELECT `students`.`first_name`, `students`.`last_name`, `students`.`date_of_birth`, `students`.`tuition_fees`, CONCAT(`courses`.`title`, ' ', `courses`.`stream`, ' ', `courses`.`type`) AS course_fullname   FROM `students` " +
                    "JOIN `enrollments_students` on `enrollments_students`.`students_id` = `students`.`id` " +
                    "JOIN `courses` on `courses`.`id` = `enrollments_students`.`courses_id` AND `courses`.`id` = %s " +
                    "ORDER BY `students`.`last_name`, `students`.`first_name`, `students`.`date_of_birth`, `students`.`tuition_fees`;", course_id);
            
            ResultSet resultSet = Database.getResults(sql);
            
            if (!resultSet.next()) {
                System.out.println("No students listed.\n");
                return;
            }
            
            System.out.println("");
            int numberOfDashes = 130;
            printSymbol("-", numberOfDashes); 
            printSymbolBeforeAndAfterString(String.format("COURSE '%s' STUDENTS", resultSet.getString(5).toUpperCase()), " ", numberOfDashes);
            printSymbol("-", numberOfDashes); 
            System.out.println(String.format("%-2s %-30s %-30s %-30s %s","#","LAST NAME", "FIRST NAME", "DATE OF BIRTH", "TUITION FEES(\u20ac)"));
            
            resultSet.beforeFirst();
            int i = 0;
            while (resultSet.next()){
               printSymbol("-", numberOfDashes); 
               System.out.printf("%d%s %-30s %-30s %-30s %s\n", ++i,".", resultSet.getString(2), resultSet.getString(1), resultSet.getString(3), resultSet.getString(4));
            }
            printSymbol("-", numberOfDashes); 
            System.out.println("\n");
        } catch (SQLException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printTrainersPerCourse(int course_id) {
        try {
            String sql = String.format("SELECT `trainers`.`first_name`, `trainers`.`last_name`, `trainers`.`subject`, CONCAT(`courses`.`title`, ' ', `courses`.`stream`, ' ', `courses`.`type`) AS course_fullname FROM `trainers` " +
                    "JOIN `enrollments_trainers` on `enrollments_trainers`.`trainers_id` = `trainers`.`id` " +
                    "JOIN `courses` on `courses`.`id` = `enrollments_trainers`.`courses_id` AND `courses`.`id` = %s " +
                    "ORDER BY `trainers`.`last_name`, `trainers`.`first_name`, `trainers`.`subject`;", course_id);
            
            ResultSet resultSet = Database.getResults(sql);
            
            if (!resultSet.next()) {
                System.out.println("No trainers listed.\n");
                return;
            }
            
            int numberOfDashes = 90;
            System.out.println("");
            printSymbol("-", numberOfDashes); 
            printSymbolBeforeAndAfterString(String.format("COURSE '%s' TRAINERS", resultSet.getString(4).toUpperCase()), " ", numberOfDashes);            
            printSymbol("-", numberOfDashes); 
            System.out.println(String.format("%-2s %-30s %-30s %s","#","LAST NAME", "FIRST NAME", "SUBJECT"));
            
            resultSet.beforeFirst();
            int i = 0;
            while (resultSet.next()){
                printSymbol("-", numberOfDashes); 
                System.out.printf("%d%s %-30s %-30s %s\n", ++i,".", resultSet.getString(2), resultSet.getString(1), getStringCutAtIndex(resultSet.getString(3), 26));
            }
            printSymbol("-", numberOfDashes); 
            System.out.println("\n");
        } catch (SQLException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printAssignmentsPerCourse(int course_id) {
        try {
            
            String sql = String.format("SELECT `assignments`.`title`, `assignments`.`description`, `assignments`.`due_date`, `assignments`.`passing_grade`, CONCAT(`courses`.`title`, ' ', `courses`.`stream`, ' ', `courses`.`type`) AS course_fullname FROM `assignments` " +
                    "JOIN `enrollments_assignments` on `enrollments_assignments`.`assignments_id` = `assignments`.`id` " +
                    "JOIN `courses` on `courses`.`id` = `enrollments_assignments`.`courses_id` AND `courses`.`id` = %s " +
                    "ORDER BY `assignments`.`title`, `assignments`.`due_date`, `assignments`.`passing_grade` DESC;", course_id);
            
            ResultSet resultSet = Database.getResults(sql);
            
           if (!resultSet.next()) {
                System.out.println("No assignments listed.\n");
                return;
            }
           
            int numberOfDashes = 120;
            System.out.println("");
            printSymbol("-", numberOfDashes); 
            printSymbolBeforeAndAfterString(String.format("COURSE '%s' ASSIGNMENTS", resultSet.getString(5).toUpperCase()), " ", numberOfDashes);
             
            resultSet.beforeFirst();
            int i = 0;
            printSymbol("-", numberOfDashes); 
            System.out.println(String.format("%-2s %-40s %-40s %-20s %s","#","TITLE", "DESCRIPTION", "DUE DATE", "PASSING GRADE"));
            while (resultSet.next()){
                printSymbol("-", numberOfDashes); 
                 System.out.printf("%d%s %-40s %-40s %-20s %s\n", ++i,".", getStringCutAtIndex(resultSet.getString(1), 36), getStringCutAtIndex(resultSet.getString(2), 36), resultSet.getString(3), resultSet.getString(4));
            }
            printSymbol("-", numberOfDashes); 
            System.out.println("\n");
        } catch (SQLException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void printAssignmentsPerCoursePerStudent(int course_id, int students_id){
        try {
            String sql = String.format("SELECT `assignments`.`title`, `assignments`.`description`, `assignments`.`due_date`, `assignments_students_courses`.`submission_date`, `assignments`.`passing_grade`, `assignments_students_courses`.`grade`, CONCAT(`students`.`first_name`, ' ', `students`.`last_name`), CONCAT(`courses`.`title`, ' ', `courses`.`stream`, ' ', `courses`.`type`) FROM `assignments_students_courses` " +
                    "JOIN `courses` ON `assignments_students_courses`.`courses_id` = `courses`.`id` AND `courses`.`id` = %s " +
                    "JOIN `students` ON `assignments_students_courses`.`students_id` = `students`.`id` AND `students`.`id` = %s " +
                    "JOIN `assignments` ON `assignments_students_courses`.`assignments_id` = `assignments`.`id` " +
                    "ORDER BY `assignments`.`title`, `assignments`.`due_date`;", course_id, students_id);
            
            ResultSet resultSet = Database.getResults(sql);
            
           if (!resultSet.next()) {
                System.out.println("No assignments listed.\n");
                return;
            }
            int numberOfDashes = 153;
            System.out.println("");
            printSymbol("-", numberOfDashes); 
            printSymbolBeforeAndAfterString(String.format("ASSIGNMENTS OF STUDENT '%s' FOR COURSE '%s'", resultSet.getString(7).toUpperCase(), resultSet.getString(8).toUpperCase()), " ", numberOfDashes);
            
            resultSet.beforeFirst();
            int i = 0;
            printSymbol("-", numberOfDashes); 
            System.out.println(String.format("%-2s %-40s %-40s %-20s %-20s %-20s %s","#","TITLE", "DESCRIPTION", "DUE DATE", "SUBMISSION DATE", "BASE GRADE", "GRADE"));
            while (resultSet.next()){
                printSymbol("-", numberOfDashes); 
                 System.out.printf("%d%s %-40s %-40s %-20s %-20s %-20s %s\n", ++i,".", getStringCutAtIndex(resultSet.getString(1), 36), getStringCutAtIndex(resultSet.getString(2), 36), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
            }
            printSymbol("-", numberOfDashes); 
            System.out.println("\n");
        } catch (SQLException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void printStudentsBelongingToMoreThanOneCourse(){
        try {
            String sql = "SELECT `students`.`first_name`, `students`.`last_name`, `students`.`date_of_birth`, `students`.`tuition_fees`, COUNT(1) AS `number_of_courses` FROM `students` " +
                    "JOIN `enrollments_students` ON `students`.`id` = `enrollments_students`.`students_id` " +
                    "GROUP BY `enrollments_students`.`students_id` HAVING `number_of_courses` > 1 " +
                    "ORDER BY `number_of_courses` DESC, `students`.`last_name`, `students`.`first_name`,`students`.`date_of_birth`, `students`.`tuition_fees`;";
            
            ResultSet resultSet = Database.getResults(sql);
            
            if (!resultSet.next()) {
                System.out.println("No students listed.\n");
                return;
            }
            
            int numberOfDashes = 135;
            System.out.println("");
            printSymbol("-", numberOfDashes); 
            printSymbolBeforeAndAfterString(String.format("STUDENTS BELONING TO MORE THAN 1 COURSES", resultSet.getString(5).toUpperCase()), " ", numberOfDashes);
            
            resultSet.beforeFirst();
            int i = 0;
            printSymbol("-", numberOfDashes); 
            System.out.println(String.format("%-2s %-30s %-30s %-30s %-20s %s","#","LAST NAME", "FIRST NAME", "DATE OF BIRTH", "TUITION FEES(\u20ac)", "NUMBER OF COURSES"));
            while (resultSet.next()){
               printSymbol("-", numberOfDashes); 
               System.out.printf("%d%s %-30s %-30s %-30s %-20s %s\n", ++i,".", resultSet.getString(2), resultSet.getString(1), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
            }
            printSymbol("-", numberOfDashes); 
            System.out.println("\n");
        } catch (SQLException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static LocalDate getFirstDayOfWeek(LocalDate date) {
        LocalDate firstDateOfWeek = date;
        while (firstDateOfWeek.getDayOfWeek() != DayOfWeek.MONDAY) {
            firstDateOfWeek = firstDateOfWeek.minusDays(1);
        }
        return firstDateOfWeek;
    }

    public static LocalDate getLastDayOfWeekFromFirst(LocalDate firstDateOfWeek) {
        return firstDateOfWeek.plusDays(4);
    }
    
    public static String getStringCutAtIndex(String str, int index){
        if (str.length() > index){
            return str.substring(0, index).trim() + "...";
        }
        return str;
    }
    
    //**********************************************************************
    //********************* PRINT LOCAL DATA ********************************
    //**********************************************************************
    public static void printLocalStudents(Set<Student> setOfStudents) {
        printStarsBeforeAndAfterString("PRINTING LIST OF ALL STUDENTS", numberOfStars);
        if (setOfStudents.size() == 0) {
            System.out.println("No students listed.\n");
            return;
        }
        int i = 0;
        for (Student student : setOfStudents) {
            System.out.printf("%d. %s\n", ++i, student);
        }
        System.out.println("");
    }

    public static void printLocalTrainers(Set<Trainer> setOfTrainers) {
        //System.out.println("\n************ PRINTING LIST OF TRAINERS **************");
        printStarsBeforeAndAfterString("PRINTING LIST OF ALL TRAINERS", numberOfStars);
        if (setOfTrainers.size() == 0) {
            System.out.println("No trainers listed.\n");
            return;
        }
        int i = 0;
        for (Trainer trainer : setOfTrainers) {
            System.out.printf("%d. %s\n", ++i, trainer);
        }
        System.out.println("");
    }

    public static void printLocalAssignments(Set<Assignment> setOfAssignments) {
        //System.out.println("\n************ PRINTING LIST OF ASSIGNMENTS **************");
        printStarsBeforeAndAfterString("PRINTING LIST OF ALL ASSIGNMENTS", numberOfStars);
        if (setOfAssignments.size() == 0) {
            System.out.println("No assignments listed.\n");
            return;
        }
        int i = 0;
        for (Assignment assignment : setOfAssignments) {
            System.out.printf("%d. %s\n", ++i, assignment);
           
        }
        System.out.println("");
    }

    public static void printLocalCourses(Set<Course> setOfCourses) {
        printStarsBeforeAndAfterString("PRINTING LIST OF ALL COURSES", numberOfStars);
        if (setOfCourses.size() == 0) {
            System.out.println("No courses listed.\n");
            return;
        }
        int i = 0;
        for (Course course : setOfCourses) {
            System.out.printf("%d. %s\n", ++i, course);
           
        }
        System.out.println("");
    }
    
     public static void printLocalCourseStudents(Set<CourseStudents> setOfStudentsPerCourse) {
        for (CourseStudents studentsPerCourse : setOfStudentsPerCourse) {
            Course course = studentsPerCourse.getCourse();
            Set<Student> setOfStudents = studentsPerCourse.getSetOfComponents();
            printStarsBeforeAndAfterString(String.format("PRINTING LIST OF STUDENTS IN COURSE %s / %s / %s", course.getTitle().toUpperCase(), course.getStream().toUpperCase(), course.getType().toUpperCase()), numberOfStars);
            if (setOfStudents.size() == 0){
                System.out.println("No students listed.\n");
            }
            else {
                int i = 0;
                for (Student student: setOfStudents){
                     System.out.printf("%d. %s (DOB: %s)\n", ++i, student.getFullName(), student.getDateOfBirth().format(formatter));
                }
            }
            System.out.println("");
        }
       
    }

    public static void printLocalCourseTrainers(Set<CourseTrainers> setOfTrainersPerCourse) {
        for (CourseTrainers trainersPerCourse : setOfTrainersPerCourse) {
            Course course = trainersPerCourse.getCourse();
            Set<Trainer> setOfTrainers = trainersPerCourse.getSetOfComponents();
            printStarsBeforeAndAfterString(String.format("PRINTING LIST OF TRAINERS IN COURSE %s / %s / %s", course.getTitle().toUpperCase(), course.getStream().toUpperCase(), course.getType().toUpperCase()), numberOfStars);
            if (setOfTrainers.size() == 0) {
                System.out.println("No trainers listed.\n");
            } 
            else {
                int i = 0;
                for (Trainer trainer: setOfTrainers) {
                    System.out.printf("%d. %s\n", ++i, trainer);
                }
            }
            System.out.println("");
        }
    }

    public static void printLocalCourseAssignments(Set<CourseAssignments> setOfAssignmentsPerCourse) {
        for (CourseAssignments assignmentsPerCourse : setOfAssignmentsPerCourse) {
            Set<Assignment> setOfAssignments = assignmentsPerCourse.getSetOfComponents();
            Course course = assignmentsPerCourse.getCourse();
            printStarsBeforeAndAfterString(String.format("PRINTING LIST OF ASSIGNMENTS IN COURSE %s / %s / %s", course.getTitle().toUpperCase(), course.getStream().toUpperCase(), course.getType().toUpperCase()), numberOfStars);            
            if (setOfAssignments.size() == 0) {
                System.out.println("No assignments listed.\n");
            } else {
                int i = 0;
                for (Assignment assignment : setOfAssignments) {
                    System.out.printf("%d. %s\n", ++i, assignment);
                }
            }
            System.out.println("");
        }
    }
    
    
}
