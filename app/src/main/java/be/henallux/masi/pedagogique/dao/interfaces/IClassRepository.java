package be.henallux.masi.pedagogique.dao.interfaces;

/**
 * Created by Le Roi Arthur on 29-12-17.
 */
import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Class;

public interface IClassRepository {
    Class getClassById(int id);
    ArrayList<Class> getAllClasses();
}
