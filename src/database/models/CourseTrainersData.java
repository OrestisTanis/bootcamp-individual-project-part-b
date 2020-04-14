package database.models;

import bootcamp.core.Course;
import bootcamp.lists.CourseTrainers;
import database.Database;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseTrainersData extends CourseTrainers{
     /* Fields */
    private int courses_id;
    private int trainers_id;
    
    /* Constructor */
    public CourseTrainersData(Course course) {
        super(course);
        this.courses_id = ((CourseData)course).getId();
    }

    /* Properties */
    public int getTrainers_id() {
        return trainers_id;
    }
    public void setTrainers_id(int trainers_id) {
        this.trainers_id = trainers_id;
    }

    public int getCourses_id() {
        return courses_id;
    }
    public void setCourses_id(int courses_id) {
        this.courses_id = courses_id;
    }
    
    /* Methods */
    public boolean insertRecordToEnrollmentsTrainers(TrainerData trainerData, Database db){
        String sql = new StringBuilder()
                        .append("INSERT INTO `enrollments_trainers`(`trainers_id`, `courses_id`)")
                        .append("VALUES(?, ?);").toString();
        
        try {
            // Execute query
            db.setPreparedStatement(sql);
            PreparedStatement pst = db.getPreparedStatement();
            pst.setInt(1, trainerData.getId());
            pst.setInt(2, courses_id);
            int rowsAffected = pst.executeUpdate();
            //System.out.println(rowsAffected + " rows(s) inserted in table 'enrollments_trainers'");
            
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
}
