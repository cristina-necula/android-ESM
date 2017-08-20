package models.Event;

import java.util.UUID;

import enums.EventType;

/**
 * Created by Cristina on 8/15/2017.
 */

public class ActivityClosedEvent extends Event {

    public ActivityClosedEvent() {
        Type = EventType.ActivityClosed;
        Uuid = UUID.randomUUID();
    }
}
