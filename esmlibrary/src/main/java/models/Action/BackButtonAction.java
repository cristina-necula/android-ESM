package models.Action;

import java.util.UUID;

import enums.ActionType;

/**
 * Created by Cristina on 8/6/2017.
 */

public class BackButtonAction extends Action {

    public BackButtonAction(){

        Type = ActionType.Back;
        Uuid = UUID.randomUUID();
    }

}
