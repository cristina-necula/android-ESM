package models.firebase;

import android.location.*;

/**
 * Created by Cristina on 8/21/2017.
 */

public class Event {

    long timestamp;
    String type;
    String tag;
    String containerActivityName;
    String detectedUserActivity;
    Location location;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContainerActivityName() {
        return containerActivityName;
    }

    public void setContainerActivityName(String containerActivityName) {
        this.containerActivityName = containerActivityName;
    }

    public String getDetectedUserActivity() {
        return detectedUserActivity;
    }

    public void setDetectedUserActivity(String detectedUserActivity) {
        this.detectedUserActivity = detectedUserActivity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
