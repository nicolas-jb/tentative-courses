package Model.Availability;

import java.time.LocalDateTime;

public class Availability {
    private LocalDateTime date;
    private boolean confirmed = false;

    public Availability(LocalDateTime date){
        this.date = date;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed() {
        this.confirmed = true;
    }

    public void setUnconfirmed() {
        this.confirmed = false;
    }
}
