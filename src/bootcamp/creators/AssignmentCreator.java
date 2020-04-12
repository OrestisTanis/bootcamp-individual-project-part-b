package bootcamp.creators;

import appstate.UserData;
import bootcamp.core.Assignment;
import database.Database;
import database.models.AssignmentData;
import java.time.LocalDate;
import main.Input;

public class AssignmentCreator extends Creator {

    public AssignmentCreator(){
    }
    
    /* Methods */
    public void createAssignments(UserData userData, Database db){
        String choice = "Y";
     
        while(choice.equalsIgnoreCase("Y")){
            String title = getTitleFromUser(titleRegex, titleInvalidMsg);
            String description = getDescriptionFromUser(titleRegex, titleInvalidMsg);
            LocalDate subDate = getSubDateFromUser(LocalDate.parse("01/01/2015", formatter));
            int grade = getGrade();
            AssignmentData assignmentData = new AssignmentData(title, description, subDate, grade);
            addAssignment(assignmentData, userData, db);
            System.out.println("\nDo you want to create another assignment? (Y/N)");
            choice = Input.getString("[yYnN]", "Y/N?");
        }
    }
    private String getTitleFromUser(String titleRegex, String titleInvalidMsg){
        System.out.println("\nPlease enter assignment title:");
        return Input.getString(titleRegex, titleInvalidMsg);
    }
    private String getDescriptionFromUser(String titleRegex, String titleInvalidMsg){
        System.out.println("\nPlease enter assignment description: ");
        return Input.getString(titleRegex, titleInvalidMsg);
    }
    private LocalDate getSubDateFromUser(LocalDate minDate){
        System.out.printf("\nPlease enter assignment submission date (%s): \n", dateFormatStr);
        //LocalDate minDate = ;
        //tring dateInvalidMsg = getInvalidWorkDateAfterMsg(minDate);
        return Input.getWorkDateAfter(minDate, dateFormatStr);
    }
    private int getGrade(){
        System.out.println("\nPlease enter the assignment's passing grade: ");
        return Input.getIntFromTo(1, 100);
    }
    private void addAssignment(AssignmentData assignmentData, UserData userData, Database db){
        if (!userData.addAssignmentToSetOfAssignments((Assignment) assignmentData)){
            System.out.printf("ERROR: Cannot create assignment.\n" +
                              "Reason: An assignment with title \"%s\", submission date \"%s\" and passing grade \"%s\" already exists.\n", assignmentData.getTitle(), assignmentData.getSubDate().toString(), assignmentData.getGrade());
            return;
        }
        if (!assignmentData.insertRecordToAssignments(db)){
            System.out.print("ERROR: Cannot create assignment.\n" +
                              "Reason: There was an error while communicating with the database.\n");
            // Delete the object that was just saved so local data are in sync with db
            userData.removeAssignmentFromSetOfAssignments((Assignment)assignmentData);
            return;
        }
        System.out.printf("\nAssignment \"%s\" successfuly created!", assignmentData.getTitle());
    }
}
