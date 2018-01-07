package be.henallux.masi.pedagogique.dao.interfaces;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;
import be.henallux.masi.pedagogique.model.Group;

/**
 * Created by hendrikdebeuf2 on 30/12/17.
 */

public interface IInstrumentRepository {
    ArrayList<Instrument> getAllInstruments ();
    Instrument getInstrumentOfLocation(int id);
    ArrayList<Instrument> getInstrumentsOfGroup(Group g);
    void setIsUnlocked(int instrumentId, int groupId);
}
