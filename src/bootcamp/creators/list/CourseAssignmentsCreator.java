package bootcamp.creators.list;

import appstate.UserData;
import bootcamp.core.Course;
import bootcamp.core.Assignment;
import bootcamp.lists.CourseAssignments;
import java.util.Iterator;
import java.util.Set;
import interfaces.DateFormatable;
import main.Input;

public class CourseAssignmentsCreator implements DateFormatable {
    
    public CourseAssignmentsCreator(){
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
    
    public void createAssignmentsPerCourse(UserData userData){
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
            Assignment assignment = getAssignmentFromUser(userData);
            Course course = getCourseFromUser(assignment, userData);
            addAssignmentToAssignmentsPerCourseList(assignment, course, userData);
            System.out.println("\nDo you want to insert another Assignment to a course? (Y/N)");
            choice = Input.getString("[yYnN]", "Y/N?");
        }
    }
    
    public void addAssignmentToAssignmentsPerCourseList(Assignment assignment, Course course, UserData userData){
        if (course.getStartDate().isAfter(assignment.getSubDateTime()) || course.getEndDate().isBefore(assignment.getSubDateTime())){
            System.out.printf("Assignment %s with submission date %s is not between course start date %s and course end date %s%n", assignment.getTitle(), (assignment.getSubDateTime()).format(formatter).toString(), (course.getStartDate()).format(formatter), (course.getEndDate()).format(formatter));                   
            return ;
        }
        
        Set<CourseAssignments> setOfAssignmentsPerCourse = userData.getSetOfAssignmentsPerCourse();
        Iterator it = setOfAssignmentsPerCourse.iterator();
        while (it.hasNext()){
           CourseAssignments assignmentsPerCourse = ((CourseAssignments) it.next());
           if (assignmentsPerCourse.getCourse() == course){
               Set<Assignment> setOfAssignments = assignmentsPerCourse.getSetOfComponents();
               for (Assignment as: setOfAssignments){
                   if (as.equals(assignment)){
                       System.out.printf("Assignment %s with submission date %s and total mark %s already assigned to course %s/%s/%s!%n", assignment.getTitle(), (assignment.getSubDateTime()).format(formatter).toString(), assignment.getTotalMark(), course.getTitle(), course.getStream(), course.getType());
                       return;
                   }
               }
               setOfAssignments.add(assignment);
               System.out.printf("Assignment %s successfully added to course %s/%s/%s!%n", assignment.getTitle(), course.getTitle(), course.getStream(), course.getType());                                
               return;
           }
        }
        // If execution reaches here, that means there is no trainersPerCourse obj holding the course specified by the user
        CourseAssignments assignmentsPerCourse = new CourseAssignments(course);
        assignmentsPerCourse.addToSet(assignment);
        userData.addAssignmentsPerCourseToSetOfAssignmentsPerCourse(assignmentsPerCourse);
        System.out.printf("Assignment %s successfully added to course %s/%s/%s!%n", assignment.getTitle(), course.getTitle(), course.getStream(), course.getType());     
    }
}
