package be.henallux.masi.pedagogique.utils;

import android.content.Context;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.dao.interfaces.IAnswerRepository;
import be.henallux.masi.pedagogique.dao.interfaces.IQuestionRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteQuestionRepository;
import be.henallux.masi.pedagogique.model.Answer;

/**
 * Created by hendrikdebeuf2 on 7/01/18.
 */

public class MailComposer implements IMailComposer {

    private Context context;
    private IQuestionRepository questionRepository;

    public MailComposer(Context context) {
        this.context = context;
        this.questionRepository = new SQLiteQuestionRepository(context);
    }

    public StringBuilder composeResultsGrid(ArrayList<Answer> givenAnswers){

        ArrayList<Answer> fullAnswerList = new ArrayList<Answer>();
        for (Answer answer : givenAnswers) {
            String questionName = questionRepository.getQuestionName(answer.getQuestionId());
            int point = 0;
            if(answer.isCorrect()){
                point = 1;
            }
            Answer fullAnswer = new Answer(answer.getId(),answer.getStatement(),point,questionName);
            fullAnswerList.add(fullAnswer);
        }

        StringBuilder mb = new StringBuilder();

        mb.append("<table>");
        mb.append("<tr>");
        mb.append("<th>Question</th>");
        mb.append("<th>Réponse</th>");
        mb.append("<th>Point</th>");
        mb.append("</tr>");

        for (Answer fullAnswer : fullAnswerList) {
            mb.append("<tr>");
            mb.append("<td>");
            mb.append(fullAnswer.getQuestionStatement());
            mb.append("</td>");
            mb.append("<td>");
            mb.append(fullAnswer.getStatement());
            mb.append("</td>");
            mb.append("<td>");
            mb.append(fullAnswer.getPoint());
            mb.append("</td>");
            mb.append("</tr>");
        }
        mb.append("</table>");

        return mb;
    }
}
