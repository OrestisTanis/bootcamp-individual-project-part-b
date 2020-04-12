package bootcamp.creators.list;

import appstate.UserData;
import bootcamp.core.Course;
import bootcamp.core.Student;
import bootcamp.lists.CourseStudents;
import database.Database;
import database.models.CourseData;
import database.models.CourseStudentsData;
import database.models.StudentData;
import java.util.Iterator;
import java.util.Set;
import interfaces.DateFormatable;
import main.Input;


public class CourseStudentsCreator implements DateFormatable {

    public CourseStudentsCreator(){
    }
    
    public void createStudentsPerCourse(UserData userData, Database db){
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
            StudentData studentData = (StudentData) getStudentFromUser(userData);
            CourseData courseData = (CourseData) getCourseFromUser((Student) studentData, userData);
            addStudentToStudentsPerCourseList(studentData, courseData, userData, db);
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
    
    public void addStudentToStudentsPerCourseList(StudentData studentData, CourseData courseData, UserData userData, Database db){
        Set<CourseStudents> setOfStudentsPerCourse = userData.getSetOfStudentsPerCourse();
        Iterator it = setOfStudentsPerCourse.iterator();
        while (it.hasNext()){
            CourseStudents studentsPerCourse = ((CourseStudents) it.next());
           if (studentsPerCourse.getCourse().equals((Course) courseData)){
               Set<Student> setOfStudents = studentsPerCourse.getSetOfComponents();
               for (Student st : setOfStudents){
                   if (st.equals((Student)studentData)){
                      System.out.printf("ERROR: Cannot insert student to course.\n" +
                                        "Reason: A student with first name \"%s\", last name \"%s\" and birth date \"%s\" is already assigned to course with title: \"%s, %s, %s\".\n", studentData.getFirstName(), studentData.getLastName(), (studentData.getDateOfBirth()).format(formatter), courseData.getTitle(), courseData.getStream(), courseData.getType());
                      return;
                   }
               }
               // Add student to the existing set of students for this course and save it to DB
               setOfStudents.add((Student)studentData);
               saveToDb(studentData, (CourseStudentsData) studentsPerCourse, userData, db);
//             System.out.printf("Student %s %s successfully added to course %s/%s/%s!%n", student.getFirstName(), student.getLastName(), course.getTitle(), course.getStream(), course.getType());
               return;
           }
        }
        // It's the first time we add a student to this course
        CourseStudentsData studentsPerCourseData = new CourseStudentsData((Course)courseData);
        studentsPerCourseData.addToSet((Student)studentData);
        userData.addStudentsPerCourseToSetOfStudentsPerCourse((CourseStudents)studentsPerCourseData);
        saveToDb(studentData, studentsPerCourseData, userData, db);
        
        // Save to DB
//        if (!studentsPerCourseData.insertRecordToEnrollmentsStudents(db)){
//            System.out.print("ERROR: Cannot insert student to course.\n" +
//                             "Reason: There was an error while communicating with the database.\n");
//            // Delete the object that was just saved so local data are in sync with db
//            userData.removeStudentsPerCourseFromSetOfStudentsPerCourse((CourseStudents)studentsPerCourseData);
//            return;
//        }
    }
    
     public void saveToDb(StudentData studentData, CourseStudentsData studentsPerCourseData, UserData userData, Database db){
        Course course = studentsPerCourseData.getCourse();
        // Save to DB
        if (!studentsPerCourseData.insertRecordToEnrollmentsStudents(studentData, db)){
            System.out.println("ERROR: Cannot insert student to course.\n" +
                               "Reason: There was an error while communicating with the database.\n");
            // Delete the object that was just saved so local data are in sync with db
            userData.removeStudentsPerCourseFromSetOfStudentsPerCourse((CourseStudents)studentsPerCourseData);
            return;
        }
        System.out.printf("Student \"%s %s\" was successfully added to course \"%s %s %s\".\n", studentData.getFirstName(), studentData.getLastName(), course.getTitle(), course.getStream(), course.getType());
    }
}
