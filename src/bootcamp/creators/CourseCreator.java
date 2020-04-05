package bootcamp.creators;

import appstate.UserData;
import bootcamp.core.Course;
import java.time.LocalDate;
import main.Input;

public class CourseCreator extends Creator {
    
    public CourseCreator(){
    }
    
    /* Methods */
    public void createCourses(UserData userData){
        String choice = "Y";
        while(choice.equalsIgnoreCase("Y")){
            String title = getTitleFromUser(titleRegex, titleInvalidMsg);
            String stream = getStreamFromUser(titleRegex, titleInvalidMsg);
            String type = getTypeFromUser(titleRegex, titleInvalidMsg);
            LocalDate startDate = getStartDateFromUserAfter(LocalDate.parse("01/01/2015", formatter));
            LocalDate endDate = getEndDateFromUser(startDate);
            Course course = new Course(title, stream, type, startDate, endDate);
            addCourseToSetOfCourses(course, userData);
            System.out.println("\nDo you want to create another course? (Y/N)");
            choice = Input.getString("[yYnN]", "Y/N?");
        }
    }
    private String getTitleFromUser(String titleRegex, String titleInvalidMsg){
        System.out.println("\nPlease enter course title:");
        return Input.getString(titleRegex, titleInvalidMsg);
    }
    private String getStreamFromUser(String titleRegex, String titleInvalidMsg){
        System.out.println("\nPlease enter course stream name: ");
        return Input.getString(titleRegex, titleInvalidMsg);
    }
    private String getTypeFromUser(String titleRegex, String titleInvalidMsg){
        System.out.println("\nPlease enter course type: ");
        return Input.getString(titleRegex, titleInvalidMsg);
    }
    private LocalDate getStartDateFromUserAfter(LocalDate minDate){
        System.out.printf("\nPlease enter course start date (%s): \n", dateFormatStr);
        //String invalidDateMsg = getInvalidWorkDateAfterMsg(minDate);
        return Input.getWorkDateAfter(minDate, dateFormatStr);
    }
    private LocalDate getEndDateFromUser(LocalDate startDate){
        System.out.printf("\nPlease enter course end date (%s): \n", dateFormatStr);
        //String invalidDateMsg = getInvalidWorkDateAfterMsg(startDate);
        return Input.getWorkDateAfter(startDate, dateFormatStr);
    }
    private void addCourseToSetOfCourses(Course course, UserData userData){
        if (!userData.addCourseToSetOfCourses(course)){
            System.out.printf("Course %s %s %s with starting date %s and ending date %s already exists!%n",  
                    course.getTitle(), course.getStream(), course.getType(), course.getStartDate(), course.getEndDate());
            return;
        }
        
        System.out.printf("\nCourse \"%s\" successfuly created!", course.getTitle());
    }

}
