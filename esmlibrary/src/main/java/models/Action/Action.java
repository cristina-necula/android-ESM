package models.Action;

import android.location.Location;

import java.util.UUID;

import enums.ActionType;
import Interfaces.Interaction;

/**
 * Created by Cristina on 8/6/2017.
 */

public abstract class Action implements Interaction {

    public ActionType Type;
    public String Tag;
    public long Timestamp;
    public Location Location;
    public UUID Uuid;

}
