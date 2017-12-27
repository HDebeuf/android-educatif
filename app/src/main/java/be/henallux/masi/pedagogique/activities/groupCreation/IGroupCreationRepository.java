package be.henallux.masi.pedagogique.activities.groupCreation;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Category;
import be.henallux.masi.pedagogique.model.User;

/**
 * Created by haubo on 12/27/2017.
 */

public interface IGroupCreationRepository {
    ArrayList<User> GetUsersByCaterogy(int categoryId, int userId);
    Category getCategoryOfUser(int categoryId);

}
