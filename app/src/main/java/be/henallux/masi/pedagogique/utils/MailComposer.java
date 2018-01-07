package be.henallux.masi.pedagogique.utils;

import android.content.Context;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.dao.interfaces.IAnswerRepository;
import be.henallux.masi.pedagogique.dao.interfaces.IQuestionRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteQuestionRepository;
import be.henallux.masi.pedagogique.model.Answer;
import be.henallux.masi.pedagogique.model.Group;
import be.henallux.masi.pedagogique.model.User;

/**
 * Created by hendrikdebeuf2 on 7/01/18.
 */

public class MailComposer implements IMailComposer {

    private Context context;

    public MailComposer(Context context) {
        this.context = context;
    }

    public StringBuilder composeResultGridHTML(Group group,ArrayList<Answer> givenAnswers){

        SQLiteQuestionRepository questionRepository = SQLiteQuestionRepository.getInstance(context);
        ArrayList<Answer> fullAnswerList = new ArrayList<Answer>();
        for (Answer answer : givenAnswers) {
            String questionName = questionRepository.getQuestionName(answer.getQuestion().getId());
            int point = 0;
            if(answer.isCorrect()){
                point = 1;
            }
            Answer fullAnswer = new Answer(answer.getId(),answer.getStatement(),answer.isCorrect(),point,answer.getQuestion());
            fullAnswerList.add(fullAnswer);
        }

        StringBuilder mb = new StringBuilder();

        mb.append("<html>");
        mb.append("<table width=\"600\" style=\"border:1px solid #333\">");
        mb.append("<tr>");
        mb.append("<th>Question</th>");
        mb.append("<th>Réponse</th>");
        mb.append("<th>Point</th>");
        mb.append("</tr>");

        for (Answer fullAnswer : fullAnswerList) {
            mb.append("<tr>");
            mb.append("<td>");
            mb.append(fullAnswer.getQuestion().getStatement());
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
        mb.append("</html>");

        return mb;
    }

    @Override
    public StringBuilder composeResultGridPlaintext(Group group, ArrayList<Answer> givenAnswers) {
        SQLiteQuestionRepository questionRepository = SQLiteQuestionRepository.getInstance(context);
        ArrayList<Answer> fullAnswerList = new ArrayList<Answer>();
        for (Answer answer : givenAnswers) {
            String questionName = questionRepository.getQuestionName(answer.getQuestion().getId());
            int point = 0;
            if(answer.isCorrect()){
                point = 1;
            }
            Answer fullAnswer = new Answer(answer.getId(),answer.getStatement(),answer.isCorrect(),point,answer.getQuestion());
            fullAnswerList.add(fullAnswer);
        }
        StringBuilder mb = new StringBuilder();
        mb.append("Membres du groupe : \n");
        for(User u : group.getMembers()){
            mb.append(u.getUsername() + "\n");
        }
        mb.append("\n");
        mb.append(context.getString(R.string.plaintext_header_answer_question) + "\n");
        for(Answer answer : fullAnswerList){
            mb.append(answer.getQuestion().getStatement() + " - " + answer.getStatement() + " - " + answer.getPoint() + "\n");
        }
        return  mb;
    }
}
