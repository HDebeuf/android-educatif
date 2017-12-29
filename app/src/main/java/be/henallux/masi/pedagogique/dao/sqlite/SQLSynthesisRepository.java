package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.Synthesis;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.SynthesisImage;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.SynthesisVideo;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.SynthesisWebView;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.entities.SynthesisImageEntity;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.entities.SynthesisVideoEntity;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.entities.SynthesisWebViewEntity;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.dao.interfaces.ISynthesisRepository;
import be.henallux.masi.pedagogique.dao.sqlite.entities.UserEntity;
import be.henallux.masi.pedagogique.model.Category;
import be.henallux.masi.pedagogique.model.User;

/**
 * Created by Le Roi Arthur on 29-12-17.
 */

public class SQLSynthesisRepository implements ISynthesisRepository {

    private Context context;

    public SQLSynthesisRepository(Context context) {
        this.context = context;
    }

    @Override
    public ArrayList<Synthesis> getAllSynthesisOfLocation(Location l) {
        ArrayList<Synthesis> allSynthesis = new ArrayList<>();
        allSynthesis.addAll(getAllVideoSynthesis(l));
        allSynthesis.addAll(getAllImageSynthesis(l));
        allSynthesis.addAll(getAllWebViewSynthesis(l));
        return allSynthesis;
    }

    private ArrayList<Synthesis> getAllVideoSynthesis(Location l) {

        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);
        ArrayList<Synthesis> synthesises = new ArrayList<>();
        Cursor cursor = db.query(SynthesisVideoEntity.TABLE,
                new String[]{SynthesisVideoEntity.COLUMN_ID,SynthesisVideoEntity.COLUMN_TEXT,SynthesisVideoEntity.COLUMN_URL_VIDEO},
                SynthesisVideoEntity.COLUMN_FK_LOCATION + "=?",
                new String[]{String.valueOf(l.getId())},
                null, null, null);



        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String text = cursor.getString(1);
            String urlString = cursor.getString(2);
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                Log.e("malformedURL","Malformed URL : " + urlString);}
            synthesises.add(new SynthesisVideo(id,text,url));
            cursor.moveToNext();
        }

        cursor.close();
        return synthesises;
    }

    private ArrayList<Synthesis> getAllImageSynthesis(Location l) {
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);
        ArrayList<Synthesis> synthesises = new ArrayList<>();
        Cursor cursor = db.query(SynthesisImageEntity.TABLE,
                new String[]{SynthesisImageEntity.COLUMN_ID,SynthesisImageEntity.COLUMN_TEXT, SynthesisImageEntity.COLUMN_URL_IMAGE},
                SynthesisImageEntity.COLUMN_FK_LOCATION + "=?",
                new String[]{String.valueOf(l.getId())},
                null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String text = cursor.getString(1);
            String urlString = cursor.getString(2);
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                Log.e("malformedURL","Malformed URL : " + urlString);}
            synthesises.add(new SynthesisImage(id,text,url));
            cursor.moveToNext();
        }

        cursor.close();
        return synthesises;
    }

    private ArrayList<Synthesis> getAllWebViewSynthesis(Location l) {
        SQLiteDatabase db = SQLiteHelper.getDatabaseInstance(context);
        ArrayList<Synthesis> synthesises = new ArrayList<>();
        Cursor cursor = db.query(SynthesisWebViewEntity.TABLE,
                new String[]{SynthesisWebViewEntity.COLUMN_ID,SynthesisWebViewEntity.COLUMN_TEXT, SynthesisWebViewEntity.COLUMN_URL},
                SynthesisWebViewEntity.COLUMN_FK_LOCATION + "=?",
                new String[]{String.valueOf(l.getId())},
                null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String text = cursor.getString(1);
            String urlString = cursor.getString(2);
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                Log.e("malformedURL","Malformed URL : " + urlString);}
            synthesises.add(new SynthesisWebView(id,text,url));
            cursor.moveToNext();
        }

        cursor.close();
        return synthesises;
    }
}
