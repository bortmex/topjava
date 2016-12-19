package ru.javawebinar.topjava.to;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by alexa on 20.12.2016.
 */
public class localDate {
    private LocalDate startdate;
    private LocalTime starttime;
    private LocalDate enddate;
    private LocalTime endtime;

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalTime getStarttime() {
        return starttime;
    }

    public void setStarttime(LocalTime starttime) {
        this.starttime = starttime;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }

    public LocalTime getEndtime() {
        return endtime;
    }

    public void setEndtime(LocalTime endtime) {
        this.endtime = endtime;
    }
}
