package bootcamp.creators.list;

import appstate.UserData;
import bootcamp.core.Course;
import bootcamp.core.Trainer;
import bootcamp.lists.CourseTrainers;
import java.util.Iterator;
import java.util.Set;
import main.Input;

public class CourseTrainersCreator {

    public CourseTrainersCreator(){
    }
    
    public void createTrainersPerCourse(UserData userData){
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
            Trainer trainer = getTrainerFromUser(userData);
            Course course = getCourseFromUser(trainer, userData);
            addTrainerToTrainersPerCourseList(trainer, course, userData);
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
    
    public void addTrainerToTrainersPerCourseList(Trainer trainer, Course course, UserData userData){
        Set setOfTrainersPerCourse = userData.getSetOfTrainersPerCourse();
        Iterator it = setOfTrainersPerCourse.iterator();
        while (it.hasNext()){
           CourseTrainers trainersPerCourse = ((CourseTrainers) it.next());
           if (trainersPerCourse.getCourse().equals(course)){
               Set<Trainer> setOfTrainers = trainersPerCourse.getSetOfComponents();
               for (Trainer tr : setOfTrainers){
                   if (tr.equals(trainer)){
                       System.out.printf("Trainer %s %s is already assigned to course %s/%s/%s!%n", trainer.getFirstName(), trainer.getLastName(), course.getTitle(), course.getStream(), course.getType()); 
                       return;
                   }
               }
               setOfTrainers.add(trainer);
               System.out.printf("Trainer %s %s successfully added to course %s/%s/%s!%n", trainer.getFirstName(), trainer.getLastName(), course.getTitle(), course.getStream(), course.getType());
               return;
           }
        }
        // If execution reaches here, that means there is no trainersPerCourse obj holding the course specified by the user
        CourseTrainers trainersPerCourse = new CourseTrainers(course);
        trainersPerCourse.addToSet(trainer);
        System.out.printf("Trainer %s %s successfully added to course %s/%s/%s!%n", trainer.getFirstName(), trainer.getLastName(), course.getTitle(), course.getStream(), course.getType());
        userData.addTrainersPerCourseToSetOfTrainersPerCourse(trainersPerCourse);
    }
}
