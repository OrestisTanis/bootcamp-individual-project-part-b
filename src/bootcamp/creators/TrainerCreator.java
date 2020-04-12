package bootcamp.creators;

import appstate.UserData;
import bootcamp.core.Trainer;
import database.Database;
import database.models.TrainerData;
import main.Input;

public class TrainerCreator extends Creator {
  
    public TrainerCreator() {
    }
   
    /* Methods */
    public void createTrainers(UserData userData, Database db) {
        String choice = "Y";
        while (choice.equalsIgnoreCase("Y")) {
            // Get input from user
            String firstName = getFirstNameFromUser(nameRegex, nameInvalidMsg);
            String lastName = getLastNameFromUser(nameRegex, nameInvalidMsg);
            String subject = getSubjectFromUser(nameRegex, nameInvalidMsg);
            
            // Store data
            TrainerData trainerData = new TrainerData(firstName, lastName, subject);
            addTrainer(trainerData, userData, db);
            
            System.out.println("\nDo you want to create another Trainer? (Y/N)");
            System.out.println(trainerData);
            choice = Input.getString("[yYnN]", "Y/N?");
        }
    }
    private String getFirstNameFromUser(String nameRegex, String nameInvalidMsg){
        System.out.println("\nPlease enter trainer first name:");
        return Input.getString(nameRegex, nameInvalidMsg);
    }
    private String getLastNameFromUser(String nameRegex, String nameInvalidMsg){
        System.out.println("\nPlease enter trainer last name:");
        return Input.getString(nameRegex, nameInvalidMsg);
    }
    private String getSubjectFromUser(String nameRegex, String nameInvalidMsg){
        System.out.println("\nPlease enter trainer subject:");
        return Input.getString(nameRegex, nameInvalidMsg);           
    }
    private void addTrainer(TrainerData trainerData, UserData userData, Database db){
        if (!userData.addTrainerToSetOfTrainers((Trainer)trainerData)){
            System.out.printf("ERROR: Cannot create trainer.\n" +
                               "Reason: A trainer with firstname \"%s\", lastname \"%s\", and subject \"%s\" already exists.\n",  
                               trainerData.getFirstName(), trainerData.getLastName(), trainerData.getSubject());
            return;
        }
        if (!trainerData.insertRecordToTrainers(db)){
            System.out.print("ERROR: Cannot create trainer.\n" +
                              "Reason: There was an error while communicating with the database.\n");
            // Delete the object that was just saved so local data are in sync with db
            userData.removeTrainerFromSetOfTrainers((Trainer)trainerData);
            return;
        }
        System.out.printf("\nTrainer \"%s %s\" was successfuly created!", trainerData.getFirstName(), trainerData.getLastName());
    }
}
