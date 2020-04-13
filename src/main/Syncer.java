package main;

import appstate.UserData;
import bootcamp.core.Assignment;
import bootcamp.core.Course;
import bootcamp.core.Student;
import bootcamp.core.Trainer;
import bootcamp.lists.CourseAssignments;
import bootcamp.lists.CourseStudents;
import bootcamp.lists.CourseTrainers;
import database.Database;
import database.models.AssignmentData;
import database.models.CourseAssignmentsData;
import database.models.CourseData;
import database.models.CourseStudentsData;
import database.models.CourseTrainersData;
import database.models.StudentData;
import database.models.TrainerData;
import static interfaces.DateFormatable.formatter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Syncer {
    private UserData userData;
    private Database db;
    
    public Syncer(UserData userData, Database db){
        this.userData = userData;
        this.db = db;
    }

    public UserData getUserData() {
        return userData;
    }
    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Database getDb() {
        return db;
    }
    public void setDb(Database db) {
        this.db = db;
    }
    
    public void syncData(){
        System.out.println("Initializing...");
        System.out.println("Syncing local data with the data from the database...");
        syncSetOfCoursesWithDB();
        syncSetOfTrainersWithDB();
        syncSetOfStudentsWithDB();
        syncSetOfAssignmentsWithDB();
        syncTrainersPerCourseWithDB();
        syncStudentsPerCourseWithDB();
        syncAssignmentsPerCourseWithDB();
        System.out.println("");
    }
    
    public void syncSetOfCoursesWithDB(){
        try {
            ResultSet resultSet = db.getResults("SELECT * FROM `courses`");
            Set<Course> setOfCourses = userData.getSetOfCourses();
            while (resultSet.next()){
                CourseData courseData = new CourseData(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDate(5).toLocalDate(), resultSet.getDate(6).toLocalDate());
                setOfCourses.add((Course)courseData);
            }
            System.out.println("Course local data synced with db successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(Syncer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void syncSetOfTrainersWithDB(){
        try {
            ResultSet resultSet = db.getResults("SELECT * FROM `trainers`");
            Set<Trainer> setOfTrainers = userData.getSetOfTrainers();
            while (resultSet.next()){
                TrainerData trainerData = new TrainerData(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                setOfTrainers.add((Trainer)trainerData);
            }
            System.out.println("Trainer local data synced with db successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(Syncer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void syncSetOfStudentsWithDB(){
        try {
            ResultSet resultSet = db.getResults("SELECT * FROM `students`");
            Set<Student> setOfStudents = userData.getSetOfStudents();
            while (resultSet.next()){
                StudentData studentData = new StudentData(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4).toLocalDate(), resultSet.getDouble(5));
                setOfStudents.add((Student)studentData);
            }
            System.out.println("Student local data synced with db successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(Syncer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void syncSetOfAssignmentsWithDB(){
        try {
            ResultSet resultSet = db.getResults("SELECT * FROM `assignments`");
            Set<Assignment> setOfASsignments = userData.getSetOfAssignments();
            while (resultSet.next()){
                AssignmentData assignmentData = new AssignmentData(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4).toLocalDate(), resultSet.getInt(5));
                setOfASsignments.add((Assignment)assignmentData);
            }
            System.out.println("Assignment local data synced with db successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(Syncer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void syncTrainersPerCourseWithDB(){
        try {
            ResultSet resultSet = db.getResults("SELECT * FROM `enrollments_trainers`");
            Set<CourseTrainers> setOfTrainersPerCourse = userData.getSetOfTrainersPerCourse();
            while (resultSet.next()){
                CourseData courseData = getCourseObjByID(resultSet, resultSet.getInt(2));
                TrainerData trainerData = getTrainerObjByID(resultSet.getInt(1));
                addTrainerToTrainersPerCourseList(trainerData, courseData);
            }
            System.out.println("Trainers Per Course local data synced with db successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(Syncer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void syncStudentsPerCourseWithDB(){
        try {
            ResultSet resultSet = db.getResults("SELECT * FROM `enrollments_students`");
            Set<CourseStudents> setOfStudentsPerCourse = userData.getSetOfStudentsPerCourse();
            while (resultSet.next()){
                CourseData courseData = getCourseObjByID(resultSet, resultSet.getInt(2));
                StudentData studentData = getStudentObjByID(resultSet.getInt(1));
                addStudentToStudentsPerCourseList(studentData, courseData);
            }
            System.out.println("Students Per Course local data synced with db successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(Syncer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void syncAssignmentsPerCourseWithDB(){
        try {
            ResultSet resultSet = db.getResults("SELECT * FROM `enrollments_assignments`");
            Set<CourseAssignments> setOfAssignmentsPerCourse = userData.getSetOfAssignmentsPerCourse();
            while (resultSet.next()){
                CourseData courseData = getCourseObjByID(resultSet, resultSet.getInt(2));
                AssignmentData assignmentData = getAssignmentObjByID(resultSet.getInt(1));
                addAssignmentToAssignmentsPerCourseList(assignmentData, courseData);
            }
            System.out.println("Assignments Per Course local data synced with db successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(Syncer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private TrainerData getTrainerObjByID(int id){
        Set<Trainer> trainers = userData.getSetOfTrainers();
        Iterator it = trainers.iterator();
        while (it.hasNext()){
            TrainerData trainer = (TrainerData)it.next();
            if (trainer.getId() == id){
                return trainer;
            }
        }
        return null;
    }
    
    private StudentData getStudentObjByID(int id){
        Set<Student> students = userData.getSetOfStudents();
        Iterator it = students.iterator();
        while (it.hasNext()){
            StudentData student = (StudentData)it.next();
            if (student.getId() == id){
                return student;
            }
        }
        return null;
    }
    
    private AssignmentData getAssignmentObjByID(int id){
        Set<Assignment> assignments = userData.getSetOfAssignments();
        Iterator it = assignments.iterator();
        while (it.hasNext()){
            AssignmentData assignment = (AssignmentData)it.next();
            if (assignment.getId() == id){
                return assignment;
            }
        }
        return null;
    }
    
    private CourseData getCourseObjByID(ResultSet resultSet, int id){
        Set<Course> courses = userData.getSetOfCourses();
        Iterator it = courses.iterator();
        while (it.hasNext()){
            CourseData course = (CourseData)it.next();
            if (course.getId() == id){
                return course;
            }
        }
        return null;
    }
    
    public void addTrainerToTrainersPerCourseList(TrainerData trainerData, CourseData courseData){
        Set<CourseTrainers> setOfTrainersPerCourse = userData.getSetOfTrainersPerCourse();
        Iterator it = setOfTrainersPerCourse.iterator();
        while (it.hasNext()){
           CourseTrainers trainersPerCourse = ((CourseTrainers) it.next());
           if (trainersPerCourse.getCourse().equals((Course)courseData)){
               Set<Trainer> setOfTrainers = trainersPerCourse.getSetOfComponents();
               for (Trainer tr : setOfTrainers){
                   if (tr.equals((Trainer)trainerData)){
                       System.out.printf("ERROR: Cannot insert trainer to course.\n" +
                                         "Reason: A trainer with firstname \"%s\", lastname \"%s\" and subject \"%s\" is already assigned to course \"%s %s %s\".\n", trainerData.getFirstName(), trainerData.getLastName(), trainerData.getSubject(), courseData.getTitle(), courseData.getStream(), courseData.getType()); 
                       return;
                   }
               }
               // Add trainer to the existing set of trainers for this course and save it to DB
               setOfTrainers.add((Trainer)trainerData);
               return;
               //System.out.printf("Trainer %s %s successfully added to course %s/%s/%s!%n", trainerData.getFirstName(), trainerData.getLastName(), courseData.getTitle(), courseData.getStream(), courseData.getType());
               //return;
           }
        }
        // It's the first time we add a trainer to this course
        CourseTrainersData trainersPerCourseData = new CourseTrainersData((Course)courseData);
        trainersPerCourseData.addToSet((Trainer)trainerData);
        userData.addTrainersPerCourseToSetOfTrainersPerCourse((CourseTrainers)trainersPerCourseData);
    }
    
    public void addStudentToStudentsPerCourseList(StudentData studentData, CourseData courseData){
        Set<CourseStudents> setOfStudentsPerCourse = userData.getSetOfStudentsPerCourse();
        Iterator it = setOfStudentsPerCourse.iterator();
        while (it.hasNext()){
            CourseStudents studentsPerCourse = ((CourseStudents) it.next());
           if (studentsPerCourse.getCourse().equals((Course) courseData)){
               Set<Student> setOfStudents = studentsPerCourse.getSetOfComponents();
               for (Student st : setOfStudents){
                   if (st.equals((Student)studentData)){
                      System.out.printf("ERROR: Cannot insert student to course.\n" +
                                        "Reason: A student with first name \"%s\", last name \"%s\" and birth date \"%s\" is already assigned to course with title: \"%s, %s, %s\".\n", studentData.getFirstName(), studentData.getLastName(), (studentData.getDateOfBirth()).format(formatter), courseData.getTitle(), courseData.getStream(), courseData.getType());
                      return;
                   }
               }
               // Add student to the existing set of students for this course and save it to DB
               setOfStudents.add((Student)studentData);
               return;
           }
        }
        // It's the first time we add a student to this course
        CourseStudentsData studentsPerCourseData = new CourseStudentsData((Course)courseData);
        studentsPerCourseData.addToSet((Student)studentData);
        userData.addStudentsPerCourseToSetOfStudentsPerCourse((CourseStudents)studentsPerCourseData);
    }
    
    public void addAssignmentToAssignmentsPerCourseList(AssignmentData assignmentData, CourseData courseData){
        if (courseData.getStartDate().isAfter(assignmentData.getSubDate()) || courseData.getEndDate().isBefore(assignmentData.getSubDate())){
            System.out.printf("ERROR: Cannot insert assignment to course.\n" +
                                "Reason: Assignment %s with submission date %s is not between course start date %s and course end date %s%n", assignmentData.getTitle(), (assignmentData.getSubDate()).format(formatter).toString(), (courseData.getStartDate()).format(formatter), (courseData.getEndDate()).format(formatter));                   
            return ;
        }
        Set<CourseAssignments> setOfAssignmentsPerCourse = userData.getSetOfAssignmentsPerCourse();
        Iterator it = setOfAssignmentsPerCourse.iterator();
        while (it.hasNext()){
           CourseAssignments assignmentsPerCourse = ((CourseAssignments) it.next());
           // Course already had assignments
           if (assignmentsPerCourse.getCourse().equals((Course) courseData)){
               Set<Assignment> setOfAssignments = assignmentsPerCourse.getSetOfComponents();
               for (Assignment as: setOfAssignments){
                   if (as.equals((Assignment) assignmentData)){
                       System.out.printf("ERROR: Cannot insert assignment to course.\n" +
                                         "Reason: An assignment with title \"%s\", submission date \"%s\" and passing grade \"%s\" already assigned to course with title: \"%s, %s, %s\".\n", assignmentData.getTitle(), (assignmentData.getSubDate()).format(formatter).toString(), assignmentData.getGrade(), courseData.getTitle(), courseData.getStream(), courseData.getType());
                       return;
                   }
               }
               // Add assignment to existing set of assignments for this course
               setOfAssignments.add((Assignment)assignmentData);
               return;                 
           }
        }
        // It's the first time we add an assignment to this course here
        CourseAssignmentsData assignmentsPerCourseData = new CourseAssignmentsData((Course)courseData);
        assignmentsPerCourseData.addToSet((Assignment)assignmentData);
        userData.addAssignmentsPerCourseToSetOfAssignmentsPerCourse((CourseAssignments)assignmentsPerCourseData);
    }
}
