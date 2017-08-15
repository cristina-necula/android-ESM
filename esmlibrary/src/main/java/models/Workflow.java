package models;

import java.util.LinkedList;

import models.Action.Action;

/**
 * Created by Cristina on 8/6/2017.
 */

public class Workflow {
    private LinkedList<Action> actions;

    public LinkedList<Action> getActions() {
        return actions;
    }

    public void setActions(LinkedList<Action> actions) {
        this.actions = actions;
    }
}
