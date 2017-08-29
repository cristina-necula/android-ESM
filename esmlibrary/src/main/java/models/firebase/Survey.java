package models.firebase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
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
    Map<String, Object> answers;

    public Map<String, Object> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, Object> answers) {
        this.answers = answers;
    }

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

    public Survey(){
        answerMap = new HashMap<>();
        answers = new HashMap<>();
    }

    public Map<String, Answer> getAnswerMap() {
        return answerMap;
    }

    public void setAnswerMap(Map<String, Answer> answerMap) {
        this.answerMap = answerMap;
    }

    @Exclude
    public CharSequence[] getStringAnswers(){
        String[] stringAnswers = new String[answerMap.size()];
        int i = 0;
        for (Answer answer : answerMap.values()) {
            stringAnswers[i] = answer.getText();
            i++;
        }
        return stringAnswers;
    }

    @Exclude
    public void setSelectedAnswer(CharSequence answerText){
        for(Answer answer : answerMap.values()){
            if(answer.getText().equals(answerText)){
                answer.setSelected(!answer.isSelected());
                break;
            }
        }
    }

    @Exclude
    public void setSelectedAnswer(CharSequence answerText, boolean isSelected){
        for(Answer answer : answerMap.values()){
            if(answer.getText().equals(answerText)){
                answer.setSelected(isSelected);
                break;
            }
        }
    }
}
