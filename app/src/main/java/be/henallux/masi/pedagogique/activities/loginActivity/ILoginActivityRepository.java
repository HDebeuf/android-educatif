package be.henallux.masi.pedagogique.activities.loginActivity;

/**
 * Created by haubo on 12/22/2017.
 */

public interface ILoginActivityRepository {
    public int getCount();
    public int getID(String log);
    public String getPwdHash(int id);
}
