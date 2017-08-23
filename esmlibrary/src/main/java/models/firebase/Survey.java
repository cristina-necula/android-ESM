package models.firebase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

/**
 * Created by Cristina on 8/21/2017.
 */

@IgnoreExtraProperties
public class Survey {

    String question;
    Map<String, Answer> answerMap;
    boolean isSingleChoice;
    boolean isDismissed;

    public boolean isDismissed() {
        return isDismissed;
    }

    public void setDismissed(boolean dismissed) {
        isDismissed = dismissed;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isSingleChoice() {
        return isSingleChoice;
    }

    public void setSingleChoice(boolean singleChoice) {
        isSingleChoice = singleChoice;
    }

    public Map<String, Answer> getAnswerMap() {
        return answerMap;
    }

    public void setAnswerMap(Map<String, Answer> answerMap) {
        this.answerMap = answerMap;
    }

    @Exclude
    public String[] getStringAnswers(){
        String[] stringAnswers = new String[answerMap.size()];
        int i = 0;
        for (Answer answer : answerMap.values()) {
            stringAnswers[i] = answer.getText();
            i++;
        }
        return stringAnswers;
    }

    @Exclude
    public void setSelectedAnswer(String answerText, boolean isSelected){
        for(Answer answer : answerMap.values()){
            if(answer.getText().equals(answerText)){
                answer.setSelected(isSelected);
                break;
            }
        }
    }
}
