package database.models;

import database.Database;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class CourseStudentsAssignmentsData {
    /* Fields */
    private int courses_id;
    private int students_id;
    private int assignments_id;
    private LocalDate submissionDate;
    private int grade;
    
    /* Constructor */
    public CourseStudentsAssignmentsData(int courses_id, int students_id, int assignments_id, LocalDate submissionDate, int grade) {
        this.courses_id = courses_id;
        this.students_id = students_id;
        this.assignments_id = assignments_id;
        this.submissionDate = submissionDate;
        this.grade = grade;
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

    public int getAssignments_id() {
        return assignments_id;
    }
    public void setAssignments_id(int assignments_id) {
        this.assignments_id = assignments_id;
    }

    public LocalDate getSubmission_date() {
        return submissionDate;
    }
    public void setSubmission_date(LocalDate submission_date) {
        this.submissionDate = submission_date;
    }

    public int getGrade() {
        return grade;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }
    
    /* Methods */
    public boolean insertRecordToAssignmentsPerStudentPerCourse(Database db){
        String sql = new StringBuilder()
                        .append("INSERT INTO `assignments_students_courses`(`assignments_id`, `students_id`, `courses_id`, `submission_date`, `grade`)")
                        .append("VALUES(?, ?, ?, ?, ?);").toString();
        try {
            // Execute query
            db.setPreparedStatement(sql);
            PreparedStatement pst = db.getPreparedStatement();
            pst.setInt(1, assignments_id);
            pst.setInt(2, students_id);
            pst.setInt(3, courses_id);
            pst.setString(4, submissionDate.toString());
            pst.setInt(5, grade);
            int rowsAffected = pst.executeUpdate();
            System.out.println(rowsAffected + " rows(s) inserted in table 'assignments_students_courses'");
            
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
}
  
