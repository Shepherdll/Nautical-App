package com.swampass.nauticalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileActivity extends AppCompatActivity {



    private ProgressDialog  mProgress;
    private CircleImageView mProfilePic;
    private Button mSubmitBtn;
    private static final int GALLERY_REQUEST = 1;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private Uri mImageUri = null;
    private DatabaseReference mDatabaseUsers;
    private StorageReference mStorageImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.tToolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);


        mAuth = FirebaseAuth.getInstance();

        mProfilePic = (CircleImageView) findViewById(R.id.profileimagebutton2);
        mSubmitBtn = (Button) findViewById(R.id.setupSubmit);
        mProgress = new ProgressDialog(this);

        mStorageImage = FirebaseStorage.getInstance().getReference().child("Profile_images");
        mAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        ImageView homeActivity = (ImageView) toolbar.findViewById(R.id.action_home);
        ImageView cnectActivity = (ImageView) toolbar.findViewById(R.id.action_msg);
        Button logout = (Button) findViewById(R.id.logout_btn2);

        mProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity( new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }

        });

        homeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileActivity = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(profileActivity);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

        });
        cnectActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent knct = new Intent(ProfileActivity.this, ConnectionsActivity.class);
                startActivity(knct);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

        });
    }





    private void startSetupAccount() {

        FirebaseUser user = mAuth.getCurrentUser();



        if (mImageUri != null) {

            //For acutal end of setup
            mProgress.setMessage("Finishing Setup...");
            mProgress.show();
            // ends here
            StorageReference filepath = mStorageImage.child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final String user_id = mAuth.getCurrentUser().getUid();
                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();



                    mDatabaseUsers.child(user_id).child("image").setValue(downloadUrl.toString());

                }
            });


        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);


        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();
                mProfilePic.setImageURI(mImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }



        /*mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());

        if(mAuth.getCurrentUser() != null) {

            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    ImageUpload img = dataSnapshot.getValue(ImageUpload.class);
                    String dick = img.getUrl();

                    Picasso.with(getApplicationContext()).load(img.getUrl()).into(profPic);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });
        }*/







    }


