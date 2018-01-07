package be.henallux.masi.pedagogique.dao.interfaces;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Answer;
import be.henallux.masi.pedagogique.model.Group;

/**
 * Created by hendrikdebeuf2 on 7/01/18.
 */

public interface IResultRepository {
    void sendResult (ArrayList<Answer> givenAnswerArrayList, Group group);
}
