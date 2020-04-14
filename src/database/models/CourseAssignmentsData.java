package database.models;

import bootcamp.core.Course;
import bootcamp.lists.CourseAssignments;
import database.Database;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseAssignmentsData extends CourseAssignments {
    /* Fields */
    private int courses_id;
    private int assignments_id;

    /* Constructor */
    public CourseAssignmentsData(Course course) {
        super(course);
        this.courses_id = ((CourseData)course).getId();
    }

    /* Properties */
    public int getAssignments_id() {
        return assignments_id;
    }
    public void setAssignments_id(int assignments_id) {
        this.assignments_id = assignments_id;
    }

    public int getCourses_id() {
        return courses_id;
    }
    public void setCourses_id(int courses_id) {
        this.courses_id = courses_id;
    }
    
    /* Methods */
    public boolean insertRecordToEnrollmentsAssignments(AssignmentData assignmentData, Database db){
        String sql = new StringBuilder()
                        .append("INSERT INTO `enrollments_assignments`(`assignments_id`, `courses_id`)")
                        .append("VALUES(?, ?);").toString();
        try {
            // Execute query
            db.setPreparedStatement(sql);
            PreparedStatement pst = db.getPreparedStatement();
            pst.setInt(1, assignmentData.getId());
            pst.setInt(2, courses_id);
            int rowsAffected = pst.executeUpdate();
            //System.out.println(rowsAffected + " rows(s) inserted in table 'enrollments_assignments'");
            
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
}
