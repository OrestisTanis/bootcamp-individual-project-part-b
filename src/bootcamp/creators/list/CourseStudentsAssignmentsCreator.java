package bootcamp.creators.list;

import appstate.UserData;
import bootcamp.core.Assignment;
import bootcamp.core.Course;
import bootcamp.core.Student;
import bootcamp.creators.Creator;
import database.Database;
import database.models.AssignmentData;
import database.models.CourseData;
import database.models.CourseStudentsAssignmentsData;
import database.models.StudentData;
import java.time.LocalDate;
import java.util.Set;
import main.Input;

public class CourseStudentsAssignmentsCreator extends Creator {
    
    public void createSubmittedAssignments(UserData userData, Database db){
        String choice = "Y";
        
        while(choice.equalsIgnoreCase("Y")){
            if (userData.setOfCoursesIsEmpty()){
                System.out.println("No available courses to insert submitted assignments to. Returning to main menu.");
                return;
            }
            if (userData.setOfStudentsIsEmpty()){
                System.out.println("No available students to assign a submitted assignment to. Returning to main menu.");
                return;
            }
            if (userData.setOfAssignmentsIsEmpty()){
               System.out.println("No available assignments to submit. Returning to main menu.");
               return;
            }
            
            // Get course ID from user
            System.out.printf("\nChoose a course: \n");
            CourseData courseData = (CourseData) Input.getCourseFromUser(userData);
            int courseID = courseData.getId();
            
            // Get the assignments belonging to the course using local data
            Set<Assignment> setOfAssignments = userData.getSetOfAssignmentsBelongingToCourse((Course)courseData);
            if (setOfAssignments != null && setOfAssignments.size() > 0){
                // Get the students belonging to the course using local data
                Set<Student> setOfStudents = userData.getSetOfStudentsBelongingToCourse((Course)courseData);
                System.out.println("\nChose a student:");
                // Display the students as options to the user and get the object corresponding to the user's choice
                Input.printOptionsFromSet(setOfStudents);
                Student selectedStudent = (Student) Input.getOptionFromSet(setOfStudents);
                
                // Display the students as options to the user and get the object corresponding to the user's choice
                System.out.println("\nChoose an assignment:");
                Input.printOptionsFromSet(setOfAssignments);
                Assignment selectedAssignment = (Assignment) Input.getOptionFromSet(setOfAssignments);
                
                // Get submission date from user
                LocalDate subDate = getSubDateFromUser(courseData);

                // Get grade form user
                int grade = getGrade();

                // Insert data to DB
                CourseStudentsAssignmentsData submittedAssignment = new CourseStudentsAssignmentsData(courseID, ((StudentData)selectedStudent).getId(), ((AssignmentData)selectedAssignment).getId(), subDate, grade);
                saveToDB(submittedAssignment, db);
            }
            else {
                System.out.println("There are no assignments in this course that you can submit for.");
            }
            
            System.out.println("\nDo you want to submit another Assignment to a course? (Y/N)");
            choice = Input.getString("[yYnN]", "Y/N?");
        }
    }
    
    private int getGrade(){
        System.out.println("\nPlease enter the student's grade: ");
        return Input.getIntFromTo(0, 100);
    }
    
    private LocalDate getSubDateFromUser(CourseData courseData){
        String invalidDateMsg = getInvalidDateBetweenMsg(courseData.getStartDate().format(formatter), courseData.getEndDate().format(formatter));
        System.out.printf("\nPlease enter assignment submission date (%s): \n", dateFormatStr);
        return Input.getLocalDateFromTo(courseData.getStartDate(), courseData.getEndDate(), getDateFormat(), invalidDateMsg);
    }
    
    private void saveToDB(CourseStudentsAssignmentsData courseStudentsAssignmentsData, Database db){
         // Save to DB
        if (!courseStudentsAssignmentsData.insertRecordToAssignmentsPerStudentPerCourse(db)){
            System.out.println("ERROR: Cannot insert assignment to course.\n" +
                               "Reason: Selected assignment is already submitted for the chosen student.\n");
            return;
        }
        System.out.println("Assignment was successfully submitted.");
        //System.out.printf("Assignment with title \"%s\" was successfully added as submitted assignment to student \"%s %s\" for course \"%s %s %s\".\n", assignmentData.getTitle(), course.getTitle(), course.getStream(), course.getType()); 
    }
}
