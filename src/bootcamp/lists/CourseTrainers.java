package bootcamp.lists;

import bootcamp.core.Course;
import bootcamp.core.Trainer;
import java.util.Set;

public class CourseTrainers extends CourseComponents<Trainer> {
    public CourseTrainers(){
    }
    
    public CourseTrainers(Course course){
        super(course);
    }
    
    public CourseTrainers(Course course, Set<Trainer> setOfTrainers){
        super(course, setOfTrainers);
    }
    
     @Override
    public String toString() {
        return String.format("%s Trainers: %s", getCourse(), getSetOfComponents());
    }
}
