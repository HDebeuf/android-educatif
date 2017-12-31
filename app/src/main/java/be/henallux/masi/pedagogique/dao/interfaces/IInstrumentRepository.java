package be.henallux.masi.pedagogique.dao.interfaces;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.Instrument;

/**
 * Created by hendrikdebeuf2 on 30/12/17.
 */

public interface IInstrumentRepository {
    ArrayList<Instrument> getAllInstruments ();
}
