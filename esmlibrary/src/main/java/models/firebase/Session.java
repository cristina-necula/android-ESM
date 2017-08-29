package models.firebase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cristina on 8/21/2017.
 */
@IgnoreExtraProperties
public class Session {

    String userKey;
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

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public Session(){
        events = new ArrayList<>();
    }

    public void addEvent(Event event){
        events.add(event);
        if(events.size() > 4){
            int size = events.size();
            if(events.get(size - 1).getContainerActivityName()
                    .equals(events.get(size - 4).getContainerActivityName())
                    && !events.get(size - 1).isError()
                    && !events.get(size - 2).isError()
                    && !events.get(size - 3).isError()
                    && !events.get(size - 4).isError()){
                events.get(size - 2).setError(true);
                events.get(size - 3).setError(true);
                events.get(size - 4).setError(true);
                // // TODO: 8/30/2017 show survey for error
            }
        }
    }

    @Exclude
    public Map<String, Object> toMap(){

        HashMap<String, Object> map = new HashMap<>();

        map.put("userKey", userKey);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("events", events);

        return map;
    }
}
