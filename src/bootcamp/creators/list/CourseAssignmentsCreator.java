package bootcamp.creators.list;

import appstate.UserData;
import bootcamp.core.Course;
import bootcamp.core.Assignment;
import bootcamp.lists.CourseAssignments;
import database.Database;
import database.models.AssignmentData;
import database.models.CourseAssignmentsData;
import database.models.CourseData;
import java.util.Iterator;
import java.util.Set;
import interfaces.DateFormatable;
import main.Input;

public class CourseAssignmentsCreator implements DateFormatable {
    
    public CourseAssignmentsCreator(){
    }
    
    public void createAssignmentsPerCourse(UserData userData, Database db){
      String choice = "Y";
        
        while(choice.equalsIgnoreCase("Y")){
            if (userData.setOfCoursesIsEmpty()){
                System.out.println("No available courses to insert assignments to. Returning to main menu.");
                return;
            }
            if (userData.setOfAssignmentsIsEmpty()){
               System.out.println("No available assignments to insert. Returning to main menu.");
               return;
            }
            
            // Get input from user
            AssignmentData assignmentData = (AssignmentData) getAssignmentFromUser(userData);
            CourseData courseData = (CourseData) getCourseFromUser((Assignment) assignmentData, userData);
            
            // Store data
            addAssignmentToAssignmentsPerCourseList(assignmentData, courseData, userData, db);
            
            System.out.println("\nDo you want to insert another Assignment to a course? (Y/N)");
            choice = Input.getString("[yYnN]", "Y/N?");
        }
    }
    
    private Assignment getAssignmentFromUser(UserData userData){
        System.out.println("\nChoose an assignment to insert to a course: ");
        Set setOfAssignments = userData.getSetOfAssignments();
        Input.printOptionsFromSet(setOfAssignments);
        Assignment assignment = (Assignment) Input.getOptionFromSet(setOfAssignments);
        return assignment;
    }
    
    private Course getCourseFromUser(Assignment assignment, UserData userData){
        System.out.printf("\nChoose a course to insert the assignment %s to:\n", assignment.getTitle());
        Set setOfCourses = userData.getSetOfCourses();
        Input.printOptionsFromSet(setOfCourses);
        Course course = (Course)Input.getOptionFromSet(setOfCourses);
        return course;
    }
    
    public void addAssignmentToAssignmentsPerCourseList(AssignmentData assignmentData, CourseData courseData, UserData userData, Database db){        
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
               saveToDB(assignmentData, (CourseAssignmentsData)assignmentsPerCourse, userData, db);
               return; 
           }
        }
        // It's the first time we add an assignment to this course here
        CourseAssignmentsData assignmentsPerCourseData = new CourseAssignmentsData((Course)courseData);
        assignmentsPerCourseData.addToSet((Assignment)assignmentData);
        userData.addAssignmentsPerCourseToSetOfAssignmentsPerCourse((CourseAssignments)assignmentsPerCourseData);
        saveToDB(assignmentData, assignmentsPerCourseData, userData, db);
    }
    
    public void saveToDB(AssignmentData assignmentData, CourseAssignmentsData assignmentsPerCourseData, UserData userData, Database db){
        Course course = assignmentsPerCourseData.getCourse();
         // Save to DB
        if (!assignmentsPerCourseData.insertRecordToEnrollmentsAssignments(assignmentData, db)){
            System.out.println("ERROR: Cannot insert assignment to course.\n" +
                             "Reason: There was an error while communicating with the database.\n");
            // Delete the object that was previously stored locally so local data are in sync with db
            userData.removeAssignmentsPerCourseFromSetOfAssignmentsPerCourse((CourseAssignments)assignmentsPerCourseData);
            return;
        }
        System.out.printf("Assignment with title \"%s\" was successfully added to course \"%s %s %s\".\n", assignmentData.getTitle(), course.getTitle(), course.getStream(), course.getType()); 
    }
}
