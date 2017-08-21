package models.firebase;

import java.util.ArrayList;

/**
 * Created by Cristina on 8/21/2017.
 */

public class Session {

    long startTime;
    long endTime;
    ArrayList<Event> events;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
