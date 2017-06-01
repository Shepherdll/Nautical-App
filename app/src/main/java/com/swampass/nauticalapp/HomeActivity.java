package com.swampass.nauticalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.tToolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    finish();


                }
            }
        };


        ImageView profileActivity = (ImageView) toolbar.findViewById(R.id.action_profile);
        ImageView cnectActivity = (ImageView) toolbar.findViewById(R.id.action_msg);
        Button logout = (Button) findViewById(R.id.logout_btn);





        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity( new Intent(HomeActivity.this, LoginActivity.class));
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }

        });

        profileActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent profileActivity = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(profileActivity);
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }

        });
        cnectActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent knct = new Intent(HomeActivity.this, ConnectionsActivity.class);
                startActivity(knct);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

        });


    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }



    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

}
