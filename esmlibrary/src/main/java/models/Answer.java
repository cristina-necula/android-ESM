package models;

/**
 * Created by Cristina on 5/6/2017.
 */

public class Answer {

    private String text;
    private String isSelected;

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
