package be.henallux.masi.pedagogique.dao.interfaces;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Category;
import be.henallux.masi.pedagogique.model.User;

/**
 * Created by Le Roi Arthur on 28-12-17.
 */

public interface ICategoryRepository {
    Category getCategoryById(int id);
    ArrayList<User> getAllUsersOfCategory(Category c);
}
