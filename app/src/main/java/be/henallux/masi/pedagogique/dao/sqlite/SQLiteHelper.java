package be.henallux.masi.pedagogique.dao.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.mooveit.library.Fakeit;

import be.henallux.masi.pedagogique.activities.historyActivity.LocationInfoActivity;
import be.henallux.masi.pedagogique.activities.historyActivity.MapHistoryActivity;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.entities.SynthesisImageEntity;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.entities.SynthesisVideoEntity;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.entities.SynthesisWebViewEntity;
import be.henallux.masi.pedagogique.activities.mapActivity.ActivityMapBase;
import be.henallux.masi.pedagogique.activities.mapActivity.ActivityMapBaseEntity;
import be.henallux.masi.pedagogique.activities.mapActivity.LocationEntity;
import be.henallux.masi.pedagogique.activities.musicalActivity.MapMusicalActivity;
import be.henallux.masi.pedagogique.activities.musicalActivity.MusicalActivity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.ActivityEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.AnswerEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.AnswerToQuestionEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.CategoryEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.CategoryToActivityEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.ClassEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.GroupEntity;
import be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.entities.InstrumentEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.InstrumentUnlockedEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.QuestionEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.QuestionnaireEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.ResultEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.UserEntity;
import be.henallux.masi.pedagogique.dao.sqlite.entities.UserToGroupEntity;
import be.henallux.masi.pedagogique.model.Answer;
import be.henallux.masi.pedagogique.model.Question;
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
                    + UserEntity.COLUMN_FK_CATEGORY + " integer not null,"
                    + "foreign key (" + UserEntity.COLUMN_FK_CATEGORY + ") references " + CategoryEntity.TABLE + "(" + CategoryEntity.COLUMN_ID + "),"
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
                    + GroupEntity.COLUMN_ID + " integer primary key autoincrement);";
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
                    + ActivityEntity.COLUMN_FK_QUESTIONNAIRE + " integer,"
                    + ActivityEntity.COLUMN_URI_ICON + " varchar(20),"
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
                    + QuestionEntity.COLUMN_FK_QUESTIONNAIRE + " integer not null,"
                    + " foreign key (" + QuestionEntity.COLUMN_FK_QUESTIONNAIRE + ") references " + QuestionnaireEntity.TABLE + "(" + QuestionnaireEntity.COLUMN_ID + "))";
    public static final String CREATE_TABLE_ANSWER =
            "create table "
                    + AnswerEntity.TABLE + "("
                    + AnswerEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + AnswerEntity.COLUMN_STATEMENT + " varchar(50) not null,"
                    + AnswerEntity.COLUMN_IS_TRUE+ " integer not null,"
                    + AnswerEntity.COLUMN_FK_QUESTION + " integer not null,"
                    + " foreign key (" + AnswerEntity.COLUMN_FK_QUESTION + ") references " + QuestionEntity.TABLE + "(" + QuestionEntity.COLUMN_ID + "))";
    public static final String CREATE_TABLE_RESULT =
            "create table "
                    + ResultEntity.TABLE + "("
                    + ResultEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + ResultEntity.COLUMN_NB_CORRECT + " integer not null,"
                    + ResultEntity.COLUMN_NB_WRONG + " integer not null,"
                    + ResultEntity.COLUMN_FK_GROUP + " integer not null,"
                    + " foreign key (" + ResultEntity.COLUMN_FK_GROUP + ") references " + GroupEntity.TABLE + "(" + GroupEntity.COLUMN_ID + "))";

    public static final String CREATE_TABLE_ANSWER_TO_QUESTION =
            "create table "
                    + AnswerToQuestionEntity.TABLE + "("
                    + AnswerToQuestionEntity.COLUMN_ID + " integer primary key autoincrement, "
                    + AnswerToQuestionEntity.COLUMN_FK_QUESTION + " integer not null,"
                    + AnswerToQuestionEntity.COLUMN_FK_RESULT + " integer not null,"
                    + AnswerToQuestionEntity.COLUMN_FK_ANSWER + " integer not null,"
                    + " foreign key (" + AnswerToQuestionEntity.COLUMN_FK_QUESTION + ") references " + QuestionEntity.TABLE + "(" + QuestionEntity.COLUMN_ID + "),"
                    + " foreign key (" + AnswerToQuestionEntity.COLUMN_FK_RESULT + ") references " + ResultEntity.TABLE + "(" + ResultEntity.COLUMN_ID + "),"
                    + " foreign key (" + AnswerToQuestionEntity.COLUMN_FK_ANSWER + ") references " + AnswerEntity.TABLE + "(" + AnswerEntity.COLUMN_ID + "))";

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
        sqLiteDatabase.execSQL("drop table if exists " + ResultEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + AnswerToQuestionEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + SynthesisVideoEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + SynthesisImageEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + SynthesisWebViewEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + InstrumentEntity.TABLE);
        sqLiteDatabase.execSQL("drop table if exists " + InstrumentUnlockedEntity.TABLE);

        sqLiteDatabase.execSQL(CREATE_TABLE_CLASS);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORY);
        sqLiteDatabase.execSQL(CREATE_TABLE_GROUP);
        sqLiteDatabase.execSQL(CREATE_TABLE_USERTOGROUP);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUESTIONNAIRE);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUESTION);
        sqLiteDatabase.execSQL(CREATE_TABLE_ANSWER);
        sqLiteDatabase.execSQL(CREATE_TABLE_ACTIVITY);
        sqLiteDatabase.execSQL(CREATE_TABLE_RESULT);
        sqLiteDatabase.execSQL(CREATE_TABLE_ANSWER_TO_QUESTION);


        // For all modules
        sqLiteDatabase.execSQL(ActivityMapBaseEntity.CREATE_TABLE);
        sqLiteDatabase.execSQL(LocationEntity.CREATE_TABLE_LOCATION);
        sqLiteDatabase.execSQL(SynthesisImageEntity.CREATE_TABLE_SYNTHESIS_IMAGE);
        sqLiteDatabase.execSQL(SynthesisVideoEntity.CREATE_TABLE_SYNTHESIS_VIDEO);
        sqLiteDatabase.execSQL(SynthesisWebViewEntity.CREATE_TABLE_SYNTHESIS_WEBVIEW);
        sqLiteDatabase.execSQL(InstrumentEntity.CREATE_TABLE_INSTRUMENTS);
        sqLiteDatabase.execSQL(InstrumentUnlockedEntity.CREATE_TABLE_INSTRUMENT_UNLOCKED);
        // End modules

        sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORYTOACTIVITY);
        insert(sqLiteDatabase);
    }

    private void insert(SQLiteDatabase database) {
        Fakeit.init();
        ContentValues values = new ContentValues();
        values.put(ClassEntity.COLUMN_DESCRIPTION, "1A");
        database.insert(ClassEntity.TABLE, null, values);

        //region Category
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
        //endregion

        //HISTORY ACTIVITY

        //Quizz
        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "Questionnaire Fédéraltion Wallonie-Bruxelles");
        int idQuestionnaireWallonieBruxelles = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "Questionnaire Communauté Flammande");
        int idQuestionnaireCommunauteFlamande = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "Questionnaire Communauté Germanophone");
        int idQuestionnaireGermanophone = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "Questionnaire Ostende");
        int idQuestionnaireOstende = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "Questionnaire Bruges");
        int idQuestionnaireBruges = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "Questionnaire Gand");
        int idQuestionnaireGand = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "Questionnaire Anvers");
        int idQuestionnaireAnvers = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "Questionnaire Bruxelles");
        int idQuestionnaireBruxelles = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "Questionnaire Charleroi");
        int idQuestionnaireCharleroi = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "Questionnaire Liège");
        int idQuestionnaireLiege = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "Questionnaire Eupen");
        int idQuestionnaireEupen = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "Questionnaire Namur");
        int idQuestionnaireNamur = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "Questionnaire Bastogne");
        int idQuestionnaireBastogne = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        //Questions
        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "Quelle est la date de la fête de la Fédération Wallonie-Bruxelles ?");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireWallonieBruxelles);
        int idQuestionWallonieBruxelles1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "Quelle est la date de la fête de la Communauté flamande ?");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireCommunauteFlamande);
        int idQuestionCommunauteFlamande1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "Que se passe-t-il le 15 novembre ?");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireGermanophone);
        int idQuestionGermanophone1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "Où se trouve la ville d'Ostende ?");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireOstende);
        int idQuestionOstende1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "Que est la spécialité de Bruges? De quand date-t-elle ?");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireBruges);
        int idQuestionBruges1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "C'est beau n'est-ce pas ?");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireGand);
        int idQuestionGand1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "Qu'est-ce qui caractérise le mieux les villes flamandes ?");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireAnvers);
        int idQuestionAnvers1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "A l'aide de tes propres connaissances. Que sais-tu de l'Atomimum ?");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireBruxelles);
        int idQuestionBruxelles1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "Pourquoi dit-on que Charleroi n'est pas une belle ville ?");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireCharleroi);
        int idQuestionCharleroi1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "Lequel est une spécialité liègeoise ?");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireLiege);
        int idQuestionLiege1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "Quelle langue parle-t-on à Eupen ?");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireEupen);
        int idQuestionEupen1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "A ton avis, pourquoi Namur est-elle la capitale de la Wallonie ?");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireNamur);
        int idQuestionNamur1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "Comment Bastogne se fait-il connaitre ?");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireBastogne);
        int idQuestionBastogne1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        //Answers
        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le 26 septembre");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionWallonieBruxelles1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le 21 juillet");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionWallonieBruxelles1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le 27 septembre");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionWallonieBruxelles1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Toutes les réponses sont correctes");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionWallonieBruxelles1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Aucune réponse n'est correcte");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionWallonieBruxelles1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le 21 juillet");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionCommunauteFlamande1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le 11 juillet");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionCommunauteFlamande1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le 14 juillet");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionCommunauteFlamande1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Toutes les réponses sont correctes");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionCommunauteFlamande1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Aucune réponse n'est correcte");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionCommunauteFlamande1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "La fête du roi");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionGermanophone1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "La fête de la communauté germanophone");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionGermanophone1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "C'est la veille du 16 novembre");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionGermanophone1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Toutes les réponses sont correctes");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionGermanophone1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Aucune réponse n'est correcte");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionGermanophone1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "En France");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionOstende1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Dans les Ardennes");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionOstende1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "À la côte de la Belgique");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionOstende1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Toutes les réponses sont correctes");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionOstende1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Aucune réponse n'est correcte");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionOstende1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le chocolat depuis le Moyen Age");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionBruges1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Les frites depuis 1830");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionBruges1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "La dentelle depuis le Moyen Age");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionBruges1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Toutes les réponses sont correctes");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionBruges1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Aucune réponse n'est correcte");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionBruges1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Oui");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionGand1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Non");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionGand1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "L'architecture");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAnvers1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "L'industrie");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAnvers1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "La mode");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAnvers1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Toutes les réponses sont correctes");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAnvers1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Aucune réponse n'est correcte");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAnvers1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Il peut représenter les provinces de Belgique");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionBruxelles1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Au départ, il ne devait rester là que pendant 6 mois");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionBruxelles1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Il acceuille des expositions et ce encore aujourd'hui");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionBruxelles1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Toutes les réponses sont correctes");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionBruxelles1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Aucune réponse n'est correcte");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionBruxelles1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Car c'est très industriel");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionCharleroi1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Car il y a beaucoup de ruines");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionCharleroi1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Car la ville est pleine de déchets");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionCharleroi1);
        database.insert(AnswerEntity.TABLE, null, values);


        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Toutes les réponses sont correctes");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionCharleroi1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Aucune réponse n'est correcte");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionCharleroi1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Les pâtes");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionLiege1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "La gauffre");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionLiege1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "La glace");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionLiege1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Toutes les réponses sont correctes");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionLiege1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Aucune réponse n'est correcte");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionLiege1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Français");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionEupen1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Allemand");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionEupen1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Flammand");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionEupen1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Toutes les réponses sont correctes");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionEupen1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Aucune réponse n'est correcte");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionEupen1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Car elle est centrale");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionNamur1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Car c'est la plus grosse ville de Wallonie");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionNamur1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Car c'est la ville la plus peuplée de Wallonie");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionNamur1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Toutes les réponses sont correctes");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionNamur1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Aucune réponse n'est correcte");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionNamur1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Car c'est la plus grande ville de la province du Luxembourg");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionBastogne1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Suite à la première guerre mondiale");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionBastogne1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Suite à la bataille de Ardenne");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionBastogne1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Toutes les réponses sont correctes");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionBastogne1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Aucune réponse n'est correcte");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionBastogne1);
        database.insert(AnswerEntity.TABLE, null, values);


        // Charleroi, Liège, Eupen, Namur and Bastogne to add


        //region MapsActivityHistory
        values.clear();
        values.put(ActivityEntity.COLUMN_NAME, "Histoire de la Belgique");
        values.put(ActivityEntity.COLUMN_FK_QUESTIONNAIRE, "");
        values.put(ActivityEntity.COLUMN_ACTIVITY_CANONICAL_CLASS_NAME, MapHistoryActivity.class.getName());
        values.put(ActivityEntity.COLUMN_CLASS_CANONICAL_CLASS_NAME, ActivityMapBase.class.getName());
        Uri uriIcon = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/book_icon");
        values.put(ActivityEntity.COLUMN_URI_ICON,uriIcon.toString());
        int activityId = (int) database.insert(ActivityEntity.TABLE, null, values);

        values.clear();
        values.put(ActivityMapBaseEntity.COLUMN_FK_ACTIVITY, activityId);
        Uri uriJsonStyle = Uri.parse("android.resource://" + context.getPackageName() + "/raw/maps_activity_history_json_style");
        values.put(ActivityMapBaseEntity.COLUMN_STYLE, uriJsonStyle.toString());
        values.put(ActivityMapBaseEntity.COLUMN_LATITUDE_CENTER, 50.8468);
        values.put(ActivityMapBaseEntity.COLUMN_LONGITUDE_CENTER, 4.3775);
        values.put(ActivityMapBaseEntity.COLUMN_ZOOM, 8);
        int idActivityMap = (int) database.insert(ActivityMapBaseEntity.TABLE, null, values);
        //endregion

        //region Locations
        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Fédération Wallonie-Bruxelles");
        values.put(LocationEntity.COLUMN_LATITUDE, 50.678542);
        values.put(LocationEntity.COLUMN_LONGITUDE, 4.404887);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, LocationInfoActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireWallonieBruxelles);
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        int idWallonieBruxelles = (int) database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Communauté Flamande");
        values.put(LocationEntity.COLUMN_LATITUDE, 50.946412);
        values.put(LocationEntity.COLUMN_LONGITUDE, 4.040334);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, LocationInfoActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireCommunauteFlamande);
        int idCOmmunauteFlamande = (int) database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Communauté Germanophone");
        values.put(LocationEntity.COLUMN_LATITUDE, 50.639930);
        values.put(LocationEntity.COLUMN_LONGITUDE, 6.032238);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, LocationInfoActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireGermanophone);
        int idGermanophone = (int) database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Ostende");
        values.put(LocationEntity.COLUMN_LATITUDE, 51.215607);
        values.put(LocationEntity.COLUMN_LONGITUDE, 2.928504);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, LocationInfoActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireOstende);
        int idOstende = (int)database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Bruges");
        values.put(LocationEntity.COLUMN_LATITUDE,51.209364);
        values.put(LocationEntity.COLUMN_LONGITUDE,3.222772);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, LocationInfoActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireBruges);
        int idBruges = (int) database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Gand");
        values.put(LocationEntity.COLUMN_LATITUDE,51.053878);
        values.put(LocationEntity.COLUMN_LONGITUDE,3.717913);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, LocationInfoActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireGand);
        int idGand = (int) database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Anvers");
        values.put(LocationEntity.COLUMN_LATITUDE,51.219893);
        values.put(LocationEntity.COLUMN_LONGITUDE,4.405093);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, LocationInfoActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireAnvers);
        int idAnvers = (int) database.insert(LocationEntity.TABLE, null, values);values.clear();

        values.put(LocationEntity.COLUMN_TITLE, "Bruxelles");
        values.put(LocationEntity.COLUMN_LATITUDE,50.855584);
        values.put(LocationEntity.COLUMN_LONGITUDE, 4.336267);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, LocationInfoActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireBruxelles);
        int idBruxelles = (int) database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Charleroi");
        values.put(LocationEntity.COLUMN_LATITUDE,50.408776);
        values.put(LocationEntity.COLUMN_LONGITUDE, 4.445191);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, LocationInfoActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireCharleroi);
        int idCharleroi = (int) database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Liège");
        values.put(LocationEntity.COLUMN_LATITUDE,50.631883);
        values.put(LocationEntity.COLUMN_LONGITUDE, 5.586922);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireLiege);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, LocationInfoActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        int idLiege = (int) database.insert(LocationEntity.TABLE, null, values);

        values.put(LocationEntity.COLUMN_TITLE, "Eupen");
        values.put(LocationEntity.COLUMN_LATITUDE,50.629599);
        values.put(LocationEntity.COLUMN_LONGITUDE, 6.024611);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, LocationInfoActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireEupen);
        int idEupen = (int) database.insert(LocationEntity.TABLE, null, values);

        values.put(LocationEntity.COLUMN_TITLE, "Namur");
        values.put(LocationEntity.COLUMN_LATITUDE,50.476382);
        values.put(LocationEntity.COLUMN_LONGITUDE,4.927587);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, LocationInfoActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireNamur);
        int idNamur = (int) database.insert(LocationEntity.TABLE, null, values);

        values.put(LocationEntity.COLUMN_TITLE, "Bastogne");
        values.put(LocationEntity.COLUMN_LATITUDE,50.007363);
        values.put(LocationEntity.COLUMN_LONGITUDE,5.732305);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, LocationInfoActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireBastogne);
        int idBastogne = (int) database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(CategoryToActivityEntity.COLUMN_FK_ACTIVITY, activityId);
        values.put(CategoryToActivityEntity.COLUMN_FK_CATEGORY, idCategorySuperior);
        database.insert(CategoryToActivityEntity.TABLE, null, values);

        //region synthesis
        values.clear();
        values.put(SynthesisWebViewEntity.COLUMN_FK_LOCATION, idWallonieBruxelles);
        values.put(SynthesisWebViewEntity.COLUMN_URL, "https://www.rtl.be/info/video/216091.aspx");
        values.put(SynthesisWebViewEntity.COLUMN_TEXT,"La fête de la Fédération Wallonie-Bruxelles est 27 septembre en référence à une page importante de l'indépendance de la Belgique. La nuit du 26 au 27 septembre, les troupes hollondaises se retirent de Bruxelles. ");
        database.insert(SynthesisWebViewEntity.TABLE, null, values);

        values.clear();
        values.put(SynthesisWebViewEntity.COLUMN_FK_LOCATION, idCOmmunauteFlamande);
        values.put(SynthesisWebViewEntity.COLUMN_URL, "https://www.belgium.be/fr/la_belgique/connaitre_le_pays/la_belgique_en_bref/symboles/fete_nationale");
        values.put(SynthesisWebViewEntity.COLUMN_TEXT,"En flandre ...");
        database.insert(SynthesisWebViewEntity.TABLE, null, values);

        values.clear();
        values.put(SynthesisWebViewEntity.COLUMN_FK_LOCATION, idGermanophone);
        values.put(SynthesisWebViewEntity.COLUMN_TEXT, "Région germanophone");
        values.put(SynthesisWebViewEntity.COLUMN_URL,"https://www.belgium.be/fr/la_belgique/connaitre_le_pays/la_belgique_en_bref/symboles/fete_nationale");
        database.insert(SynthesisWebViewEntity.TABLE, null, values);

        values.clear();
        values.put(SynthesisVideoEntity.COLUMN_FK_LOCATION, idOstende);
        values.put(SynthesisVideoEntity.COLUMN_URL_VIDEO, "https://www.youtube.com/watch?v=8uMP3eiX_lU");
        values.put(SynthesisVideoEntity.COLUMN_TEXT,"Ostende est une ville du Littoral de la mer du nord, où l'on parle le nérelandais. On la surnomme la reine des stations balnaires. On y trouve de larges plages et plein de ports. ");
        database.insert(SynthesisVideoEntity.TABLE, null, values);

        values.clear();
        values.put(SynthesisVideoEntity.COLUMN_FK_LOCATION, idBruges);
        values.put(SynthesisVideoEntity.COLUMN_URL_VIDEO, "https://www.youtube.com/watch?v=o9ZjTEjgPaw");
        values.put(SynthesisVideoEntity.COLUMN_TEXT,"Bruges est une ville presque mondialement connue. Elle conserve l'architecture du Moyen Age.");
        database.insert(SynthesisVideoEntity.TABLE, null, values);

        values.clear();
        values.put(SynthesisVideoEntity.COLUMN_FK_LOCATION, idGand);
        values.put(SynthesisVideoEntity.COLUMN_URL_VIDEO, "https://www.youtube.com/watch?v=P2bVsc-oLW0");
        values.put(SynthesisVideoEntity.COLUMN_TEXT,"Gand est une ville historique et contemporaine. C'est la 2ème commune la plus peuplée de Belgique après Anvers. ");
        database.insert(SynthesisVideoEntity.TABLE, null, values);

        values.clear();
        values.put(SynthesisVideoEntity.COLUMN_FK_LOCATION, idAnvers);
        values.put(SynthesisVideoEntity.COLUMN_URL_VIDEO, "https://www.youtube.com/watch?v=qRPO6vsMtcM");
        values.put(SynthesisVideoEntity.COLUMN_TEXT,"Anvers est la ville la plus peuplée de Belgique. Elle abrite les plus prestigieux diamantaires et la plus importante bourse de diamants. Le port d'Anvers est la 2ème plus gros d'Europe.");
        database.insert(SynthesisVideoEntity.TABLE, null, values);

        values.clear();
        values.put(SynthesisVideoEntity.COLUMN_FK_LOCATION, idBruxelles);
        values.put(SynthesisVideoEntity.COLUMN_URL_VIDEO, "https://www.youtube.com/watch?v=eU0VEklIeW4");
        values.put(SynthesisVideoEntity.COLUMN_TEXT,"Bruxelles est la capitale de la Belgique ET de l'Europe.");
        database.insert(SynthesisVideoEntity.TABLE, null, values);

        values.clear();
        values.put(SynthesisWebViewEntity.COLUMN_FK_LOCATION, idCharleroi);
        values.put(SynthesisWebViewEntity.COLUMN_URL, "https://www.kisskissbankbank.com/charleroi-la-plus-moche-ville-du-monde");
        values.put(SynthesisWebViewEntity.COLUMN_TEXT,"Charleroi est une ville francophone . C'est un centre industriel et ancien centre minier.");
        database.insert(SynthesisWebViewEntity.TABLE, null, values);

        values.clear();
        values.put(SynthesisVideoEntity.COLUMN_FK_LOCATION, idLiege);
        values.put(SynthesisVideoEntity.COLUMN_URL_VIDEO, "https://www.youtube.com/watch?v=jDWPzAVRDjY");
        values.put(SynthesisVideoEntity.COLUMN_TEXT,"A la base, Liège était une principauté. Comme Charleroi, Liège est une ville industrielle. L'industrie la plus présente est la sidérurgie. La ville de Liège est estudiantine et festive.");
        database.insert(SynthesisVideoEntity.TABLE, null, values);

        values.clear();
        values.put(SynthesisImageEntity.COLUMN_FK_LOCATION, idEupen);
        values.put(SynthesisImageEntity.COLUMN_URL_IMAGE, "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c7/Eupen_Li%C3%A8ge_Belgium_Map.png/280px-Eupen_Li%C3%A8ge_Belgium_Map.png");
        values.put(SynthesisImageEntity.COLUMN_TEXT,"A Eupen, les gens parlent principalement en allemand. Mais certains parlent en français. Eupen se situe à la frontière allemande. ");
        database.insert(SynthesisImageEntity.TABLE, null, values);

        values.clear();
        values.put(SynthesisVideoEntity.COLUMN_FK_LOCATION, idNamur);
        values.put(SynthesisVideoEntity.COLUMN_URL_VIDEO, "https://www.youtube.com/watch?v=zzpDGSBItvw");
        values.put(SynthesisVideoEntity.COLUMN_TEXT,"Namur est la capitale de la région wallone. ");
        database.insert(SynthesisVideoEntity.TABLE, null, values);


        //TODO : C'est une vidéo en MP4, vérifier si Picasso sait pas handle ça plutôt qu'une webview
        values.clear();
        values.put(SynthesisWebViewEntity.COLUMN_FK_LOCATION, idBastogne);
        values.put(SynthesisWebViewEntity.COLUMN_URL, "http://www.bastognewarmuseum.be/video/video_page_accueil.mp4");
        values.put(SynthesisWebViewEntity.COLUMN_TEXT,"Bastogne est connue suite à la bataille des Ardennes lors de la deuxième guerre mondiale. La ville de Bastogne contient de nombreux monuments qui commémore cette période. La ville est visitée par beaucoup d'Américains qui étaient nos alliés de combat.");
        database.insert(SynthesisWebViewEntity.TABLE, null, values);


        //MUSICACTIVITY

        //Quizz
        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "La culture musicale Africaine");
        int idQuestionnaireAfrique = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "La culture musicale Asiatique");
        int idQuestionnaireAsie = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "La culture musicale Américaine");
        int idQuestionnaireAmerique = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "La culture musicale Européenne");
        int idQuestionnaireEurope = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionnaireEntity.COLUMN_STATEMENT, "La culture musicale de l'Océanie");
        int idQuestionnaireOceanie = (int) database.insert(QuestionnaireEntity.TABLE, null, values);

        //Question
        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "Pourquoi les africains dansent-ils ?");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireAfrique);
        int idQuestionAfrique1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "Quel est l'instrument d'origine africaine ?");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireAfrique);
        int idQuestionAfrique2 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "L'Afrique se trouve :");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireAfrique);
        int idQuestionAfrique3 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "La musique américaine est :");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireAmerique);
        int idQuestionAmerique1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "En amérique, on peut trouver :");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireAmerique);
        int idQuestionAmerique2 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "Le sirop d'érable vient :");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireAmerique);
        int idQuestionAmerique3 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "Combien de religions sont présentes en Asie ?");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireAsie);
        int idQuestionAsie1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "La musique est liée :");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireAsie);
        int idQuestionAsie2 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "L'instrument que l'on a découvert est :");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireAsie);
        int idQuestionAsie3 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "Le style de musique dépend :");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireEurope);
        int idQuestionEurope1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "La Belgique se trouve :");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireEurope);
        int idQuestionEurope2 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "En Europe on trouve :");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireEurope);
        int idQuestionEurope3 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "Une des traditions en océanie s'appelle :");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireOceanie);
        int idQuestionOceanie1 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "L'océanie est le continent :");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireOceanie);
        int idQuestionOceanie2 = (int) database.insert(QuestionEntity.TABLE, null, values);

        values.clear();
        values.put(QuestionEntity.COLUMN_STATEMENT, "L'aliment le plus connu est :");
        values.put(QuestionEntity.COLUMN_TYPE, "1");
        values.put(QuestionEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireOceanie);
        int idQuestionOceanie3 = (int) database.insert(QuestionEntity.TABLE, null, values);

        //Answers
        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Pour se réveiller le matin");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAfrique1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Avant d'aller au toilette");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAfrique1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Fêter des éléments de la vie de tous les jours");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAfrique1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le djembé");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAfrique2);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Les marraccas");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAfrique2);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le bâton de pluie");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAfrique2);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Au dessus de la Belgique");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAfrique3);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "En dessous de la Belgique");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAfrique3);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "À côté de la Belgique");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAfrique3);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Diversifiée");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAmerique1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Simple");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAmerique1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Triste");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAmerique1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le fruit du dragon");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAmerique2);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "La fraise");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAmerique2);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "La banane");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAmerique2);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Du Canada");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAmerique3);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "De la Belgique");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAmerique3);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "De l'Autralie");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAmerique3);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "1");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAsie1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "2");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAsie1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "3");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAsie1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Au passé");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAsie2);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Au présent");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAsie2);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Au futur");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAsie2);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le bonjo");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAsie3);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le benju");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAsie3);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "La guitare");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionAsie3);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Des pays");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionEurope1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Des habitants");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionEurope1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "De la pluie");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionEurope1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "En Europe");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionEurope2);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "En Asie");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionEurope2);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "En Amérique");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionEurope2);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Des pâtes");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionEurope3);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le fruit du dragon");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionEurope3);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le litchi");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionEurope3);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le haka");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionOceanie1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le maka");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionOceanie1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Le ika");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionOceanie1);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "De la Belgique");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionOceanie2);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "De l'Australie");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionOceanie2);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "De la France");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionOceanie2);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Pavlova");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "1");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionOceanie3);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Patata");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionOceanie3);
        database.insert(AnswerEntity.TABLE, null, values);

        values.clear();
        values.put(AnswerEntity.COLUMN_STATEMENT, "Polisa");
        values.put(AnswerEntity.COLUMN_IS_TRUE, "0");
        values.put(AnswerEntity.COLUMN_FK_QUESTION, idQuestionOceanie3);
        database.insert(AnswerEntity.TABLE, null, values);


        //region
        values.clear();
        values.put(ActivityEntity.COLUMN_NAME, "La musique autour du monde");
        values.put(ActivityEntity.COLUMN_FK_QUESTIONNAIRE, "");
        values.put(ActivityEntity.COLUMN_ACTIVITY_CANONICAL_CLASS_NAME, MapMusicalActivity.class.getName());
        values.put(ActivityEntity.COLUMN_CLASS_CANONICAL_CLASS_NAME, ActivityMapBase.class.getName());
        uriIcon = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/ic_music_icon");
        values.put(ActivityEntity.COLUMN_URI_ICON,uriIcon.toString());
        activityId = (int) database.insert(ActivityEntity.TABLE, null, values);

        values.clear();
        values.put(ActivityMapBaseEntity.COLUMN_FK_ACTIVITY, activityId);
        values.put(ActivityMapBaseEntity.COLUMN_LATITUDE_CENTER, 50.8468);
        values.put(ActivityMapBaseEntity.COLUMN_LONGITUDE_CENTER, 4.3775);
        values.put(ActivityMapBaseEntity.COLUMN_ZOOM, 0.01);
        idActivityMap = (int) database.insert(ActivityMapBaseEntity.TABLE, null, values);

        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Asie");
        values.put(LocationEntity.COLUMN_LATITUDE, 39.898369);
        values.put(LocationEntity.COLUMN_LONGITUDE, 97.346919);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE,idQuestionnaireAsie);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, MusicalActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        int idAsie = (int) database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Afrique");
        values.put(LocationEntity.COLUMN_LATITUDE, 3.711932);
        values.put(LocationEntity.COLUMN_LONGITUDE, 21.880014);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE,idQuestionnaireAfrique);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, MusicalActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        int idAfrique = (int) database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Amérique");
        values.put(LocationEntity.COLUMN_LATITUDE, -16.246897);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE,idQuestionnaireAmerique);
        values.put(LocationEntity.COLUMN_LONGITUDE, -60.228244);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, MusicalActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        int idAmerique = (int) database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Europe");
        values.put(LocationEntity.COLUMN_LATITUDE, 50.151015);
        values.put(LocationEntity.COLUMN_LONGITUDE, 7.265708);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE,idQuestionnaireEurope);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, MusicalActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        int idEurope = (int) database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(LocationEntity.COLUMN_TITLE, "Océanie");
        values.put(LocationEntity.COLUMN_LATITUDE, -23.925622);
        values.put(LocationEntity.COLUMN_LONGITUDE, 138.992828);
        values.put(LocationEntity.COLUMN_FK_QUESTIONNAIRE,idQuestionnaireOceanie);
        values.put(LocationEntity.COLUMN_ACTIVITY_CANONICAL_NAME, MusicalActivity.class.getName());
        values.put(LocationEntity.COLUMN_FK_ACTIVITYMAPBASE, idActivityMap);
        int idOceanie = (int) database.insert(LocationEntity.TABLE, null, values);

        values.clear();
        values.put(CategoryToActivityEntity.COLUMN_FK_ACTIVITY, activityId);
        values.put(CategoryToActivityEntity.COLUMN_FK_CATEGORY, idCategoryInferior);
        database.insert(CategoryToActivityEntity.TABLE, null, values);

        //Instruments
        values.clear();
        values.put(InstrumentEntity.COLUMN_FK_LOCATION, idAfrique);
        values.put(InstrumentEntity.COLUMN_NAME, "Djembe");
        values.put(InstrumentEntity.COLUMN_DESCRIPTION, "Les africains dansent pour marquer des événements de la vie quotidienne. Leur rythme est joyeux et entrainant.");
        uriIcon = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/ic_instrument_africa");
        values.put(InstrumentEntity.COLUMN_IMAGE_PATH, uriIcon.toString());
        values.put(InstrumentEntity.COLUMN_SAMPLE_FILE_NAME, "djembe_sample");
        values.put(InstrumentEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireAsie);
        database.insert(InstrumentEntity.TABLE, null, values);

        values.clear();
        values.put(InstrumentEntity.COLUMN_FK_LOCATION, idAsie);
        values.put(InstrumentEntity.COLUMN_NAME, "Benju");
        values.put(InstrumentEntity.COLUMN_DESCRIPTION, "Elle est caractérisée par son ancienneté et sa richesse. Les asiatiques s'inspirent du passé et des traditions anciennes. La musique peut également être liée à une des trois religions : boudiste, indou ou musulman.");
        uriIcon = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/ic_instrument_asia");
        values.put(InstrumentEntity.COLUMN_IMAGE_PATH, uriIcon.toString());
        values.put(InstrumentEntity.COLUMN_SAMPLE_FILE_NAME, "benju_sample");
        values.put(InstrumentEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireAfrique);
        database.insert(InstrumentEntity.TABLE, null, values);

        values.clear();
        values.put(InstrumentEntity.COLUMN_FK_LOCATION, idAmerique);
        values.put(InstrumentEntity.COLUMN_NAME, "Bâton de pluie");
        values.put(InstrumentEntity.COLUMN_DESCRIPTION, "La musique américaine est très variée. Elle comporte les touches de beaucoup de pays ( à cause de l'immigration). On peut trouver du jazz, de la salsa, du rap, du RNB.");
        uriIcon = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/ic_instrument_america");
        values.put(InstrumentEntity.COLUMN_IMAGE_PATH, uriIcon.toString());
        values.put(InstrumentEntity.COLUMN_SAMPLE_FILE_NAME, "rain_stick_sample");
        values.put(InstrumentEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireAmerique);
        database.insert(InstrumentEntity.TABLE, null, values);

        values.clear();
        values.put(InstrumentEntity.COLUMN_FK_LOCATION, idEurope);
        values.put(InstrumentEntity.COLUMN_NAME, "Guitare");
        values.put(InstrumentEntity.COLUMN_DESCRIPTION, "La culture musicale de l'Europe est elle aussi très variée en raison de tout les pays qui composent le continent. On peut écouteer du rap, des chansons douces, de la pop, du rock n roll ou encore des chansons sans paroles. Certains grands chanteurs ont vécu en France, en Angleterre ou même en Belgique. La musique évolue d'année en année (et ce pour tous les continents!).");
        uriIcon = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/ic_instrument_europe");
        values.put(InstrumentEntity.COLUMN_IMAGE_PATH, uriIcon.toString());
        values.put(InstrumentEntity.COLUMN_SAMPLE_FILE_NAME, "guitar_sample");
        values.put(InstrumentEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireEurope);
        database.insert(InstrumentEntity.TABLE, null, values);

        values.clear();
        values.put(InstrumentEntity.COLUMN_FK_LOCATION, idOceanie);
        values.put(InstrumentEntity.COLUMN_NAME, "Guimbarde en bambou");
        values.put(InstrumentEntity.COLUMN_DESCRIPTION, "En Océanie, on parle souvent de musique aborigènes. Parfois, on peut même entendre des hakas (danses et chants océaniens). La musique d'océanie est une accompagnante, elle accompagne les cérémonies et les rituels. Elle est différente en fonction des clans dans les pays. ");
        uriIcon = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/ic_instrument_oceanie");
        values.put(InstrumentEntity.COLUMN_IMAGE_PATH, uriIcon.toString());
        values.put(InstrumentEntity.COLUMN_SAMPLE_FILE_NAME, "jaw_harp_sample");
        values.put(InstrumentEntity.COLUMN_FK_QUESTIONNAIRE, idQuestionnaireOceanie);
        database.insert(InstrumentEntity.TABLE, null, values);

        //region users
        int iUser;
        values.clear();

        //TO DELETE
        //Test User because it's too annoying to check every time the db
        values.put(UserEntity.COLUMN_FIRSTNAME,"Music");
        values.put(UserEntity.COLUMN_LASTNAME,"User");
        values.put(UserEntity.COLUMN_USERNAME,"MusicUser");
        values.put(UserEntity.COLUMN_GENDER, 1);
        values.put(UserEntity.COLUMN_FK_CLASS, 1);
        values.put(UserEntity.COLUMN_FK_CATEGORY,2);
        String sha256pwd = cryptographyService.hashPassword("Tigrou007");
        values.put(UserEntity.COLUMN_PASSWORDHASH,sha256pwd);
        database.insert(UserEntity.TABLE,null,values);

        String firstName = "Test";
        String lastName = "Test";
      
        for (iUser = 1; iUser < 61; iUser++) {
          values.clear();

            values.put(UserEntity.COLUMN_FIRSTNAME,firstName);
            values.put(UserEntity.COLUMN_LASTNAME,lastName);
            values.put(UserEntity.COLUMN_USERNAME,firstName+lastName);
            values.put(UserEntity.COLUMN_GENDER, 1);
            values.put(UserEntity.COLUMN_FK_CLASS, 1);
            values.put(UserEntity.COLUMN_FK_CATEGORY,iUser % 2 == 0 ? idCategoryInferior : idCategorySuperior);
            sha256pwd = cryptographyService.hashPassword("Tigrou007");
            values.put(UserEntity.COLUMN_PASSWORDHASH,sha256pwd);
            database.insert(UserEntity.TABLE,null,values);


            firstName = Fakeit.name().firstName();
            lastName = Fakeit.name().lastName();
        }
        //endregion

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
