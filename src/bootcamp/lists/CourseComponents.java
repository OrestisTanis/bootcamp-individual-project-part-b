package bootcamp.lists;

import bootcamp.core.Course;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class CourseComponents<T>{
    private Course course;
    private Set<T> set;
    private String type;
    
    /* Constructors */
    protected CourseComponents(){
    }
    
    protected CourseComponents(Course course){
        // Save the data-type of the object that's going to be instantiated
        this.type = getClass().getName();
        this.course = course;
    }
    
    protected CourseComponents(Course course, Set<T> set){
        this(course);
        this.set = set;
    }
    
    /* Properties */
    public Set<T> getSetOfComponents(){
        return set;
    }
    
    public Course getCourse(){
        return course;
    }
    
    public String getType() {
        return this.type;
    }
    
    /*  Methods */
    public void addToSet(T item){
        // Lazy-instantiate the list
        if (set == null){
            set = new HashSet();
        }
        // Add to list
        this.set.add(item);
    }
    
    public boolean SetIsEmpty(){
        return set.size() == 0;
    }
    
    public boolean containedInSet(T item){
        return set.contains(item);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.course);
        hash = 19 * hash + Objects.hashCode(this.set);
        hash = 19 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CourseComponents<?> other = (CourseComponents<?>) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.course, other.course)) {
            return false;
        }
        if (!Objects.equals(this.set, other.set)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "CourseSet{" + "course=" + course + ", set=" + set + ", type=" + type + '}';
    }
}