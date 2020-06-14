package Utils;

import Exceptions.InvalidDay;
import Exceptions.InvalidHour;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class DateValidation {

    public void dateValidation(LocalDateTime date){
        if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY))
            throw new InvalidDay();
        if (date.getHour() < 9 || date.getHour() > 18)
            throw new InvalidHour();

    }
}
