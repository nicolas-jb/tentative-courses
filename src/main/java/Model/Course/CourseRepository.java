package Model.Course;


import Model.Actors.Professor;
import Model.Actors.Student;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CourseRepository {

    private static CourseRepository instance = null;
    private static List<Professor> unconfirmedProfessors = new ArrayList<>();
    private static List<Student> unconfirmedStudents = new ArrayList<>();
    private static List<Professor> confirmedProfessors = new ArrayList<>();
    private static List<Student> confirmedStudents = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();


    private CourseRepository() {
    }

    public static CourseRepository getInstance() {
        if (instance == null) {
            instance = new CourseRepository();
        }
        return instance;
    }

    public void addProfessors(Professor professor) {
        unconfirmedProfessors.add(professor);
    }

    public void addStudent(Student student) {
        unconfirmedStudents.add(student);
    }

    public void createCourses(ExactCreation factory, int quantity) {
        courses.addAll(factory.createCourse(unconfirmedStudents, unconfirmedProfessors, quantity));
        this.refreshProfessors();
        this.refreshStudents();
    }


    private void refreshProfessors() {
        List<Professor> professorsToConfirm = unconfirmedProfessors.stream().filter(
                professor -> !professor.hasTurns()).collect(Collectors.toList());
        confirmedProfessors.addAll(professorsToConfirm);
        unconfirmedProfessors.removeAll(professorsToConfirm);

    }


    private void refreshStudents() {
        List<Student> studentToConfirm = unconfirmedStudents.stream().filter(
                student -> !student.hasTurns()).collect(Collectors.toList());
        confirmedStudents.addAll(studentToConfirm);
        unconfirmedStudents.removeAll(studentToConfirm);

    }

    public List<Course> getCourses() {
        return courses;
    }


    public static List<Professor> getConfirmedProfessors() {
        return confirmedProfessors;
    }


    public static List<Student> getConfirmedStudents() {
        return confirmedStudents;
    }


    public static List<Professor> getUnconfirmedProfessors() {
        return unconfirmedProfessors;
    }


    public static List<Student> getUnconfirmedStudents() {
        return unconfirmedStudents;
    }



}
