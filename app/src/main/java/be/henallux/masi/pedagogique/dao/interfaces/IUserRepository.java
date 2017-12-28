package be.henallux.masi.pedagogique.dao.interfaces;

import be.henallux.masi.pedagogique.model.User;

/**
 * Created by Le Roi Arthur on 28-12-17.
 */

public interface IUserRepository {
    User getUserByUsername(String username);
    User accessGranted(String username, String password);
}
