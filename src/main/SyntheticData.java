package main;

import bootcamp.core.Assignment;
import bootcamp.core.Course;
import bootcamp.core.Student;
import bootcamp.core.Trainer;
import bootcamp.lists.CourseAssignments;
import bootcamp.lists.CourseStudents;
import bootcamp.lists.CourseTrainers;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class SyntheticData {
    private Set<Course> courses;
    private Set<Trainer> trainers;
    private Set<Assignment> assignments;
    private Set<Student> students;
    private Set<CourseStudents> setOfCourseStudents;
    private Set<CourseAssignments> setOfCourseAssignments;
    private Set<CourseTrainers> setOfCourseTrainers;
    
    public SyntheticData(){
        courses = new HashSet();
        trainers = new HashSet();
        assignments = new HashSet();
        students = new HashSet();
        setOfCourseStudents = new HashSet();
        setOfCourseAssignments = new HashSet();
        setOfCourseTrainers = new HashSet();
        createSyntheticData();
    }
    
    public Set<Course> getCourses() {
        return courses;
    }

    public Set<Trainer> getTrainers() {
        return trainers;
    }

    public Set<Assignment> getAssignments() {
        return assignments;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Set<CourseStudents> getStudentsPerCourses() {
        return setOfCourseStudents;
    }

    public Set<CourseAssignments> getAssignmentsPerCourses() {
        return setOfCourseAssignments;
    }

    public Set<CourseTrainers> getTrainersPerCourses() {
        return setOfCourseTrainers;
    }

    public Set<CourseStudents> getSetOfCourseStudents() {
        return setOfCourseStudents;
    }

    public Set<CourseAssignments> getSetOfCourseAssignments() {
        return setOfCourseAssignments;
    }

    public Set<CourseTrainers> getSetOfCourseTrainers() {
        return setOfCourseTrainers;
    }
    
    public void createSyntheticData(){
        Course courseJavaFull = new Course("CB-10", "Java", "Full-time", LocalDate.parse("2020-03-03"), LocalDate.parse("2020-06-05"));
        courses.add(courseJavaFull);
        Course courseJavaPart = new Course("CB-10", "Java", "Part-time", LocalDate.parse("2020-01-05"), LocalDate.parse("2020-06-10"));
        courses.add(courseJavaPart);
        Course courseJSPart = new Course("CB-11", "JavaScript", "Full-time", LocalDate.parse("2020-09-01"), LocalDate.parse("2021-01-10"));
        courses.add(courseJSPart);
        
        // TRAINERS DATA
        Set<Trainer> trainersJavaFull = new HashSet();
        Set<Trainer> trainersJavaPart = new HashSet();
        Set<Trainer> trainersJavaScript = new HashSet();
        Trainer trainer1 = new Trainer("Tasos", "Lelakis", "Java");
        Trainer trainer2 = new Trainer("George", "Pasparakis", "Csharp");
        Trainer trainer3 = new Trainer("Michalis", "Chamilos", "SQL");
        trainers.add(trainer1);
        trainers.add(trainer2);
        trainers.add(trainer3);
        trainersJavaFull.add(trainer1);
        trainersJavaFull.add(trainer2);
        trainersJavaFull.add(trainer3);
        trainersJavaPart.add(trainer1);
        trainersJavaPart.add(trainer3);
        trainersJavaScript.add(trainer2);
        setOfCourseTrainers.add(new CourseTrainers(courseJavaFull, trainersJavaFull));
        setOfCourseTrainers.add(new CourseTrainers(courseJavaPart, trainersJavaPart));
        setOfCourseTrainers.add(new CourseTrainers(courseJSPart, trainersJavaScript));
        
        // STUDENTS
        Set<Student> studentsJavaFull = new HashSet();
        Set<Student> studentsJavaPart = new HashSet();
        Set<Student> studentsJS = new HashSet();
        Student student1 = new Student("George", "Papadopoulos", LocalDate.parse("1991-07-10"), 2500d);
        Student student2 = new Student("Orestis", "Tanis", LocalDate.parse("1989-05-05"), 2500d);
        Student student3 = new Student("George", "Kourouzidis", LocalDate.parse("1993-11-11"), 2500d);
        Student student4 = new Student("Stelios", "Dimitriou", LocalDate.parse("1988-10-08"), 2500d);
        Student student5 = new Student("Petros", "Nikolopoulos", LocalDate.parse("1990-06-10"), 2500d);
        Student student6 = new Student("Dimitris", "Aggelopoulos", LocalDate.parse("1988-08-03"), 2500d);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        students.add(student6);
        studentsJavaFull.add(student1);
        studentsJavaFull.add(student2);
        studentsJavaFull.add(student3);
        studentsJavaPart.add(student3);
        studentsJavaPart.add(student2);
        studentsJavaPart.add(student4);
        studentsJavaPart.add(student6);
        studentsJS.add(student5);
        studentsJS.add(student3);
        studentsJS.add(student6);
        setOfCourseStudents.add(new CourseStudents(courseJavaFull, studentsJavaFull));
        setOfCourseStudents.add(new CourseStudents(courseJavaPart, studentsJavaPart));
        setOfCourseStudents.add(new CourseStudents(courseJSPart, studentsJS));
        
        // ASSIGNMENTS
        Set<Assignment> assignmentsJavaFull = new HashSet();
        Set<Assignment> assignmentsJavaPart = new HashSet();
        Set<Assignment> assignmentsJS = new HashSet();
        Assignment assignmentJavaFull1 = new Assignment("Individual Project Part A CB-10 Java Full-Time", "Individual project description", LocalDate.parse("2020-03-24"), 75);
        Assignment assignmentJavaFull2 = new Assignment("Individual Project Part B CB-10 Java Full-Time", "Individual project description", LocalDate.parse("2020-04-15"), 75);
        Assignment assignmentJavaPart1 = new Assignment("Individual Project Part A CB-10 Java Part-Time", "Individual project description", LocalDate.parse("2020-05-25"), 75);
        Assignment assignmentJavaPart2 = new Assignment("Individual Project Part B CB-10 Java Part-Time", "Team project description", LocalDate.parse("2020-06-15"), 75);
        Assignment assignmentJS1 = new Assignment("Individual Project Part A CB-11 JavaScript Part-time project", "Individual project description", LocalDate.parse("2020-09-29"), 75);
        Assignment assignmentJS2 = new Assignment("Individual Project Part B CB-11 JavaScript Part-time project", "Individual project description", LocalDate.parse("2020-10-16"), 75);
        assignments.add(assignmentJavaFull1);
        assignments.add(assignmentJavaFull2);
        assignments.add(assignmentJavaPart1);
        assignments.add(assignmentJavaPart2);
        assignments.add(assignmentJS1);
        assignments.add(assignmentJS2);
        assignmentsJavaFull.add(assignmentJavaFull1);
        assignmentsJavaFull.add(assignmentJavaFull2);
        assignmentsJavaPart.add(assignmentJavaPart1);
        assignmentsJavaPart.add(assignmentJavaPart2);
        assignmentsJS.add(assignmentJS1);
        assignmentsJS.add(assignmentJS2);
        setOfCourseAssignments.add(new CourseAssignments(courseJavaFull, assignmentsJavaFull));
        setOfCourseAssignments.add(new CourseAssignments(courseJavaPart, assignmentsJavaPart));
        setOfCourseAssignments.add(new CourseAssignments(courseJSPart, assignmentsJS));
    }
}
