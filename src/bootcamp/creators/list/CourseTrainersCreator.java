package bootcamp.creators.list;

import appstate.UserData;
import bootcamp.core.Course;
import bootcamp.core.Trainer;
import bootcamp.lists.CourseTrainers;
import database.Database;
import database.models.CourseData;
import database.models.CourseTrainersData;
import database.models.TrainerData;
import java.util.Iterator;
import java.util.Set;
import main.Input;

public class CourseTrainersCreator {

    public CourseTrainersCreator(){
    }
    
    public void createTrainersPerCourse(UserData userData, Database db){
        String choice = "Y";
        
        while(choice.equalsIgnoreCase("Y")){
            if (userData.setOfTrainersIsEmpty()){
                System.out.println("No available trainers to assign. Returning to main menu.");
                return;
            }
            if (userData.setOfCoursesIsEmpty()){
                System.out.println("No available course to assign trainers to. Returning to main menu.");
                return;
            }
            
            // Get input from user
            TrainerData trainerData = (TrainerData) getTrainerFromUser(userData);
            CourseData courseData = (CourseData) getCourseFromUser((Trainer) trainerData, userData);
            
            // Store Data
            addTrainerToTrainersPerCourseList(trainerData, courseData, userData, db);
            
            System.out.println("\nDo you want to insert another Trainer to a course? (Y/N)");
            choice = Input.getString("[yYnN]", "Y/N?");
        }
    }
    
     public Trainer getTrainerFromUser(UserData userData){
        Set setOfTrainers = userData.getSetOfTrainers();
        System.out.println("\nChoose a trainer to assign to a course: ");
        Input.printOptionsFromSet(setOfTrainers);
        Trainer trainer = (Trainer) Input.getOptionFromSet(setOfTrainers);
        return trainer;
    }
    
    public Course getCourseFromUser(Trainer trainer, UserData userData){
        System.out.printf("\nChoose a course to assign trainer %s to:\n", trainer);
        Set setOfCourses = userData.getSetOfCourses();
        Input.printOptionsFromSet(setOfCourses);
        Course course = (Course)Input.getOptionFromSet(setOfCourses);
        return course;
    }
    
    public void addTrainerToTrainersPerCourseList(TrainerData trainerData, CourseData courseData, UserData userData, Database db){
        Set<CourseTrainers> setOfTrainersPerCourse = userData.getSetOfTrainersPerCourse();
        Iterator it = setOfTrainersPerCourse.iterator();
        while (it.hasNext()){
           CourseTrainers trainersPerCourse = ((CourseTrainers) it.next());
           if (trainersPerCourse.getCourse().equals((Course)courseData)){
               Set<Trainer> setOfTrainers = trainersPerCourse.getSetOfComponents();
               for (Trainer tr : setOfTrainers){
                   if (tr.equals((Trainer)trainerData)){
                       System.out.printf("ERROR: Cannot insert trainer to course.\n" +
                                         "Reason: A trainer with firstname \"%s\", lastname \"%s\" and subject \"%s\" is already assigned to course \"%s %s %s\".\n", trainerData.getFirstName(), trainerData.getLastName(), trainerData.getSubject(), courseData.getTitle(), courseData.getStream(), courseData.getType()); 
                       return;
                   }
               }
               // Add trainer to the existing set of trainers for this course and save it to DB
               setOfTrainers.add((Trainer)trainerData);
               saveToDB(trainerData, (CourseTrainersData) trainersPerCourse, userData, db);
               return;
           }
        }
        // It's the first time we add a trainer to this course
        CourseTrainersData trainersPerCourseData = new CourseTrainersData(courseData);
        trainersPerCourseData.addToSet((Trainer)trainerData);
        userData.addTrainersPerCourseToSetOfTrainersPerCourse((CourseTrainers)trainersPerCourseData);
        saveToDB(trainerData, trainersPerCourseData, userData, db);
    }
    
    public void saveToDB(TrainerData trainerData, CourseTrainersData trainersPerCourseData, UserData userData, Database db){
        Course course = trainersPerCourseData.getCourse();
        // Save to DB
        if (!trainersPerCourseData.insertRecordToEnrollmentsTrainers(trainerData, db)){
            System.out.println("ERROR: Cannot insert trainer to course.\n" +
                               "Reason: There was an error while communicating with the database.\n");
            // Delete the object that was just saved so local data are in sync with db
            userData.removeTrainersPerCourseFromSetOfTrainersPerCourse((CourseTrainers)trainersPerCourseData);
            return;
        }
        System.out.printf("Trainer \"%s %s\" was successfully added to course \"%s %s %s\".\n", trainerData.getFirstName(), trainerData.getLastName(), course.getTitle(), course.getStream(), course.getType());
    }
}
