package models.Action;

import java.util.UUID;

import enums.ActionType;

/**
 * Created by Cristina on 8/6/2017.
 */

public class ButtonAction extends Action {

    public ButtonAction(){
        Type = ActionType.Button;
        Uuid = UUID.randomUUID();
    }

}
