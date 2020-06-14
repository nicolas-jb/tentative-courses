
import Exceptions.*;
import Model.Actors.Student;
import Model.Availability.Availability;
import Model.Course.ExactCreation;
import Model.Modality.Group;
import Model.Modality.Individual;
import Utils.SortListOfStudents;
import org.junit.Test;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static Model.Level.Level.*;
import static org.junit.Assert.*;

public class StudentTests {


    LocalDateTime validDate = LocalDateTime.parse("2020-01-01T10:00:00");
    LocalDateTime invalidDate = LocalDateTime.parse("2020-06-13T23:00:00");
    LocalDateTime invalidHour = LocalDateTime.parse("2020-06-12T19:00:00");


    @Test(expected = LevelRequiredAtStudentCreation.class)
    public void studentCreationWithoutLevel() {
        new Student(null, new Individual(), Arrays.asList(new Availability(validDate)));
    }

    @Test(expected = ModalityRequiredAtStudentCreation.class)
    public void studentCreationWithoutModality() {
        new Student(ADVANCED, null, Arrays.asList(new Availability(validDate)));
    }

    @Test(expected = ScheduleRequiredAtStudentCreation.class)
    public void studentCreationWithoutSchedule() {
        new Student(ADVANCED, new Individual(), null);
    }


    @Test(expected = InvalidDay.class)
    public void studentCreationWithInvalidDay() {
        new Student(ADVANCED, new Individual(), Arrays.asList(new Availability(invalidDate)));
    }

    @Test(expected = InvalidHour.class)
    public void studentCreationWithInvalidHour() {
        new Student(ADVANCED, new Individual(), Arrays.asList(new Availability(invalidHour)));
    }


    @Test
    public void studentCreationOK() {
        Student oneStudent = new Student(ADVANCED, new Individual(), Arrays.asList(new Availability(validDate)));
        assertEquals(ADVANCED, oneStudent.getLevel());
    }

    @Test
    public void studentWithIndividualModalityDoesntTakeGroupCourses() {
        Student example = new Student(INTERMEDIATE, new Individual(), Arrays.asList(new Availability(validDate)));
        assertFalse(example.acceptsGroupCourse());
    }

    @Test
    public void studentWithGroupModalityCanAcceptGroupCourses() {
        Student example = new Student(INTERMEDIATE, new Group(), Arrays.asList(new Availability(validDate)));
        assertTrue(example.acceptsGroupCourse());
    }

    @Test
    public void studentCanTakeTheCourse() {
        assertTrue(new Student(INTERMEDIATE, new Group(), Arrays.asList(new Availability(validDate))).canTakeTheCourse(LocalDateTime.parse("2020-01-01T10:00:00")));
    }

    @Test
    public void studentCanTTakeTheCourseIfTheHourIsDifferent() {
        assertFalse(new Student(INTERMEDIATE, new Group(), Arrays.asList(new Availability(validDate))).canTakeTheCourse(LocalDateTime.parse("2020-01-01T12:00:00")));
    }

    @Test
    public void studentCanTTakeTheCourseIfTheDayIsDifferent() {
        assertFalse(new Student(INTERMEDIATE, new Group(), Arrays.asList(new Availability(validDate))).canTakeTheCourse(LocalDateTime.parse("2020-01-02T10:00:00")));
    }

    @Test
    public void studentCanTTakeTheCourseIfItisConfirmed() {
        Student example = new Student(INTERMEDIATE, new Group(), Arrays.asList(new Availability(validDate)));
        example.getScheduler().get(0).setConfirmed();
        assertFalse(example.canTakeTheCourse(LocalDateTime.parse("2020-01-01T10:00:00")));
    }

    @Test
    public void studentCanConfirmAndUnconfirmDate(){
        Student example = new Student(INTERMEDIATE, new Group(), Arrays.asList(new Availability(validDate)));
        assertTrue(example.hasTurns());
        example.confirmAvailabilityByDate(validDate);
        assertFalse(example.hasTurns());
        example.unconfirmAvailabilityByDate(validDate);
        assertTrue(example.hasTurns());
    }
}



