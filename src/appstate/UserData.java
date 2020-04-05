package appstate;

import bootcamp.core.Assignment;
import bootcamp.core.Course;
import bootcamp.core.Student;
import bootcamp.core.Trainer;
import bootcamp.lists.CourseAssignments;
import bootcamp.lists.CourseStudents;
import bootcamp.lists.CourseTrainers;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserData {
    private Set<Course> setOfCourses;
    private Set<Trainer> setOfTrainers;
    private Set<Student> setOfStudents;
    private Set<Assignment> setOfAssignments;
    private Set<CourseTrainers> setOfTrainersPerCourse;
    private Set<CourseStudents> setOfStudentsPerCourse;
    private Set<CourseAssignments> setOfAssignmentsPerCourse;
    
    public UserData(){
        setOfCourses = new HashSet();
        setOfTrainers = new HashSet();
        setOfStudents = new HashSet();
        setOfAssignments = new HashSet();
        setOfTrainersPerCourse = new HashSet();
        setOfStudentsPerCourse = new HashSet();
        setOfAssignmentsPerCourse = new HashSet();
    }

    public Set<Course> getSetOfCourses() {
        return setOfCourses;
    }

    public Set<Trainer> getSetOfTrainers() {
        return setOfTrainers;
    }

    public Set<Student> getSetOfStudents() {
        return setOfStudents;
    }

    public Set<Assignment> getSetOfAssignments() {
        return setOfAssignments;
    }

    public Set<CourseTrainers> getSetOfTrainersPerCourse() {
        return setOfTrainersPerCourse;
    }

    public Set<CourseStudents> getSetOfStudentsPerCourse() {
        return setOfStudentsPerCourse;
    }

    public Set<CourseAssignments> getSetOfAssignmentsPerCourse() {
        return setOfAssignmentsPerCourse;
    }

    public void setSetOfCourses(Set<Course> setOfCourses) {
        this.setOfCourses = setOfCourses;
    }

    public void setSetOfTrainers(Set<Trainer> setOfTrainers) {
        this.setOfTrainers = setOfTrainers;
    }

    public void setSetOfStudents(Set<Student> setOfStudents) {
        this.setOfStudents = setOfStudents;
    }

    public void setSetOfAssignments(Set<Assignment> setOfAssignments) {
        this.setOfAssignments = setOfAssignments;
    }

    public void setSetOfTrainersPerCourse(Set<CourseTrainers> setOfTrainersPerCourse) {
        this.setOfTrainersPerCourse = setOfTrainersPerCourse;
    }

    public void setSetOfStudentsPerCourse(Set<CourseStudents> setOfStudentsPerCourse) {
        this.setOfStudentsPerCourse = setOfStudentsPerCourse;
    }

    public void setSetOfAssignmentsPerCourse(Set<CourseAssignments> setOfAssignmentsPerCourse) {
        this.setOfAssignmentsPerCourse = setOfAssignmentsPerCourse;
    }
    
    
    
    public boolean addCourseToSetOfCourses(Course course){
        return setOfCourses.add(course);
    }
    
    public boolean addTrainerToSetOfTrainers(Trainer trainer){
        return setOfTrainers.add(trainer);
    }
    
    public boolean addAssignmentToSetOfAssignments(Assignment assignment){
        return setOfAssignments.add(assignment);
    }
    
    public boolean addStudentToSetOfStudents(Student student){
        return setOfStudents.add(student);
    }
    
    public boolean addStudentsPerCourseToSetOfStudentsPerCourse(CourseStudents studentsPerCourse){
        return setOfStudentsPerCourse.add(studentsPerCourse);
    }
    
    public boolean addAssignmentsPerCourseToSetOfAssignmentsPerCourse(CourseAssignments assignmentsPerCourse){
        return setOfAssignmentsPerCourse.add(assignmentsPerCourse);
    }
    
    public boolean addTrainersPerCourseToSetOfTrainersPerCourse(CourseTrainers trainersPerCourse){
        return setOfTrainersPerCourse.add(trainersPerCourse);
    }
    
    public boolean setOfCoursesIsEmpty(){
        return setOfCourses.isEmpty();
    }
    
    public boolean setOfTrainersIsEmpty(){
        return setOfTrainers.isEmpty();
    }
    
    public boolean setOfStudentsIsEmpty(){
        return setOfStudents.isEmpty();
    }
    
    public boolean setOfAssignmentsIsEmpty(){
        return setOfAssignments.isEmpty();
    }
    
    public boolean setOfAssignmentsPerCourseIsEmpty(){
        return setOfAssignmentsPerCourse.isEmpty();
    }
    
    public boolean setOfTrainersPerCourseIsEmpty(){
        return setOfTrainersPerCourse.isEmpty();
    }
    
    public boolean setOfStudenrsPerCourseIsEmpty(){
        return setOfStudentsPerCourse.isEmpty();
    }
    
    public static Map<Student, Set<Course>> getCoursesPerStudentMap(Set<CourseStudents> setOfStudentsPerCourse, Set<Student> allStudents) {
        Map<Student, Set<Course>> coursesPerStudentMap = new HashMap();
        for (Student student : allStudents) { // for every student
            for (CourseStudents studentsPerCourse : setOfStudentsPerCourse) { // for every course 
                // If the student belongs in this course
                if (studentsPerCourse.containedInSet(student)) {
                    Set<Course> courses = new HashSet();
                    // If student is already added to the map
                    if (coursesPerStudentMap.containsKey(student)) {
                        courses = coursesPerStudentMap.get(student);
                        // add the new course and replace the value in the map
                        courses.add(studentsPerCourse.getCourse());
                        coursesPerStudentMap.put(student, courses); // update the entry
                    } else { // if student is not already in the map, add the first entry
                        courses.add(studentsPerCourse.getCourse());
                        coursesPerStudentMap.put(student, courses);
                    }
                }
            }
        }
        return coursesPerStudentMap;
    }
 
    public static Map<Student, Set<Assignment>> getAssignmentsPerStudentMap(Set<CourseAssignments> setOfAssignmentsPerCourse, Set<CourseStudents> setOfStudentsPerCourse, Set<Student> allStudents) {
        Map<Student, Set<Course>> coursesPerStudentMap = getCoursesPerStudentMap(setOfStudentsPerCourse, allStudents);
        Map<Student, Set<Assignment>> assignmentsPerStudentMap = new HashMap();
        for (Student student : allStudents) {
            for (CourseAssignments assignmentsPerCourse : setOfAssignmentsPerCourse) { //for every course
                if (coursesPerStudentMap.get(student).contains(assignmentsPerCourse.getCourse())) { //if student is in the course
                    Set<Assignment> assignments = new HashSet();
                    // If student is already added to the map
                    if (assignmentsPerStudentMap.containsKey(student)) {
                        assignments = assignmentsPerStudentMap.get(student);
                        // add the new assignments and replace the value in the map
                        assignments.addAll(assignmentsPerCourse.getSetOfComponents());
                        assignmentsPerStudentMap.put(student, assignments); // update the entry
                    } else { // if student is not already in the map, add the first entry
                        assignments.addAll(assignmentsPerCourse.getSetOfComponents());
                        assignmentsPerStudentMap.put(student, assignments);
                    }
                }
            }
        }
        return assignmentsPerStudentMap;
    }
}
