package bootcamp.creators;

import appstate.UserData;
import bootcamp.core.Trainer;
import main.Input;


public class TrainerCreator extends Creator {
  
    public TrainerCreator() {
    }
   
    /* Methods */
    public void createTrainers(UserData userData) {
        String choice = "Y";
        while (choice.equalsIgnoreCase("Y")) {
            String firstName = getFirstNameFromUser(nameRegex, nameInvalidMsg);
            String lastName = getLastNameFromUser(nameRegex, nameInvalidMsg);
            String subject = getSubjectFromUser(nameRegex, nameInvalidMsg);
            Trainer trainer = new Trainer(firstName, lastName, subject);
            addTrainerToSetOfTrainers(trainer, userData);
            System.out.println("\nDo you want to create another Trainer? (Y/N)");
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
    private void addTrainerToSetOfTrainers(Trainer trainer, UserData userData){
        if (!userData.addTrainerToSetOfTrainers(trainer)){
            String inputInvalidStr = "Trainer %s %s with subject %s already exists!%n";
            System.out.printf(inputInvalidStr, trainer.getFirstName(), trainer.getLastName(), trainer.getSubject());
            return;
        }
        
        System.out.printf("\nTrainer %s %s successfuly created!", trainer.getFirstName(), trainer.getLastName());
    }
}
