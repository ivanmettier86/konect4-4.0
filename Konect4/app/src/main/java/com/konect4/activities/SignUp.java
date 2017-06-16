package com.konect4.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.appyvet.rangebar.IRangeBarFormatter;
import com.appyvet.rangebar.RangeBar;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.konect4.R;
import com.konect4.utilities.AndroidMultiPartEntity;
import com.konect4.utilities.CustomHttpClient;
import com.konect4.utilities.CustomRequest;
import com.konect4.utilities.Functions;
import com.konect4.utilities.Globals;
import com.konect4.utilities.ImageFilePath;
import com.konect4.utilities.MyVolley;
import com.konect4.utilities.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import khandroid.ext.apache.http.HttpEntity;
import khandroid.ext.apache.http.HttpResponse;
import khandroid.ext.apache.http.client.ClientProtocolException;
import khandroid.ext.apache.http.client.methods.HttpPost;
import khandroid.ext.apache.http.entity.mime.content.FileBody;
import khandroid.ext.apache.http.entity.mime.content.StringBody;
import khandroid.ext.apache.http.params.BasicHttpParams;
import khandroid.ext.apache.http.params.HttpConnectionParams;
import khandroid.ext.apache.http.params.HttpParams;
import khandroid.ext.apache.http.util.EntityUtils;

/**
 * Created by Muhammad Zeeshan on 5/31/2017.
 */

public class SignUp extends Activity {

    Button sign_up;
    TextView register, gender, relation, orientation, looking_for, age;
    RangeBar distance_rangebar;
    ImageView back;
    EditText user_name, email, password, description, first_name, last_name;
    String age_day, age_month, age_year, selected_distance;
    private String mCurrentPhotoPath = "";
    long totalSize;
    private ImageView profile_image;
    private final int ACTIVITY_CHOOSE_PHOTO = 3;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap scaled;
    private File file_profile;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        user_name = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        description = (EditText) findViewById(R.id.description);
        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);

        register = (TextView) findViewById(R.id.register);
        gender = (TextView) findViewById(R.id.gender);
        relation = (TextView) findViewById(R.id.single_relation);
        orientation = (TextView) findViewById(R.id.orientation);
        looking_for = (TextView) findViewById(R.id.looking);
        age = (TextView) findViewById(R.id.age);
        distance_rangebar = (RangeBar) findViewById(R.id.distance_rangebar);
        profile_image = (ImageView)findViewById(R.id.profile_image);

        distance_rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                selected_distance=rightPinValue;
            }
        });

        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGenderDialog();
            }
        });

        relation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRelationDialog();
            }
        });

        looking_for.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLookingDialog();
            }
        });

        orientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOrientationDialog();
            }
        });
    }


    public void showGenderDialog() {
        final CharSequence[] items = {" Male ", " Female "};
        new AlertDialog.Builder(this)
                .setTitle("Gender")
                .setSingleChoiceItems(items, 0, null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        gender.setText(items[selectedPosition]);
                        // Do something useful withe the position of the selected radio button
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        // Do something useful withe the position of the selected radio button
                    }
                })
                .show();
    }


    public void showRelationDialog() {
        final CharSequence[] items = {" Single ", " Taken "};
        new AlertDialog.Builder(this)
                .setTitle("Single/Relation")
                .setSingleChoiceItems(items, 0, null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        relation.setText(items[selectedPosition]);
                        // Do something useful withe the position of the selected radio button
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        // Do something useful withe the position of the selected radio button
                    }
                })
                .show();
    }


    public void showLookingDialog() {
        final CharSequence[] items = {" Dating ", " Meeting new friends ", " Stayin konected ", " Business networking "};
        new AlertDialog.Builder(this)
                .setTitle("Looking for")
                .setSingleChoiceItems(items, 0, null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        looking_for.setText(items[selectedPosition]);
                        // Do something useful withe the position of the selected radio button
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        // Do something useful withe the position of the selected radio button
                    }
                })
                .show();
    }


    public void showOrientationDialog() {
        final CharSequence[] items = {" Straight ", " Gay ", " Bisexual ", " Do not identify "};
        new AlertDialog.Builder(this)
                .setTitle("Orientation")
                .setSingleChoiceItems(items, 0, null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        orientation.setText(items[selectedPosition]);
                        // Do something useful withe the position of the selected radio button
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        // Do something useful withe the position of the selected radio button
                    }
                })
                .show();
    }


    /*
    Date Picker Dialog
     */
    public void setDateTimeField(View v) {

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                age_day=String.valueOf(dayOfMonth);
                age_month=String.valueOf(monthOfYear);
                age_year=String.valueOf(year);
                age.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }


    /*
    Photo Taker
     */
    public void changeProfile(View v) {

        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    takePhoto();
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    // Start the Intent
                    startActivityForResult(Intent.createChooser(galleryIntent, "Choose a photo"), ACTIVITY_CHOOSE_PHOTO);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /*
    Photo Selection/Taken
     */
    public void takePhoto() {
        PackageManager pm = this.getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(pm) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();

                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        } else {
            Toast.makeText(this, "No camera", Toast.LENGTH_LONG).show();
        }
    }

    /*
    Create Image As File
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        String storageD = Environment.getExternalStorageDirectory().toString() + "/Pictures";
        File photo_folder = new File(storageD);

        if (!photo_folder.exists()) {
            photo_folder.mkdirs();
        }
        File f = new File(storageD + File.separator + imageFileName + ".jpg");
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = f.getAbsolutePath();
        return f;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == ACTIVITY_CHOOSE_PHOTO) {
            if (data == null) {
                return;
            }
            Uri selectedImageUri = data.getData();
            mCurrentPhotoPath = ImageFilePath.getPath(getApplicationContext(), selectedImageUri);
            if (mCurrentPhotoPath == null || (mCurrentPhotoPath != null && mCurrentPhotoPath=="")) {
                Toast.makeText(getApplicationContext(), "Some error occured with your image, please choose another.", Toast.LENGTH_SHORT).show();
                return;
            }

            Bitmap d = BitmapFactory.decodeFile(mCurrentPhotoPath);
            int nh = (int) (d.getHeight() * (512.0 / d.getWidth()));
            scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
            file_profile = new File(mCurrentPhotoPath);
            scaled=d;
            profile_image.setVisibility(View.VISIBLE);
            profile_image.setImageBitmap(scaled);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (mCurrentPhotoPath == null) {
                return;
            }

            try {
                Bitmap d = BitmapFactory.decodeFile(mCurrentPhotoPath);
                int nh = (int) (d.getHeight() * (512.0 / d.getWidth()));
                scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
                file_profile = new File(mCurrentPhotoPath);
                scaled=d;
                profile_image.setVisibility(View.VISIBLE);
                profile_image.setImageBitmap(scaled);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /*
    Save Listener
     */
    public  void saveProfile(View view){
        String user_email = email.getText().toString();
        String user_first_name = first_name.getText().toString();
        String user_last_name = last_name.getText().toString();

        boolean checker = false;
        if (user_name.equals("")) {
            user_name.setError("User name missing");
            checker = true;
        }
        if (user_email != null && user_email.equals("")) {
            email.setError(getResources().getString(R.string.email_error_msg));
            checker = true;
        }
        if (password.getText().toString().equals("")) {
            checker = true;
            password.setError(getResources().getString(R.string.password_error_msg));
        }
        if (first_name.getText().toString().equals("")) {
            checker = true;
            first_name.setError(getResources().getString(R.string.fname_error_msg));
        }
        if (last_name.getText().toString().equals("")) {
            checker = true;
            last_name.setError(getResources().getString(R.string.lname_error_msg));
        }
        if (age.getText().toString().equals("")) {
            checker = true;
            age.setError("Please select age");
        }
        if (gender.getText().toString().equals("")) {
            checker = true;
            gender.setError("Please select gender");
        }
        if (orientation.getText().toString().equals("")) {
            checker = true;
            orientation.setError("Please select orientation");
        }
        if (description.getText().toString().equals("")) {
            checker = true;
            description.setError("Please add description");
        }
        if (looking_for.getText().toString().equals("")) {
            checker = true;
            looking_for.setError("Please select looking for");
        }
        if (relation.getText().toString().equals("")) {
            checker = true;
            relation.setError("Please select relationship");
        }
        if (checker)
            return;

        Intent intent = new Intent(getApplicationContext(), Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

