package be.henallux.masi.pedagogique.utils;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Answer;
import be.henallux.masi.pedagogique.model.Group;

/**
 * Created by hendrikdebeuf2 on 7/01/18.
 */

public interface IMailComposer {
    StringBuilder composeResultGridHTML(Group group, ArrayList<Answer> givenAnswers);
    StringBuilder composeResultGridPlaintext(Group group, ArrayList<Answer> givenAnswers);
}
