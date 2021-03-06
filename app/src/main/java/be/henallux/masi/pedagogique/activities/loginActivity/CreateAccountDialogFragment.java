package be.henallux.masi.pedagogique.activities.loginActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.historyActivity.ConfirmLocationChosenDialogFragment;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.dao.interfaces.ICategoryRepository;
import be.henallux.masi.pedagogique.dao.interfaces.IClassRepository;
import be.henallux.masi.pedagogique.dao.interfaces.IUserRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteCategoryRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteClassRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLiteUserRepository;
import be.henallux.masi.pedagogique.model.Category;
import be.henallux.masi.pedagogique.model.Class;
import be.henallux.masi.pedagogique.model.User;
import be.henallux.masi.pedagogique.utils.Constants;
import be.henallux.masi.pedagogique.utils.IPermissionsHandler;
import be.henallux.masi.pedagogique.utils.PermissionsHandler;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Le Roi Arthur on 04-01-18.
 */

public class CreateAccountDialogFragment extends DialogFragment implements Validator.ValidationListener {

    private ImageButton addAvatar;
    private ImageView thumbNail;
    private IUserRepository userRepository = SQLiteUserRepository.getInstance(this.getActivity());
    private IClassRepository classRepository = SQLiteClassRepository.getInstance(this.getActivity());
    private ICategoryRepository categoryRepository = SQLiteCategoryRepository.getInstance(this.getActivity());
    private IPermissionsHandler permissionHandler = new PermissionsHandler();

    @NotEmpty(messageResId = R.string.error_field_required)
    EditText firstNameEditText;
    @NotEmpty(messageResId = R.string.error_field_required)
    EditText lastNameEditText;
    @NotEmpty(messageResId = R.string.error_field_required)
    EditText passwordEditText;

    private CreateAccountListener listener;
    private Validator validator;
    private Spinner spinnerGender;
    private Spinner spinnerClass;
    private Spinner spinnerCategory;
    private Uri avatarUri;


    public static CreateAccountDialogFragment newInstance() {
        CreateAccountDialogFragment f = new CreateAccountDialogFragment();
        return f;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this.getActivity(),R.color.colorPrimary));
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this.getActivity(),R.color.colorPrimary));
    }

    @Override
    public void onResume() {
        super.onResume();
        Button okButton = ((AlertDialog)getDialog()).getButton(AlertDialog.BUTTON_POSITIVE);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.create_account_dialog_layout, null);
        listener = (CreateAccountListener)getActivity();
        validator = new Validator(this);
        validator.setValidationListener(this);

        builder.setView(view)
                .setPositiveButton(R.string.map_activity_dialog_finish_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {}
                })
                .setNegativeButton(R.string.map_activity_dialog_finish_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {}
                });

        firstNameEditText = view.findViewById(R.id.firstNameEditText);
        lastNameEditText = view.findViewById(R.id.lastNameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        thumbNail = view.findViewById(R.id.thumbnail);
        addAvatar = view.findViewById(R.id.imageButtonAddPicture);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        spinnerClass = view.findViewById(R.id.spinnerClass);
        spinnerGender = view.findViewById(R.id.spinnerGender);

        populateSpinners();

        addAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    if(!permissionHandler.isStoragePermissionGranted(getActivity(),getActivity()))
                        permissionHandler.requestPermissions(CreateAccountDialogFragment.this.getActivity());
                    else
                        startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        return builder.create();
    }

    private void populateSpinners() {
        ArrayAdapter spinnerClassAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item,classRepository.getAllClasses());
        spinnerClass.setAdapter(spinnerClassAdapter);

        ArrayAdapter spinnerGenderAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item,Constants.getGendersList(this.getActivity()));
        spinnerGender.setAdapter(spinnerGenderAdapter);

        ArrayAdapter spinnerCategoryAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item,categoryRepository.getAllCategories());
        spinnerCategory.setAdapter(spinnerCategoryAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            savePicture();

            thumbNail.setVisibility(View.VISIBLE);
            Picasso.with(getActivity())
                    .load(getImageUri(imageBitmap))
                    .into(thumbNail);
        }
    }

    private void savePicture() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        avatarUri = Uri.fromFile(image);
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onValidationSucceeded() {
        listener.onConfirm();
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        Constants.Gender gender = Constants.Gender.values()[spinnerGender.getSelectedItemPosition()];
        Class userClass = (Class)spinnerClass.getSelectedItem();
        Category userCategory = (Category)spinnerCategory.getSelectedItem();

        User user = User.prepareUserForInsert(firstName,lastName,gender.ordinal(),avatarUri,userClass,userCategory);
        userRepository.insertUser(user,password);
        this.dismiss();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }
        }
    }


    public interface CreateAccountListener{
        void onConfirm();
        void onDismiss();
    }
}