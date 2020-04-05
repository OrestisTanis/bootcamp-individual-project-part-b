package bootcamp.creators;

import appstate.UserData;
import bootcamp.core.Student;
import java.time.LocalDate;
import main.Input;

public class StudentCreator extends Creator {
  
    public StudentCreator() {
    }

    /* Methods */
     public void createStudents(UserData userData) {
        String choice = "Y";
        
        while (choice.equalsIgnoreCase("Y")) {
            String firstName = getFirstNameFromUser(nameRegex, nameInvalidMsg);
            String lastName = getLastNameFromUser(nameRegex, nameInvalidMsg);
            LocalDate birthDate = getBirthDateFromUser(LocalDate.parse("01/01/1950", formatter), LocalDate.now().minusYears(18));
            Double tuitionFees = getFeesFromUser();              
            Student student = new Student(firstName, lastName, birthDate, tuitionFees);
            addStudentToSetOfStudents(student, userData);
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
    private void addStudentToSetOfStudents(Student student, UserData userData){
        if (!userData.addStudentToSetOfStudents(student)){
            System.out.printf("Student %s %s with birth date %s already exists!%n", student.getFirstName(), student.getLastName(), student.getDateOfBirth());
            return;
        }
        System.out.printf("\nStudent %s %s successfuly created!", student.getFirstName(), student.getLastName());
    }
}
