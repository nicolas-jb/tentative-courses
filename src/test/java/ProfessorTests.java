import Exceptions.AvailabilityRequiredAtProfessorCreation;
import Exceptions.InvalidDay;
import Exceptions.InvalidHour;
import Model.Actors.Professor;
import Model.Availability.Availability;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProfessorTests {


    LocalDateTime validDate = LocalDateTime.parse("2020-01-01T10:00:00");
    LocalDateTime invalidDate = LocalDateTime.parse("2020-06-13T16:00:00");
    LocalDateTime validHour = LocalDateTime.parse("2020-06-12T15:00:00");
    LocalDateTime invalidHour = LocalDateTime.parse("2020-06-12T23:00:00");


    Availability availability1 = new Availability(validDate);
    Availability availability2 = new Availability(validHour);

    Professor example = new Professor(Arrays.asList(availability1, availability2));

    @Test(expected = AvailabilityRequiredAtProfessorCreation.class)
    public void professorCreationWithoutAvailability() {
        new Professor(null);
    }

    @Test(expected = InvalidHour.class)
    public void professorCreationWithInvalidAvailabilityHour() {
        new Professor(Arrays.asList(new Availability(invalidHour)));
    }

    @Test(expected = InvalidDay.class)
    public void professorCreationWithInvalidAvailabilityDay() {
        new Professor(Arrays.asList(new Availability(invalidDate)));
    }

    @Test
    public void professorCreationOk() {
        new Professor(Arrays.asList(new Availability(validDate)));
        new Professor(Arrays.asList(new Availability(validHour)));
    }

    @Test
    public void professorCanTakeTheCourse() {
        assertTrue(example.canTakeTheCourse(LocalDateTime.parse("2020-01-01T10:00:00")));
    }

    @Test
    public void professorCannotTakeTheCourse() {
        assertFalse(example.canTakeTheCourse(LocalDateTime.parse("2020-01-01T12:00:00")));
    }

    @Test
    public void professorWhitTurnsHaveAvailableTurns() {

        assertTrue(example.hasTurns());
    }

    @Test
    public void professorWhitTurnsConfirmedHasNotAvailableTurns() {
        example.getScheduler().get(0).setConfirmed();
        assertTrue(example.hasTurns());
        example.getScheduler().get(1).setConfirmed();
        assertFalse(example.hasTurns());
    }

    @Test
    public void professorCanConfirmATurnByDate() {
        assertTrue(example.hasTurns());
        example.confirmAvailabilityByDate(LocalDateTime.parse("2020-01-01T10:00:00"));
        assertTrue(example.hasTurns());
        example.confirmAvailabilityByDate(LocalDateTime.parse("2020-01-02T10:00:00")); //It should do nothing
        assertTrue(example.hasTurns());
        example.confirmAvailabilityByDate(LocalDateTime.parse("2020-01-01T11:00:00")); //It should do nothing
        assertTrue(example.hasTurns());
        example.confirmAvailabilityByDate(LocalDateTime.parse("2020-06-12T15:00:00"));
        assertFalse(example.hasTurns());
        example.confirmAvailabilityByDate(LocalDateTime.parse("2020-01-01T10:00:00")); //It should do nothing
        assertFalse(example.hasTurns());
    }

    @Test
    public void studentCanConfirmAndUnconfirmDate(){
        Professor example = new Professor(Arrays.asList(new Availability(validDate)));
        assertTrue(example.hasTurns());
        example.confirmAvailabilityByDate(validDate);
        assertFalse(example.hasTurns());
        example.unconfirmAvailabilityByDate(validDate);
        assertTrue(example.hasTurns());
    }

}
