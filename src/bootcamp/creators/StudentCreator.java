package bootcamp.creators;

import appstate.UserData;
import bootcamp.core.Student;
import database.Database;
import database.models.StudentData;
import java.time.LocalDate;
import main.Input;

public class StudentCreator extends Creator {
  
    public StudentCreator() {
    }

    /* Methods */
     public void createStudents(UserData userData, Database db) {
        String choice = "Y";
        
        while (choice.equalsIgnoreCase("Y")) {
            // Get input from user
            String firstName = getFirstNameFromUser(nameRegex, nameInvalidMsg);
            String lastName = getLastNameFromUser(nameRegex, nameInvalidMsg);
            LocalDate birthDate = getBirthDateFromUser(LocalDate.parse("01/01/1950", formatter), LocalDate.now().minusYears(18));
            Double tuitionFees = getFeesFromUser();    
            
            // Store data
            StudentData studentData = new StudentData(firstName, lastName, birthDate, tuitionFees);
            addStudent(studentData, userData, db);
           
            System.out.println("\nDo you want to create another Student? (Y/N)");
            choice = Input.getString("[yYnN]", "Y/N?");
        }
        
    }
    private String getFirstNameFromUser(String nameRegex, String nameInvalidMsg){
        System.out.println("\nPlease enter student first name:");
        return Input.getString(nameRegex, nameInvalidMsg);
    }
    private String getLastNameFromUser(String nameRegex, String nameInvalidMsg){
        System.out.println("\nPlease enter student last name:");
        return Input.getString(nameRegex, nameInvalidMsg);
    }
    private LocalDate getBirthDateFromUser(LocalDate minDate, LocalDate maxDate){
        String minDateStr = minDate.format(formatter);
        String maxDateStr = maxDate.format(formatter);
        String invalidDateMsg = getInvalidDateBetweenMsg(minDateStr, maxDateStr);
        System.out.printf("\nPlease enter student date of birth (%s): \n", dateFormatStr);
        return Input.getLocalDateFromTo(minDate, maxDate, dateFormatStr, invalidDateMsg);
    }
    private Double getFeesFromUser(){
        System.out.println("\nPlease enter student tuition fees: ");
        double result = Input.getPositiveDouble();  
        return result;
    }
    private void addStudent(StudentData studentData, UserData userData, Database db){
        if (!userData.addStudentToSetOfStudents((Student)studentData)){
            System.out.printf("ERROR: Cannot create student.\n" +
                              "Reason: A student with firstname \"%s\", lastname \"%s\", and birth date \"%s\" already exists.\n",  
                               studentData.getFirstName(), studentData.getLastName(), studentData.getDateOfBirth());
            return;
        }
        if (!studentData.insertRecordToStudents(db)){
            System.out.print("ERROR: Cannot create student.\n" +
                              "Reason: There was an error while communicating with the database.\n");
            // Delete the object that was just saved so local data are in sync with db
            userData.removeStudentFromSetOfStudents((Student) studentData);
            return;
        }
        System.out.printf("\nStudent %s %s successfuly created!", studentData.getFirstName(), studentData.getLastName());
    }
}
