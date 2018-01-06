package be.henallux.masi.pedagogique.dao.interfaces;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Answer;

/**
 * Created by Le Roi Arthur on 06-01-18.
 */

public interface IAnswerRepository {
    ArrayList<Answer> getAnswersOfQuestion(int questionID);
}
