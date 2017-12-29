package be.henallux.masi.pedagogique.dao.interfaces;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Result;

/**
 * Created by hendrikdebeuf2 on 29/12/17.
 */

public interface IResultRepository {
    ArrayList<Result> getAllResults(int groupId);
}
