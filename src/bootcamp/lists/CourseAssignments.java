package bootcamp.lists;

import bootcamp.core.Assignment;
import bootcamp.core.Course;
import java.util.Set;

public class CourseAssignments extends CourseComponents<Assignment> {
    
    public CourseAssignments(Course course){
        super(course);
    }
    
    public CourseAssignments(Course course, Set<Assignment> setOfAssignments){
        super(course, setOfAssignments);
    }
}