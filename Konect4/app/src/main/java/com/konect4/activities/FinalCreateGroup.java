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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.konect4.R;
import com.konect4.adapter.ActiveFriendsAdapter;
import com.konect4.adapter.FriendsAddAdapter;
import com.konect4.model.Friends;
import com.konect4.utilities.ImageFilePath;
import com.konect4.utilities.MyRecyclerView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Muhammad Zeeshan on 6/5/2017.
 */

public class FinalCreateGroup extends Activity {

    Button create_group;
    MyRecyclerView list_friends;
    ActiveFriendsAdapter adapter;
    ArrayList<Friends> friends_data;

    MyRecyclerView list_users;
    ActiveFriendsAdapter adapter_users;
    ArrayList<Friends> users_data;

    private ImageView profile_image;
    private final int ACTIVITY_CHOOSE_PHOTO = 3;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath = "", TAG = "UploadGroupPhoto";
    private Bitmap scaled;
    private File file_profile;
    Button active_friends, start_swiping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_final);

        create_group = (Button) findViewById(R.id.create_group);
        active_friends = (Button)findViewById(R.id.active_friends);
        start_swiping = (Button)findViewById(R.id.start_swiping);

        list_friends = (MyRecyclerView)findViewById(R.id.list_friends);
        list_users = (MyRecyclerView)findViewById(R.id.list_users);

        LinearLayoutManager llm = new LinearLayoutManager(FinalCreateGroup.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list_friends.setLayoutManager(llm);

        LinearLayoutManager llm_users = new LinearLayoutManager(FinalCreateGroup.this);
        llm_users.setOrientation(LinearLayoutManager.VERTICAL);
        list_users.setLayoutManager(llm_users);

        friends_data = new ArrayList<Friends>();
        users_data = new ArrayList<Friends>();

        create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GroupDetail.class);
                startActivity(intent);
            }
        });

        active_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.putExtra("finish", true); // if you are checking for this in your other Activities
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        start_swiping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GroupDetail.class);
                startActivity(intent);
            }
        });

        populateList();
        populateUsersList();
    }


    public void populateList(){
        for (int i=0; i<4; i++){

            Friends friends;
            if (i==0){
                friends = new Friends(""+i,"My Name Goes Here", "image", "", false, true);
            }
            else{
                friends = new Friends(""+i,"Selected User "+i, "image", "", false, true);
            }
            friends_data.add(friends);
        }
        adapter = new ActiveFriendsAdapter(FinalCreateGroup.this, friends_data, false);
        list_friends.setAdapter(adapter);
    }


    public void populateUsersList(){
        for (int i=0; i<5; i++){
            Friends friends = new Friends(""+i,"Selected User "+i, "image", "", false, true);
            users_data.add(friends);
        }
        adapter_users = new ActiveFriendsAdapter(FinalCreateGroup.this, users_data, false);
        list_users.setAdapter(adapter_users);
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

        //Toast.makeText()
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(FinalCreateGroup.this);
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
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
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
}