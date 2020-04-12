/*
 * This class provides useful utilities for the development of console programs which 
 * require input from the user.
 */
package main;

import appstate.UserData;
import bootcamp.core.Course;
import bootcamp.core.Student;
import database.models.CourseData;
import database.models.StudentData;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class Input{
    private static Scanner scanner;
    
    public static void createScanner(){
        if (scanner == null){
            scanner = new Scanner(System.in);
        }
    }
    public Scanner getScanner(){
        return scanner;
    }
    public static void closeScanner(){
        scanner.close();
    }

    /**
     * Outputs the specified <tt>string<tt> to the console
     *
     * @param message The message to output
     */
    public static void printMessage(String message) {
        System.out.println(message);

    }

    /**
     * Outputs to the console  each string contained in 
     * the <tt>List</tt> in the following format:
     * <p>
     * 1. String
     * <p>
     * 2. String
     * <p>
     * 3. String
     * <p>
     * ...
     *
     * @param list a <tt>List</tt> object
     */
    public static void printOptionsFromList(List list) {
        for (int i = 0; i < list.size(); i++) {
            printMessage(i + 1 + ". " + list.get(i));
        }
    }
    
    /**
     * Outputs to the console each object contained in the <tt>Set</tt> 
     * using its toString() method in the following format:
     * <p>
     * 1. Object
     * <p>
     * 2. Object
     * <p>
     * 3. Object
     * <p>
     * ...
     *
     * @param set a <tt>Set</tt> object
     */
    public static void printOptionsFromSet(Set set){
        Iterator iterator = set.iterator();
        int i = 0;
        while (iterator.hasNext()){
            printMessage(++i + ". " + iterator.next());
        }
    }
    
    /**
     * Reads an integer input from the user and returns the <tt>Object</tt> located in the
     * <tt>Set</tt> at index of (input - 1) which matches user's selection,
     * while enforcing input validation
     *
     * @param Set A Set object
     * @return The object contained in the set, which represents the option chosen by the user
     */
    public static Object getOptionFromSet(Set set){
        Object obj = null;
        Iterator iterator = set.iterator();
        int input = getIntFromTo(1, set.size());
        int i = 0;  // i = 6
        while (iterator.hasNext()){
            i++;
            obj = iterator.next();
            if ( i == input ){
                return obj;
            }
        }
        return obj;
    }

    /**
     * Outputs any number of string parameters to the console in the following format:
     * <p>
     * 1. StringParam
     * <p>
     * 2. StringParam
     * <p>
     * 3. StringParam
     * <p>
     * ...
     *
     * @param args Any number of string parameters
     * @return An ArrayList of strings containing the passed parameters
     */
    public static ArrayList<String> printOptions(String... args) {
        byte i = 0;
        ArrayList<String> list = new ArrayList();

        for (String arg : args) {
            i++;
            printMessage(i + ". " + arg);
            list.add(arg);
        }
        return list;
    }

    /**
     * Reads input from the user and returns the <tt>string</tt> located in the
     * <tt>ArrayList</tt> at index (input - 1), which matches user's selection,
     * while enforcing input validation
     *
     * @param arrayList An ArrayList of strings
     * @return The string representing the option chosen by the user
     */
    public static String getOptionFromList(List<String> list) {
        int optionsListSize = list.size();
        int input = getIntFromTo(1, optionsListSize);
        return list.get(input - 1);
    }

    /**
     * Reads input from the user and returns an <tt>integer</tt>
     * between 1 and the specified arrayList's size, while enforcing input
     * validation
     *
     * @param scanner A Scanner object
     * @param arrayList An ArrayList of strings
     * @return The string representing the option chosen by the user
     */
    public static int getOptionInt(List list) {
        int optionsListSize = list.size();
        int input = getIntFromTo(1, optionsListSize);
        return input;
    }

    /**
     * Ensures that an integer between <tt>lowerBound</tt> and
     * <tt>upperBound</tt> will be returned by the user. Displays a message on
     * invalid input while forces the user to give valid input.
     *
     * @param scanner A Scanner object
     * @param int lowerBound The lower bound of the accepted input range.
     * @param int upperBound The upper bound of the accepted input range.
     * @return An integer number between the specified range.
     */
    public static int getIntFromTo(int lowerBound, int upperBound) {
        int input = lowerBound - 1;
        boolean onlyOneOption = lowerBound - upperBound == 0;
        while (input < lowerBound || input > upperBound){
            try {
                input = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                input = lowerBound - 1;
            }
            if ((input < lowerBound || input > upperBound) && onlyOneOption) {
                System.out.printf("The only option is (%d): \n", lowerBound);
            }
            else if (input < lowerBound || input > upperBound){
                System.out.printf("Please enter a valid number (%d-%d): \n", lowerBound, upperBound);
            }
            
        }
        System.out.println(input);
        return input;
    }
    
    /**
    * Ensures that a positive integer will be returned as input by the user. 
    * Displays a message on invalid input while forcing the user to give valid input.
    *
    * @param scanner A Scanner object
    * @return A positive integer number.
    */
    public static int getPositiveInt() {
        int input = 0;

        do {
            if (input < 0) {
                System.out.println("Only positive integer numbers are allowed. Please enter a new value: ");
            }

            try {
                input = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                input =  -1;
            }

        } while (input < 0);

        return input;
    }
    
    /**
    * Ensures that a positive double will be returned by the user. 
    * Displays a message on invalid input while forcing the user to give new input.
    *
    * @param scanner A Scanner object
    * @return A positive double number.
    */
    public static double getPositiveDouble() {
        double input = 0;

        do {
            if (input < 0) {
                System.out.println("Only positive numbers are allowed. Please enter a new value: ");
            }

            try {
                input = scanner.nextDouble();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                input =  -1;
            }

        } while (input < 0);

        return input;
    }

    /**
     * Ensures that a string that matches the specified regular expression will
     * be returned by the user. Displays a message on invalid input while forcing
     * the user to give valid input.
     *
     * @param scanner A Scanner object
     * @param allowedRegex The regular expression that matches the desired user
     * input.
     * @return A string that matches the specified regular expression
     */
    public static String getString(String allowedRegex, String invalidInputMessage) {
        String userInput = "";
        Pattern pattern = Pattern.compile(allowedRegex);
        while (!pattern.matcher(userInput).matches()) {
            userInput = scanner.nextLine().trim();

            if (!pattern.matcher(userInput).matches()) {
                System.out.println(invalidInputMessage);
            }
        }
        return userInput;
    }
    
    public static LocalDate getLocalDateAfter(LocalDate minDate, String pattern, String invalidInputMessage){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        boolean dateValid = false;
        LocalDate resultDate = null;
        while(!dateValid){
            String inputStr = scanner.nextLine().trim();
            if (isDateValid(formatter, inputStr)){
                resultDate = LocalDate.parse(inputStr, formatter);
                dateValid = minDate.isBefore(resultDate);
            }
            if (!dateValid){
                System.out.println(invalidInputMessage);
            }
        }
        return resultDate;
    }
    
    
    
    public static LocalDate getWorkDateAfter(LocalDate minDate, String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        boolean dateValid = false;
        LocalDate resultDate = null;
        String dateFormatStr = "dd/MM/yyyy";
        while(!dateValid){
            String inputStr = scanner.nextLine().trim();
            if (isDateValid(formatter, inputStr)){
                resultDate = LocalDate.parse(inputStr, formatter);
                dateValid = minDate.isBefore(resultDate);
            }
            if (dateValid && (resultDate.getDayOfWeek() == DayOfWeek.SATURDAY || resultDate.getDayOfWeek() == DayOfWeek.SUNDAY)){
                dateValid = false;
            }
            if (!dateValid){
                System.out.printf("Invalid date. Please enter a valid working date after %s (%s): \n", minDate.format(formatter), dateFormatStr);
            }
        }
        return resultDate;
    }
    
    public static boolean isDateValid(DateTimeFormatter f,String dateStr){
        try {
            LocalDate.parse(dateStr, f);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    
    public static LocalDate getLocalDateFromTo(LocalDate minDate, LocalDate maxDate, String allowedPattern, String invalidInputMessage){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(allowedPattern); //"dd/MM/yyyy"
        boolean dateValid = false;
        LocalDate resultDate = LocalDate.parse("1001-01-01");
        while(!dateValid){
            String inputStr = scanner.nextLine().trim();
            if (isDateValid(formatter, inputStr)){
                resultDate = LocalDate.parse(inputStr, formatter);
                dateValid = minDate.isBefore(resultDate.plusDays(1)) && maxDate.plusDays(1).isAfter(resultDate);
            }
            if (!dateValid){
                System.out.println(invalidInputMessage);
            }
        }
        return resultDate;
    }
    
    public static Course getCourseFromUser(UserData userData){
        Set setOfCourses = userData.getSetOfCourses();
        Input.printOptionsFromSet(setOfCourses);
        Course course = (Course)Input.getOptionFromSet(setOfCourses);
        return course;
    }
    
    public static int getCourseIDFromUser(UserData userData){
        System.out.println("\nChoose a course: ");
        CourseData courseData = (CourseData)getCourseFromUser(userData);
        return courseData.getId();
    }
    
    public static Student getStudentFromUser(UserData userData){
        System.out.println("\nChoose a student: ");
        Set setOfStudents = userData.getSetOfStudents();
        Input.printOptionsFromSet(setOfStudents);
        Student student = (Student) Input.getOptionFromSet(setOfStudents);
        return student;
    }
    
    public static int getStudentIDFromUser(UserData userData){
        StudentData studentData = (StudentData) getStudentFromUser(userData);
        return studentData.getId();
    }
}
