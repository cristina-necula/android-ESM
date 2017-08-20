package tracker;

import java.util.LinkedList;

/**
 * Created by Cristina on 8/6/2017.
 */

public class Workflow {
    private LinkedList<TrackedSession> trackedSessions;

    public LinkedList<TrackedSession> getTrackedSessions() {
        return trackedSessions;
    }

    public void setTrackedSessions(LinkedList<TrackedSession> trackedSessions) {
        this.trackedSessions = trackedSessions;
    }
}
