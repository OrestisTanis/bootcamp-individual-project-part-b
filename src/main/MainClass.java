package main;

import database.Database;
import appstate.AppState;
import appstate.MenuState;
import appstate.UserData;
import bootcamp.core.Course;
import bootcamp.core.Student;
import bootcamp.creators.StudentCreator;
import bootcamp.creators.CourseCreator;
import bootcamp.creators.AssignmentCreator;
import bootcamp.creators.TrainerCreator;
import bootcamp.creators.list.CourseAssignmentsCreator;
import bootcamp.creators.list.CourseStudentsAssignmentsCreator;
import bootcamp.creators.list.CourseStudentsCreator;
import bootcamp.creators.list.CourseTrainersCreator;
import database.models.CourseData;
import database.models.StudentData;
import java.util.ArrayList;
import java.util.Set;


public class MainClass {
    public static void main(String[] args) {
        // Initialization
        Input.createScanner();
        AppState appState = new AppState();
        UserData userData = appState.getUserData();
        Database db = new Database();
        Syncer syncer = new Syncer(userData, db);
        syncer.syncData();
        
        // Greeting
        Printer.printWelcomeMessage();
        
        // Menu
        while (appState.getMenuState() != MenuState.EXITING) {
            switch(appState.getMenuState()){
                case CREATION :
                    goToCreationMenu(appState, db);
                    break;
                case INSERTION:
                    goToInsertionMenu(appState, db);
                    break;
                case PRINTING:
                    goToPrintingMenu(appState, db);
                    break;
                default:
                    break;
            }
        }
//        PRINTLOCAL DATA
//        Printer.printLocalCourses(userData.getSetOfCourses());
//        Printer.printLocalTrainers(userData.getSetOfTrainers());
//        Printer.printLocalAssignments(userData.getSetOfAssignments());
//        Printer.printLocalStudents(userData.getSetOfStudents());
//        Printer.printLocalCourseTrainers(userData.getSetOfTrainersPerCourse());
//        Printer.printLocalCourseStudents(userData.getSetOfStudentsPerCourse());
//        Printer.printLocalCourseAssignments(userData.getSetOfAssignmentsPerCourse());
        db.closeConnection();
        Input.closeScanner();
        Printer.printEndOfProgram();
    }
    
    public static void goToCreationMenu(AppState appState, Database db){
        MenuState menuState = appState.getMenuState();
        UserData userData = appState.getUserData();
        System.out.println("\nWhat would you like to do?");
        ArrayList<String> options = Input.printOptions("Create Course", "Create Trainer", "Create Student", "Create Assignment", "Continue", "Exit");        
        int choice = Input.getOptionInt(options);
        switch (choice) {
            case 1:
                CourseCreator courseCreator = new CourseCreator();
                courseCreator.createCourses(userData, db);
                break;
            case 2:
                TrainerCreator trainerCreator = new TrainerCreator();
                trainerCreator.createTrainers(userData, db);
                break;
            case 3:
                StudentCreator studentCreator = new StudentCreator();
                studentCreator.createStudents(userData, db);
                break;
            case 4:
                AssignmentCreator assignmentCreator = new AssignmentCreator();
                assignmentCreator.createAssignments(userData, db);
                break;
            case 5:
                menuState = MenuState.INSERTION;
                break;
            default:
                menuState = MenuState.EXITING;
                break;
        }
        appState.setMenuState(menuState);
    }
    
    public static void goToInsertionMenu(AppState appState, Database db){
        MenuState menuState = appState.getMenuState();
        UserData userData = appState.getUserData();
        System.out.println("\nWhat would you like to do now?");
        ArrayList<String> options = Input.printOptions("Insert Trainer to course", "Insert Student to course", "Insert Assignment to course", "Insert Submitted Assignment", "Go Back", "Continue to Print", "Exit");                    
        int choice = Input.getOptionInt(options);
        switch (choice) {
            case 1:
                CourseTrainersCreator courseTrainersCreator = new CourseTrainersCreator();
                courseTrainersCreator.createTrainersPerCourse(userData, db);
                break;
            case 2:
                CourseStudentsCreator courseStudentsCreator = new CourseStudentsCreator();
                courseStudentsCreator.createStudentsPerCourse(userData, db);
                break;
            case 3:
                CourseAssignmentsCreator courseAssignmentsCreator = new CourseAssignmentsCreator();
                courseAssignmentsCreator.createAssignmentsPerCourse(userData, db);
                break;
            case 4:
                CourseStudentsAssignmentsCreator submittedAssignmentsCreator = new CourseStudentsAssignmentsCreator();
                submittedAssignmentsCreator.createSubmittedAssignments(userData, db);
            case 5:
                menuState = MenuState.CREATION;
                break;
            case 6:
                menuState = MenuState.PRINTING;
                break;
            default:
                menuState = MenuState.EXITING;
                break;
        }
        appState.setMenuState(menuState);
    }
    
    public static void goToPrintingMenu(AppState appState, Database db){
        MenuState menuState = appState.getMenuState();
        UserData userData = appState.getUserData();
        int courseID;
        System.out.println("\nWhat would you like to print?");
        ArrayList<String> options = Input.printOptions("Courses", "Trainers", "Students", "Assignments", "Students per Course",
                                                        "Trainers per Course", "Assignments Per Course", "Assignments per Course per Student",
                                                        "Students belonging to more than 1 Courses", "Go Back", "Exit");                    
        int choice = Input.getOptionInt(options);
        switch (choice) {
            case 1:
                Printer.printCourses();
                break;
            case 2:
                Printer.printTrainers();
                break;
            case 3:
                Printer.printStudents();
                break;
            case 4:
                Printer.printAssignments();
                break;
            case 5:
                courseID = Input.getCourseIDFromUser(userData);
                Printer.printStudentsPerCourse(courseID);
                break;
            case 6:
                courseID = Input.getCourseIDFromUser(userData);
                Printer.printTrainersPerCourse(courseID);
                break;
            case 7:
                courseID = Input.getCourseIDFromUser(userData);
                Printer.printAssignmentsPerCourse(courseID);
                break;
            case 8:
                Student student = Input.getStudentFromUser(userData);
                int studentID = ((StudentData)student).getId();
                Set setOfCoursesPerStudent = userData.getSetOfCoursesPerStudent(student);
                System.out.println("\nChoose a course: ");
                Input.printOptionsFromSet(setOfCoursesPerStudent);
                Course course = (Course)Input.getOptionFromSet(setOfCoursesPerStudent);
                courseID = ((CourseData)course).getId();
                Printer.printAssignmentsPerCoursePerStudent(courseID, studentID);
                break;
            case 9:
                Printer.printStudentsBelongingToMoreThanOneCourse();
                break;
            case 10:
                menuState = MenuState.INSERTION;
                break;
            default:
                menuState = MenuState.EXITING;
                break;
        }
        appState.setMenuState(menuState);
    }
}
    
