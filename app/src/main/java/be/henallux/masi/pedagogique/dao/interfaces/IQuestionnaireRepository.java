package be.henallux.masi.pedagogique.dao.interfaces;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Question;
import be.henallux.masi.pedagogique.model.Questionnaire;


/**
 * Created by Angele on 05/01/2018.
 */

public interface IQuestionnaireRepository {
    ArrayList<Question> getAllQuestion(Questionnaire questionnaire);
    Questionnaire getQuestionnaire(int idlocation);

}
