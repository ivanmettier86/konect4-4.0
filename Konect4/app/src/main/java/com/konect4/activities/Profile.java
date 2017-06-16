package com.konect4.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.konect4.R;
import com.konect4.adapter.ActiveFriendsAdapter;
import com.konect4.model.Friends;
import com.konect4.model.MessageData;
import com.konect4.utilities.CustomRequest;
import com.konect4.utilities.Functions;
import com.konect4.utilities.Globals;
import com.konect4.utilities.ImageFilePath;
import com.konect4.utilities.MyRecyclerView;
import com.konect4.utilities.MyVolley;
import com.konect4.utilities.SessionManager;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Muhammad Zeeshan on 6/2/2017.
 */

public class Profile extends Activity {

    private CircleImageView profile_image;
    private final int ACTIVITY_CHOOSE_PHOTO = 3;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath = "", TAG = "UploadGroupPhoto";
    private Bitmap scaled;
    private File file_profile;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /*
    Photo Taker
     */
    public void changeProfile(View v) {
        boolean check_permission=isStoragePermissionGranted();
        if (check_permission){

        }
        else{
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
//            Toast.makeText(getApplicationContext(), "Some error occured with your image, please choose another.", Toast.LENGTH_SHORT).show();
            return;
        }

        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
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


            Log.d("234234234234", "***"+mCurrentPhotoPath+"***");

            Bitmap d = BitmapFactory.decodeFile(mCurrentPhotoPath);
            int nh = (int) (d.getHeight() * (512.0 / d.getWidth()));
            scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
            file_profile = new File(mCurrentPhotoPath);
            scaled=d;
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
                profile_image.setImageBitmap(scaled);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {
                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }


    public void makeLogout(View view){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Logout")
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Logout();
                        Intent intent = new Intent(getApplicationContext(), Welcome.class);
                        intent.putExtra("finish", true); // if you are checking for this in your other Activities
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    /*
    LOGOUT API
    */
    public void Logout() {
        Functions.showDialog("Logout...", Profile.this);
        HashMap<String, String> param = new HashMap<String, String>();

        SessionManager session=new SessionManager(Profile.this);
        String device_id = session.getKey("device_id");
        String user_id = session.getKey("user_id");
        String token = session.getKey("access_token");
        param.put("device_id", device_id);
        param.put("user_id", user_id);
        param.put("access_token", token);

        Log.d("CHECKING FOR PARAMS ", " " + param.toString());

        final RequestQueue queue = MyVolley.getRequestQueue();
        CustomRequest crq = new CustomRequest(com.android.volley.Request.Method.POST, Globals.LOGOUT, param, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String error_msg = "";
                String http_status;

                Functions.hideDialog();

                Log.d("checking for the LOGOUT response", " " + response);

                try {
                    error_msg = response.optString("error");
                    http_status = response.optString("http_status");

                    if (http_status.equals("200")) {
                        Intent intent = new Intent(getApplicationContext(), Welcome.class);
                        intent.putExtra("finish", true); // if you are checking for this in your other Activities
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        if (error_msg.equals("")) {
                            Functions.showToast(Profile.this, getResources().getString(R.string.some_error));
                        } else {
                            Functions.showToast(Profile.this, error_msg);
                        }
                    }
                } catch (Exception e) {
                    Functions.showToast(Profile.this, getResources().getString(R.string.some_error));
                    Functions.hideDialog();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Functions.hideDialog();
                volleyError.printStackTrace();
                if (volleyError.networkResponse == null) {
                    Functions.showToast(Profile.this, getResources().getString(R.string.slow_internet));
                } else {
                    Functions.showToast(Profile.this, getResources().getString(R.string.some_error));
                }
            }
        }, "LOGOUT");
        crq.setShouldCache(false);
        queue.add(crq);
    }
}