package bootcamp.lists;

import bootcamp.core.Course;
import bootcamp.core.Student;
import java.util.Set;

public class CourseStudents extends CourseComponents<Student> {
    
    public CourseStudents(Course course){
        super(course);
    }
    
    public CourseStudents(Course course, Set<Student> setOfStudents){
        super(course, setOfStudents);
    }
}
