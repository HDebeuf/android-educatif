package be.henallux.masi.pedagogique.utils;

import android.content.Context;

import java.util.ArrayList;

import be.henallux.masi.pedagogique.R;

/**
 * Created by Le Roi Arthur on 17-12-17.
 */

public class Constants {
    public static final String ACTIVITY_KEY = "Key_intent_map_activity";
    public static final String KEY_CATEGORY_USER = "Key_category_user";
    public static final String KEY_ID_USER = "Key_id_user";
    public static final String KEY_LOCATION_CLICKED = "Key_location_clicked";
    public static final String KEY_LOCATIONS_CHOSEN = "Key_locations_chosen";
    public static final String KEY_GROUP_CREATED = "Key_group_created";
    public static final String KEY_USERS_CHOSEN = "Key_user_chosen";
    public static final String KEY_CURRENT_USER = "Key_current_user";

    public static final int ACTIVITY_RESULT_SYNTHESIS = 0xa47e;
    public static final String RESULT_DATA_HAS_CHOOSEN_LOCATION = "Has_choosen_location";

    public static final String KEY_IS_IN_DELETE_MODE = "Is_in_delete_mode";
    public static final int REQUEST_IMAGE_CAPTURE = 0x984a;

    public enum Gender{
        MALE,
        FEMALE,
        OTHER
    }

    public static ArrayList<String> getGendersList(Context ctx){
        ArrayList<String> genders = new ArrayList<>();
        genders.add(ctx.getString(R.string.gender_male));
        genders.add(ctx.getString(R.string.gender_female));
        genders.add(ctx.getString(R.string.gender_other));
        return genders;
    }
}
