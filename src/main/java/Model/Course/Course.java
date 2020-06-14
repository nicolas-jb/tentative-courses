package Model.Course;

import Model.Actors.Professor;
import Model.Actors.Student;
import Model.Level.Level;

import java.time.LocalDateTime;
import java.util.List;

public class Course {

    private Professor professor;
    private List<Student> students;
    private Level level;
    private LocalDateTime schedule;

    public Course (Professor professor, List<Student> students, Level level, LocalDateTime schedule) {
        this.professor = professor;
        this.students = students;
        this.level = level;
        this.schedule = schedule;

    }

    public Level getLevel() {
        return level;
    }

    public List<Student> getStudents() {
        return students;
    }

    public Professor getProfessor() {
        return professor;
    }

    public LocalDateTime getSchedule(){
        return schedule;
    }


}
