package be.henallux.masi.pedagogique.dao.interfaces;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Question;
import be.henallux.masi.pedagogique.model.Questionnaire;


/**
 * Created by Angele on 05/01/2018.
 */

public interface IQuestionnaireRepository {
<<<<<<< HEAD
    ArrayList<Question> getAllQuestion(Questionnaire questionnaire);
    Questionnaire getQuestionnaire(int idlocation);

=======
    ArrayList<Question> getAllQuestionOfQuestionnaire(Questionnaire questionnaire);
    Questionnaire getQuestionnaireById(int idQuestionnaire);
>>>>>>> e18c0f2df4e6e0ad7a0fe5aa2642c82933715529
}
