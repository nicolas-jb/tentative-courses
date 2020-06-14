package Model.Actors;

import Exceptions.AvailabilityRequiredAtProfessorCreation;
import Model.Availability.Availability;
import Utils.DateValidation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Professor extends Participants {


    public Professor(List<Availability> availability) {
        if (availability == null)
            throw new AvailabilityRequiredAtProfessorCreation();

        availability.stream().forEach(s -> new DateValidation().dateValidation(s.getDate()));

        this.scheduler = availability;
    }


    public List<LocalDateTime> getDateFromAvailability() {
        return this.getScheduler().stream().map(availability -> availability.getDate()).collect(Collectors.toList());
    }


}
