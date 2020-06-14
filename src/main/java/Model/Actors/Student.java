package Model.Actors;

import Exceptions.LevelRequiredAtStudentCreation;
import Exceptions.ModalityRequiredAtStudentCreation;
import Exceptions.ScheduleRequiredAtStudentCreation;
import Model.Availability.Availability;
import Model.Level.Level;
import Model.Modality.Modality;
import Utils.DateValidation;




import java.util.List;


public class Student extends Participants {

    private Level level;
    private Modality modality;


    public Student(Level level, Modality modality, List<Availability> scheduler) {
        if(level == null)
            throw new LevelRequiredAtStudentCreation();
        if(modality == null)
            throw new ModalityRequiredAtStudentCreation();
        if(scheduler == null)
            throw new ScheduleRequiredAtStudentCreation();

        scheduler.stream().forEach(s->new DateValidation().dateValidation(s.getDate()));

        this.level = level;
        this.modality = modality;
        this.scheduler = scheduler;
    }

    public boolean acceptsGroupCourse() {
        return this.modality.acceptAnotherStudent();
    }

    public Level getLevel () {
        return this.level;
    }

    public Modality getModality () {
        return this.modality;
    }



}
