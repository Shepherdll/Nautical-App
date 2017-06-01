package com.swampass.nauticalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.swampass.nauticalapp.R;


public class ConnectionsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_connections);

        toolbar = (Toolbar) findViewById(R.id.tToolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);


        ImageView profileActivity = (ImageView) toolbar.findViewById(R.id.action_profile);
        ImageView homeActivity = (ImageView) toolbar.findViewById(R.id.action_home);

        profileActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent profileActivity = new Intent(ConnectionsActivity.this, ProfileActivity.class);
                startActivity(profileActivity);
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }

        });
        homeActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent home = new Intent(ConnectionsActivity.this, HomeActivity.class);
                startActivity(home);
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }

        });
    }
}
