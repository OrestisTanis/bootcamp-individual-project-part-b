package database.models;

import bootcamp.core.Course;
import bootcamp.lists.CourseStudents;
import database.Database;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseStudentsData extends CourseStudents {
    /* Fields */
    private int courses_id;
    private int students_id;
    
    /* Constructor */
    public CourseStudentsData(Course course) {
        super(course);
        this.courses_id = ((CourseData)course).getId();
    }

    /* Properties */
    public int getStudents_id() {
        return students_id;
    }
    public void setStudents_id(int students_id) {
        this.students_id = students_id;
    }

    public int getCourses_id() {
        return courses_id;
    }
    public void setCourses_id(int courses_id) {
        this.courses_id = courses_id;
    }
    
    /* Methods */
    public boolean insertRecordToEnrollmentsStudents(StudentData studentData, Database db){
        String sql = new StringBuilder()
                        .append("INSERT INTO `enrollments_students`(`students_id`, `courses_id`)")
                        .append("VALUES(?, ?);").toString();
        try {
            db.setPreparedStatement(sql);
            PreparedStatement pst = db.getPreparedStatement();
            pst.setInt(1, studentData.getId());
            pst.setInt(2, courses_id);
            int rowsAffected = pst.executeUpdate();
            System.out.println(rowsAffected + " rows(s) inserted in table 'enrollments_students'");
            
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
}
