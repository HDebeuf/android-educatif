package be.henallux.masi.pedagogique.utils;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Answer;

/**
 * Created by hendrikdebeuf2 on 7/01/18.
 */

public interface IMailComposer {
    StringBuilder composeResultsGrid(ArrayList<Answer> givenAnswers);
}
