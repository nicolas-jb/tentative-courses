package Model.Actors;

import Model.Availability.Availability;

import java.time.LocalDateTime;
import java.util.List;

public abstract class Participants {

    protected List<Availability> scheduler;

    public List<Availability> getScheduler() {

        return scheduler;
    }

    public boolean canTakeTheCourse(LocalDateTime date) {
        return scheduler.stream().anyMatch(s -> s.getDate().getDayOfWeek().equals(date.getDayOfWeek())
                && s.getDate().getHour() == date.getHour() && !s.isConfirmed());
    }

    public boolean hasTurns() {
        return this.scheduler.stream().anyMatch(availability -> !availability.isConfirmed());
    }

    public void confirmAvailabilityByDate(LocalDateTime date) {
        this.getScheduler().stream().forEach(availability -> {
            if (availability.getDate().equals(date))
                availability.setConfirmed();
        });


    }

    public void unconfirmAvailabilityByDate(LocalDateTime date) {
        this.getScheduler().stream().forEach(availability -> {
            if (availability.getDate().equals(date))
                availability.setUnconfirmed();
        });
    }


}
