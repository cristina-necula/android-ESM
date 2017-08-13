package models;

import java.security.Timestamp;
import java.util.UUID;

import enums.ActionType;

/**
 * Created by Cristina on 8/6/2017.
 */

public abstract class Action {

    public ActionType Type;
    public String Tag;
    public long Timestamp;

}
