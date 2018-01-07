package be.henallux.masi.pedagogique.dao.interfaces;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Question;

/**
 * Created by Le Roi Arthur on 06-01-18.
 */

public interface IQuestionRepository {
    ArrayList<Question> getQuestionsOfQuestionnaire(int id);
    String getQuestionName(int questionId);
}
