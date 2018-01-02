package be.henallux.masi.pedagogique.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by hendrikdebeuf2 on 2/01/18.
 */

public class PermissionsHandler implements IPermissionsHandler {

    private ArrayList<String> permissions = new ArrayList<String>();

    public  boolean isStoragePermissionGranted(android.app.Activity activity,Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d("storagePermission","Permission is granted");
                return true;
            } else {

                Log.d("storagePermission","Permission is revoked");
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.d("storagePermission","Permission is granted");
            return true;
        }
    }

    public  boolean isAudioRecordPermissionGranted(android.app.Activity activity,Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d("recordPermission","Permission is granted");
                return true;
            } else {

                Log.d("recordPermission","Permission is revoked");
                permissions.add(Manifest.permission.RECORD_AUDIO);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.d("recordPermission","Permission is granted");
            return true;
        }
    }

    public void requestPermissions (android.app.Activity activity){
        String[] permissionsString = new String[permissions.size()];
        permissionsString = permissions.toArray(permissionsString);
        Log.d("permission", String.valueOf(permissionsString));
        ActivityCompat.requestPermissions(activity, permissionsString, 1);
    }

}
