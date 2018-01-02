package be.henallux.masi.pedagogique.utils;

import android.content.Context;

/**
 * Created by hendrikdebeuf2 on 2/01/18.
 */

public interface IPermissionsHandler {

    boolean isStoragePermissionGranted(android.app.Activity activity,Context context);

    boolean isAudioRecordPermissionGranted(android.app.Activity activity,Context context);

    void requestPermissions (android.app.Activity activity);
}
