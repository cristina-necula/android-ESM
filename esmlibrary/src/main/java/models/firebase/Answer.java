package models.firebase;

/**
 * Created by Cristina on 8/21/2017.
 */

public class Answer {

    String text;
    boolean isSelected;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
