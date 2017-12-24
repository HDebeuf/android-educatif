package be.henallux.masi.pedagogique.activities.loginActivity;

import be.henallux.masi.pedagogique.model.User;

/**
 * Created by haubo on 12/22/2017.
 */

public interface ILoginActivityRepository {
    int getCount();
    User getUserByUsername(String username);
    boolean accessGranted(String username, String password);
}
