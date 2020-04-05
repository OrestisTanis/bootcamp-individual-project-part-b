package main;

import appstate.AppState;
import appstate.MenuState;
import appstate.UserData;
import bootcamp.creators.StudentCreator;
import bootcamp.creators.CourseCreator;
import bootcamp.creators.AssignmentCreator;
import bootcamp.creators.TrainerCreator;
import bootcamp.creators.list.CourseAssignmentsCreator;
import bootcamp.creators.list.CourseStudentsCreator;
import bootcamp.creators.list.CourseTrainersCreator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class MainClass {
    public static void main(String[] args) {
        Input.createScanner();
        AppState appState = new AppState();
        UserData userData = appState.getUserData();
        
        // Greeting
        Printer.printWelcomeMessage();
        System.out.println("Do you want to create your own data? If you answer no, synthetic data will be used and printed. (Y/N)");
        String choice = Input.getString("[yYNn]", "Y/N?");
        
        MySqlCon.createConnection();
        //MySqlCon.createStudent("Michail", "Aggelos", "1985-02-02", "2500.00");
        MySqlCon.getListOfAllStudents();
        MySqlCon.getListOfAllCourses();
        MySqlCon.getListOfAllTrainers();
        MySqlCon.getListOfAllAssignments();
        MySqlCon.getListOfStudentsPerCourse();
        MySqlCon.getListOfTrainersPerCourse();
        MySqlCon.getListOfAssignmentsPerCourse();
        MySqlCon.getListOfAssignmentsPerCoursePerStudent();
        MySqlCon.getListOfStudentsBelongingToMoreThanOneCourse();
        MySqlCon.closeConnection();
       
//        if(choice.equalsIgnoreCase("n")){
//            populateUserDataWithSyntheticData(userData);
//        }
//        else {
//            while (appState.getMenuState() != MenuState.EXITING) {
//                if (appState.getMenuState() == MenuState.CREATION){
//                    goToCreationMenu(appState);
//                }
//                if (appState.getMenuState() == MenuState.INSERTION) {
//                    goToInsertionMenu(appState);
//                }
//            }
//        }
//        Printer.printAll(userData);
//        askForDate(userData);
//        Printer.printEndOfProgram();
        Input.closeScanner();
    }
    
    public static void goToCreationMenu(AppState appState){
        MenuState menuState = appState.getMenuState();
        UserData userData = appState.getUserData();
        System.out.println("\nWhat would you like to do?");
        ArrayList<String> options = Input.printOptions("Create Course", "Create Trainer", "Create Student", "Create Assignment", "Continue", "Exit");        
        int choice = Input.getOptionInt(options);
        switch (choice) {
            case 1:
                CourseCreator courseCreator = new CourseCreator();
                courseCreator.createCourses(userData);
                break;
            case 2:
                TrainerCreator trainerCreator = new TrainerCreator();
                trainerCreator.createTrainers(userData);
                break;
            case 3:
                StudentCreator studentCreator = new StudentCreator();
                studentCreator.createStudents(userData);
                break;
            case 4:
                AssignmentCreator assignmentCreator = new AssignmentCreator();
                assignmentCreator.createAssignments(userData);
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
    
    public static void goToInsertionMenu(AppState appState){
        MenuState menuState = appState.getMenuState();
        UserData userData = appState.getUserData();
        System.out.println("\nWhat would you like to do now?");
        ArrayList<String> options = Input.printOptions("Insert Trainer to course", "Insert Student to course", "Insert Assignment to course", "Go Back", "Continue to Print", "Exit");                    
        int choice = Input.getOptionInt(options);
        switch (choice) {
            case 1:
                CourseTrainersCreator courseTrainersCreator = new CourseTrainersCreator();
                courseTrainersCreator.createTrainersPerCourse(userData);
                break;
            case 2:
                CourseStudentsCreator courseStudentsCreator = new CourseStudentsCreator();
                courseStudentsCreator.createStudentsPerCourse((userData));
                break;
            case 3:
                CourseAssignmentsCreator courseAssignmentsCreator = new CourseAssignmentsCreator();
                courseAssignmentsCreator.createAssignmentsPerCourse(userData);
                break;
            case 4:
                menuState = MenuState.CREATION;
                break;
            case 5:
            default:
                menuState = MenuState.EXITING;
                break;
        }
        appState.setMenuState(menuState);
    }
    
    public static void askForDate(UserData userData){
        String message = " date to print the list of students who need to submit one \n" +
                            "or more assignments on the same calendar week? (Y/N)";
        System.out.println("\nLastly, do you want to enter a" + message);
        String choice = Input.getString("[yYNn]", "Y/N?");
        while (choice.equalsIgnoreCase("y")){
            System.out.println("\nPlease enter assignment submission date (dd/MM/yyyy): ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String validDate = "2015-01-01";
            LocalDate assignmentSubDate = Input.getLocalDateAfter(LocalDate.parse(validDate),"dd/MM/yyyy", "Invalid date. Enter a valid date after " + LocalDate.parse(validDate).format(formatter) + " in the format of dd/MM/yyyy: ");
            Printer.printListOfStudentsWithAssignmentsInWeek(userData, assignmentSubDate);
            System.out.println("\nDo you want to enter a new" + message + "\n");
            choice = Input.getString("[yYNn]", "Y/N?");
        } 
    }
   
    private static void populateUserDataWithSyntheticData(UserData userData){
        SyntheticData data = new SyntheticData();
        userData.setSetOfCourses(data.getCourses());
        userData.setSetOfTrainers(data.getTrainers());
        userData.setSetOfStudents(data.getStudents());
        userData.setSetOfAssignments(data.getAssignments());
        userData.setSetOfTrainersPerCourse(data.getSetOfCourseTrainers());
        userData.setSetOfStudentsPerCourse(data.getSetOfCourseStudents());
        userData.setSetOfAssignmentsPerCourse(data.getAssignmentsPerCourses());
    }
}
