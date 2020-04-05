package bootcamp.creators.list;

import appstate.UserData;
import bootcamp.core.Course;
import bootcamp.core.Student;
import bootcamp.lists.CourseStudents;
import java.util.Iterator;
import java.util.Set;
import interfaces.DateFormatable;
import main.Input;


public class CourseStudentsCreator implements DateFormatable {

    public CourseStudentsCreator(){
    }
    
    public void createStudentsPerCourse(UserData userData){
        String choice = "Y";
       
        while(choice.equalsIgnoreCase("Y")){
            if (userData.setOfStudentsIsEmpty()){
                System.out.println("No available students to assign. Returning to main menu.");
                return;
            }
            if (userData.setOfCoursesIsEmpty()){
                System.out.println("No available course to assign students to. Returning to main menu.");
                return;
            }
            Student student = getStudentFromUser(userData);
            Course course = getCourseFromUser(student, userData);
            addStudentToStudentsPerCourseList(student, course, userData);
            System.out.println("\nDo you want to insert another Student to a course? (Y/N)");
            choice = Input.getString("[yYnN]", "Y/N?");
        }
    }
    
     public Student getStudentFromUser(UserData userData){
        System.out.println("\nChoose a student to assign to a course: ");
        Set setOfStudents = userData.getSetOfStudents();
        Input.printOptionsFromSet(setOfStudents);
        Student student = (Student) Input.getOptionFromSet(setOfStudents);
        return student;
    }
    
    public Course getCourseFromUser(Student student, UserData userData){
        System.out.printf("\nChoose a course to assign student %s to:\n", student);
        Set setOfCourses = userData.getSetOfCourses();
        Input.printOptionsFromSet(setOfCourses);
        Course course = (Course)Input.getOptionFromSet(setOfCourses);
        return course;
    }
    
    public void addStudentToStudentsPerCourseList(Student student, Course course, UserData userData){
        Set setOfStudentsPerCourse = userData.getSetOfStudentsPerCourse();
        Iterator it = setOfStudentsPerCourse.iterator();
        while (it.hasNext()){
            CourseStudents studentsPerCourse = ((CourseStudents) it.next());
           if (studentsPerCourse.getCourse() == course){
               Set<Student> setOfStudents = studentsPerCourse.getSetOfComponents();
               for (Student st : setOfStudents){
                   if (st.equals(student)){
                      System.out.printf("Student %s %s with birth date %s is already assigned to course %s/%s/%s!%n", student.getFirstName(), student.getLastName(), (student.getDateOfBirth()).format(formatter), course.getTitle(), course.getStream(), course.getType());
                      return;
                   }
               }
               setOfStudents.add(student);
               System.out.printf("Student %s %s successfully added to course %s/%s/%s!%n", student.getFirstName(), student.getLastName(), course.getTitle(), course.getStream(), course.getType());
               return;
           }
        }
        // If execution reaches here, that means there is no studentsPerCourse obj holding the course specified by the user
        CourseStudents studentsPerCourse = new CourseStudents(course);
        studentsPerCourse.addToSet(student);
        userData.addStudentsPerCourseToSetOfStudentsPerCourse(studentsPerCourse);
        System.out.printf("Student %s %s successfully added to course %s/%s/%s!%n", student.getFirstName(), student.getLastName(), course.getTitle(), course.getStream(), course.getType());
    }
}
