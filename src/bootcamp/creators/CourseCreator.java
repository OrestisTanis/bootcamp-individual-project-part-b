package bootcamp.creators;

import appstate.UserData;
import bootcamp.core.Course;
import database.Database;
import database.models.CourseData;
import java.time.LocalDate;
import main.Input;

public class CourseCreator extends Creator {
    
    public CourseCreator(){
    }
    
    /* Methods */
    public void createCourses(UserData userData, Database db){
        String choice = "Y";
        while(choice.equalsIgnoreCase("Y")){
            String title = getTitleFromUser(titleRegex, titleInvalidMsg);
            String stream = getStreamFromUser(titleRegex, titleInvalidMsg);
            String type = getTypeFromUser(titleRegex, titleInvalidMsg);
            LocalDate startDate = getStartDateFromUserAfter(LocalDate.parse("01/01/2015", formatter));
            LocalDate endDate = getEndDateFromUser(startDate);
            CourseData courseData = new CourseData(title, stream, type, startDate, endDate);
            addCourse(courseData, userData, db);
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
    private void addCourse(CourseData courseData, UserData userData, Database db){
        if (!userData.addCourseToSetOfCourses((Course)courseData)){
            System.out.printf("ERROR: Cannot create course.\n" +
                              "Reason: A course with title \"%s\", stream \"%s\" and type \"%s\" already exists.\n",  
                               courseData.getTitle(), courseData.getStream(), courseData.getType());
            return;
        }
        if (!courseData.insertRecordToCourses(db)){
            System.out.print("ERROR: Cannot create course.\n" +
                              "Reason: There was an error while communicating with the database.\n");
            // Delete the object that was just saved so local data are in sync with db
            userData.removeCourseFromSetOfCourses((Course)courseData);
            return;
        }
        System.out.printf("\nCourse \"%s\" successfuly created!", courseData.getTitle());
    }

}
