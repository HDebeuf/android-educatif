package be.henallux.masi.pedagogique.dao.interfaces;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.model.Group;
import be.henallux.masi.pedagogique.model.User;

/**
 * Created by Le Roi Arthur on 28-12-17.
 */

public interface IGroupRepository {
    Group createGroup(ArrayList<User> users);
}
