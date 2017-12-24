package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.google.common.hash.Hashing;
import com.mooveit.library.Fakeit;

import java.nio.charset.StandardCharsets;

import be.henallux.masi.pedagogique.activities.historyActivity.LocationInfoActivity;
import be.henallux.masi.pedagogique.activities.mapActivity.ActivityMapBase;
import be.henallux.masi.pedagogique.activities.mapActivity.ActivityMapBaseEntity;
import be.henallux.masi.pedagogique.activities.mapActivity.LocationEntity;
import be.henallux.masi.pedagogique.activities.mapActivity.MapsActivity;
import be.henallux.masi.pedagogique.activities.musicalActivity.MusicalActivity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.ActivityEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.AnswerEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.CategoryEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.CategoryToActivityEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.ClassEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.GroupEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.QuestionEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.QuestionnaireEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.UserEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.UserToGroupEntity;
import be.henallux.masi.pedagogique.utils.ICryptographyService;
import be.henallux.masi.pedagogique.utils.SHA256CryptographyService;


/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String CREATE_TABLE_CLASS =
            "create table "
                    + ClassEntity.TABLE + "("
                    + ClassEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + ClassEntity.COLUMN_DESCRIPTION + " varchar(45) not null)";
    public static final String CREATE_TABLE_USER =
            "create table "
                    + UserEntity.TABLE + "("
                    + UserEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + UserEntity.COLUMN_FIRSTNAME + " varchar(45) not null,"
                    + UserEntity.COLUMN_LASTNAME + " varchar(45) not null,"
                    + UserEntity.COLUMN_USERNAME + " varchar(45) not null,"
                    + UserEntity.COLUMN_PASSWORDHASH + " varchar(64) not null,"
                    + UserEntity.COLUMN_GENDER + " integer not null,"
                    + UserEntity.COLUMN_URI_AVATAR + " varchar(45),"
                    + UserEntity.COLUMN_FK_CLASS + " integer not null,"
                    + "foreign key (" + UserEntity.COLUMN_FK_CLASS + ") references " + ClassEntity.TABLE + "(" + ClassEntity.COLUMN_ID + "))";
    public static final String CREATE_TABLE_USERTOGROUP =
            "create table "
                    + UserToGroupEntity.TABLE + "("
                    + UserToGroupEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + UserToGroupEntity.COLUMN_FK_GROUP + " integer not null,"
                    + UserToGroupEntity.COLUMN_FK_USER + " integer not null,"
                    + "foreign key (" + UserToGroupEntity.COLUMN_FK_USER + ") references " + UserEntity.TABLE + "(" + UserEntity.COLUMN_ID + "),"
                    + "foreign key (" + UserToGroupEntity.COLUMN_FK_GROUP + ") references " + GroupEntity.TABLE + "(" + GroupEntity.COLUMN_ID + "))";
    public static final String CREATE_TABLE_GROUP =
            "create table "
                    + GroupEntity.TABLE + "("
                    + GroupEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + GroupEntity.COLUMN_FK_CATEGORY + " integer not null,"
                    + "foreign key (" + GroupEntity.COLUMN_FK_CATEGORY + ") references " + CategoryEntity.TABLE + "(" + CategoryEntity.COLUMN_ID + "))";
    public static final String CREATE_TABLE_CATEGORY =
            "create table "
                    + CategoryEntity.TABLE + "("
                    + CategoryEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + CategoryEntity.COLUMN_AGE_MAX + " integer not null, "
                    + CategoryEntity.COLUMN_AGE_MIN + " integer not null,"
                    + CategoryEntity.COLUMN_DESCRIPTION + " varchar(20) not null)";
    public static final String CREATE_TABLE_CATEGORYTOACTIVITY =
            "create table "
                    + CategoryToActivityEntity.TABLE + "("
                    + CategoryToActivityEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + CategoryToActivityEntity.COLUMN_FK_ACTIVITY + " integer not null, "
                    + CategoryToActivityEntity.COLUMN_FK_CATEGORY + " integer not null,"
                    + "foreign key (" + CategoryToActivityEntity.COLUMN_FK_CATEGORY + ") references " + CategoryEntity.TABLE + "(" + CategoryEntity.COLUMN_ID + "),"
                    + "foreign key (" + CategoryToActivityEntity.COLUMN_FK_ACTIVITY + ") references " + ActivityEntity.TABLE + "(" + ActivityEntity.COLUMN_ID + "))";
    public static final String CREATE_TABLE_ACTIVITY =
            "create table "
                    + ActivityEntity.TABLE + "("
                    + ActivityEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + ActivityEntity.COLUMN_NAME + " varchar(45) not null,"
                    + ActivityEntity.COLUMN_ACTIVITY_CANONICAL_CLASS_NAME + " varchar(150) not null,"
                    + ActivityEntity.COLUMN_CLASS_CANONICAL_CLASS_NAME + " varchar(150) not null,"
                    + ActivityEntity.COLUMN_FK_QUESTIONNAIRE + " integer not null,"
                    + "foreign key (" + ActivityEntity.COLUMN_FK_QUESTIONNAIRE + ") references " + QuestionnaireEntity.TABLE + "(" + QuestionnaireEntity.COLUMN_ID + "))";
    public static final String CREATE_TABLE_QUESTIONNAIRE =
            "create table "
                    + QuestionnaireEntity.TABLE + "("
                    + QuestionnaireEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + QuestionnaireEntity.COLUMN_STATEMENT + " varchar(50) not null)";
    public static final String CREATE_TABLE_QUESTION =
            "create table "
                    + QuestionEntity.TABLE + "("
                    + QuestionEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + QuestionEntity.COLUMN_STATEMENT + " varchar(50) not null, "
                    + QuestionEntity.COLUMN_TYPE + " integer not null,"
                    + QuestionEntity.COLUMN_FK_QUESTIONNAIRE + " integer not null, foreign key (" + QuestionEntity.COLUMN_FK_QUESTIONNAIRE + ") references " + QuestionnaireEntity.TABLE + "(" + QuestionnaireEntity.COLUMN_ID + "))";
    public static final String CREATE_TABLE_ANSWER =
            "create table "
                    + AnswerEntity.TABLE + "("
                    + AnswerEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + AnswerEntity.COLUMN_STATEMENT + " varchar(50) not null,"
                    + AnswerEntity.COLUMN_FK_QUESTION + " integer not null, foreign key (" + AnswerEntity.COLUMN_FK_QUESTION + ") references " + QuestionEntity.TABLE + "(" + QuestionEntity.COLUMN_ID + "))";

    private static final String DATABASE_NAME = "educative.db";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteDatabase databaseInstance;
    private ICryptographyService cryptographyService;
    private Context context;


    private SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.cryptographyService = new SHA256CryptographyService();
    }

    public static SQLiteDatabase getDatabaseInstance(Context ctx) {
        if (databaseInstance == null) {
            SQLiteHelper helper = new SQLiteHelper(ctx);
            databaseInstance = helper.getWritableDatabase();
        }
        return databaseInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("drop table if exists " + ClassEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + UserEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + CategoryEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + GroupEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + UserToGroupEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + QuestionnaireEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + QuestionEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + AnswerEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + ActivityEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + ActivityMapBaseEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + ActivityEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + LocationEntity.TABLE);

        sqLiteDatabase.execSQL(CREATE_TABLE_CLASS);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORY);
        sqLiteDatabase.execSQL(CREATE_TABLE_GROUP);
        sqLiteDatabase.execSQL(CREATE_TABLE_USERTOGROUP);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUESTIONNAIRE);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUESTION);
        sqLiteDatabase.execSQL(CREATE_TABLE_ANSWER);
        sqLiteDatabase.execSQL(CREATE_TABLE_ACTIVITY);


        // For all modules
        sqLiteDatabase.execSQL(ActivityMapBaseEntity.CREATE_TABLE);
        sqLiteDatabase.execSQL(LocationEntity.CREATE_TABLE_LOCATION);

        // End modules

        sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORYTOACTIVITY);
        insert(sqLiteDatabase);
    }

    private void insert(SQLiteDatabase database) {
        Fakeit.init();
        ContentValues values = new ContentValues();
        values.put(ClassEntity.COLUMN_DESCRIPTION, "1A");
        database.insert(ClassEntity.TABLE, null, values);

        values.clear();
        values.put(CategoryEntity.COLUMN_AGE_MAX, 6);
        values.put(CategoryEntity.COLUMN_AGE_MIN, 8);
        values.put(CategoryEntity.COLUMN_DESCRIPTION, "Cycle supérieur");
        int idCategorySuperior = (int) database.insert(CategoryEntity.TABLE, null, values);

        values.clear();
        values.put(CategoryEntity.COLUMN_AGE_MAX, 6);
        values.put(CategoryEntity.COLUMN_AGE_MIN, 8);
        values.put(CategoryEntity.COLUMN_DESCRIPTION, "Cycle inférieur");
        int idCategoryInferior = (int) database.insert(CategoryEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "Questionnaire de test");
        int idQuestionnaire = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        //region MapsActivityHistory

        values.clear();
        values.put(ActivityEntity.COLUMN_NAME, "Activité historique");
        values.put(ActivityEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaire);
        values.put(ActivityEntity.COLUMN_ACTIVITY_CANONICAL_CLASS_NAME, MapsActivity.class.getName());
        values.put(ActivityEntity.COLUMN_CLASS_CANONICAL_CLASS_NAME, ActivityMapBase.class.getName());
        int activityId = (int) database.insert(ActivityEntity.TABLE, null, values);

        values.clear();
        values.put(ActivityMapBaseEntity.COLUMN_FK_ACTIVITY, activityId);
        values.put(ActivityMapBaseEntity.COLUMN_NAME, "Histoire de la Belgique");
        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/raw/maps_activity_history_json_style");
        values.put(ActivityMapBaseEntity.COLUMN_STYLE, uri.toString());
        values.put(ActivityMapBaseEntity.COLUMN_LATITUDE_CENTER, 50.8468);
        values.put(ActivityMapBaseEntity.COLUMN_LONGITUDE_CENTER, 4.3775);
        values.put(ActivityMapBaseEntity.COLUMN_ZOOM, 8);
        int idActivityMap = (int) database.insert(ActivityMapBaseEntity.TABLE, null, values);

        //endregion

        //region Locations
        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Butte de Waterloo");
        values.put(LocationEntity.COLUMN_LATITUDE, 50.678542);
        values.put(LocationEntity.COLUMN_LONGITUDE, 4.404887);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, LocationInfoActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Manneken pis");
        values.put(LocationEntity.COLUMN_LATITUDE, 50.845007);
        values.put(LocationEntity.COLUMN_LONGITUDE, 4.349971);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, LocationInfoActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(CategoryToActivityEntity.COLUMN_FK_ACTIVITY, activityId);
        values.put(CategoryToActivityEntity.COLUMN_FK_CATEGORY, idCategorySuperior);
        database.insert(CategoryToActivityEntity.TABLE, null, values);

        //endregion

        //region MusicActivity
        values.clear();
        values.put(ActivityEntity.COLUMN_NAME, "Activité musicale");
        values.put(ActivityEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaire);
        values.put(ActivityEntity.COLUMN_ACTIVITY_CANONICAL_CLASS_NAME, MapsActivity.class.getName());
        values.put(ActivityEntity.COLUMN_CLASS_CANONICAL_CLASS_NAME, ActivityMapBase.class.getName());
        activityId = (int) database.insert(ActivityEntity.TABLE, null, values);

        values.clear();
        values.put(ActivityMapBaseEntity.COLUMN_FK_ACTIVITY, activityId);
        values.put(ActivityMapBaseEntity.COLUMN_NAME, "Histoire de la musique");
        values.put(ActivityMapBaseEntity.COLUMN_LATITUDE_CENTER, 50.8468);
        values.put(ActivityMapBaseEntity.COLUMN_LONGITUDE_CENTER, 4.3775);
        values.put(ActivityMapBaseEntity.COLUMN_ZOOM, 0.5);
        idActivityMap = (int) database.insert(ActivityMapBaseEntity.TABLE, null, values);

        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Asie");
        values.put(LocationEntity.COLUMN_LATITUDE, 39.898369);
        values.put(LocationEntity.COLUMN_LONGITUDE, 97.346919);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, MusicalActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Afrique");
        values.put(LocationEntity.COLUMN_LATITUDE, 3.711932);
        values.put(LocationEntity.COLUMN_LONGITUDE, 21.880014);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, MusicalActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(CategoryToActivityEntity.COLUMN_FK_ACTIVITY, activityId);
        values.put(CategoryToActivityEntity.COLUMN_FK_CATEGORY, idCategoryInferior);
        database.insert(CategoryToActivityEntity.TABLE, null, values);

        //endregion

        //region users
        int iUser;
        String firstName,lastName;
        for (iUser = 1; iUser < 61; iUser++) {
            values.clear();
            firstName = Fakeit.name().firstName();
            lastName = Fakeit.name().lastName();
            String pwd = "Tigrou007";
            values.put(UserEntity.COLUMN_FIRSTNAME,firstName);
            values.put(UserEntity.COLUMN_LASTNAME,lastName);
            values.put(UserEntity.COLUMN_USERNAME,firstName+lastName);
            values.put(UserEntity.COLUMN_GENDER, 1);
            values.put(UserEntity.COLUMN_FK_CLASS, 1);
            String sha256pwd = cryptographyService.hashPassword("Tigrou007");
            values.put(UserEntity.COLUMN_PASSWORDHASH,sha256pwd);
            database.insert(UserEntity.TABLE,null,values);
        }
        //endregion

        //region Groups
        values.clear();
        values.put(GroupEntity.COLUMN_FK_CATEGORY,1);
        database.insert(GroupEntity.TABLE,null,values);
        values.clear();
        values.put(GroupEntity.COLUMN_FK_CATEGORY,2);
        database.insert(GroupEntity.TABLE,null,values);
        //endregion

        //region UsersToGroups
        values.clear();
        int iUserToGroup;
        for (iUserToGroup = 1; iUserToGroup < 31; iUserToGroup++) {
            values.clear();
            values.put(UserToGroupEntity.COLUMN_FK_USER,iUserToGroup);
            values.put(UserToGroupEntity.COLUMN_FK_GROUP,1);
            database.insert(UserToGroupEntity.TABLE, null, values);
        }
        for (iUserToGroup = 31; iUserToGroup < 61; iUserToGroup++) {
            values.clear();
            values.put(UserToGroupEntity.COLUMN_FK_USER,iUserToGroup);
            values.put(UserToGroupEntity.COLUMN_FK_GROUP,2);
            database.insert(UserToGroupEntity.TABLE, null, values);
        }
        //endregion
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
