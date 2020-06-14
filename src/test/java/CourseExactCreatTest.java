import Exceptions.StudentsAndProfessorsRequiredToCreateACourse;
import Model.Actors.Professor;
import Model.Actors.Student;
import Model.Availability.Availability;
import Model.Course.CourseRepository;
import Model.Course.ExactCreation;
import Model.Level.Level;
import Model.Modality.Group;
import Model.Modality.Individual;
import org.junit.Test;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class CourseExactCreatTest {

    //Setup
    ExactCreation factory = new ExactCreation();
    Student student1 = new Student(Level.INTERMEDIATE, new Individual(),
            Arrays.asList(new Availability(LocalDateTime.parse("2020-01-01T15:00:00"))));
    Student student2 = new Student(Level.BEGGINER, new Group(),
            Arrays.asList(new Availability(LocalDateTime.parse("2020-01-01T15:00:00"))));
    Student student3 = new Student(Level.ADVANCED, new Group(),
            Arrays.asList(new Availability(LocalDateTime.parse("2020-01-01T15:00:00"))));
    Student student4 = new Student(Level.BEGGINER, new Group(),
            Arrays.asList(new Availability(LocalDateTime.parse("2020-01-01T15:00:00"))));
    Student student5 = new Student(Level.UPPER_INTERMEDIATE, new Individual(),
            Arrays.asList(new Availability(LocalDateTime.parse("2020-01-01T16:00:00"))));


    Professor professor1 = new Professor(Arrays.asList(new
            Availability(LocalDateTime.parse("2020-01-01T15:00:00")), new Availability(LocalDateTime.parse("2020-01-01T16:00:00"))));
    Professor professor2 = new Professor(Arrays.asList(new
            Availability(LocalDateTime.parse("2020-01-01T15:00:00")), new Availability(LocalDateTime.parse("2020-01-01T16:00:00"))));
    Professor professor3 = new Professor(Arrays.asList(new
            Availability(LocalDateTime.parse("2020-01-01T17:00:00"))));



    @Test(expected = StudentsAndProfessorsRequiredToCreateACourse.class)
    public void canNotCreatACourseWithooutProfessorOrStudents() {
        factory.createCourse(null, null, 6);
    }

    @Test(expected = StudentsAndProfessorsRequiredToCreateACourse.class)
    public void canNotCreatACourseWithoutProfessor() {
        factory.createCourse(Arrays.asList(student1), null, 6);
    }

    @Test(expected = StudentsAndProfessorsRequiredToCreateACourse.class)
    public void canNotCreatACourseWithoutStudent() {
        factory.createCourse(null, Arrays.asList(professor1), 6);
    }

    @Test
    public void coursesCreateOk() {

        CourseRepository.getInstance().addProfessors(professor1);
        CourseRepository.getInstance().addProfessors(professor2);
        CourseRepository.getInstance().addProfessors(professor3);

        CourseRepository.getInstance().addStudent(student1);
        CourseRepository.getInstance().addStudent(student2);
        CourseRepository.getInstance().addStudent(student3);
        CourseRepository.getInstance().addStudent(student4);
        CourseRepository.getInstance().addStudent(student5);

        CourseRepository.getInstance().createCourses(factory, 6); //6 is the max number of students but i can set that number

        assertEquals(3, CourseRepository.getInstance().getCourses().size());

        //First Course
        assertEquals(2, CourseRepository.getInstance().getCourses().get(0).getStudents().size());
        assertEquals(Level.BEGGINER, CourseRepository.getInstance().getCourses().get(0).getLevel());
        //Same Level
        assertEquals(0, CourseRepository.getInstance().getCourses().get(0).getStudents().stream().filter(
                student -> ! (student.getLevel() == Level.BEGGINER)).collect(Collectors.toList()).size());

        //Second Course
        assertEquals(1, CourseRepository.getInstance().getCourses().get(1).getStudents().size());
        assertEquals(Level.UPPER_INTERMEDIATE, CourseRepository.getInstance().getCourses().get(1).getLevel());

        //Third Course
        assertEquals(1, CourseRepository.getInstance().getCourses().get(2).getStudents().size());
        assertEquals(Level.ADVANCED, CourseRepository.getInstance().getCourses().get(2).getLevel());

        //Confirmed and Unconfirmed Professors and Students
        assertEquals(2, CourseRepository.getUnconfirmedProfessors().size());
        assertEquals(1, CourseRepository.getConfirmedProfessors().size());
        assertEquals(1, CourseRepository.getUnconfirmedStudents().size());
        assertEquals(4, CourseRepository.getConfirmedStudents().size());

    }




}
