package com.swampass.nauticalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;




public class GetToKnowActivity extends AppCompatActivity {

    private ProgressDialog mProgress;

//NEEDS CODE THAT IMPLEMENTS DATA TO DATABASE... WORK IN PROGRESS... - TAYLOR

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_to_know);

        Button btnFinish = (Button) findViewById(R.id.btn_finishSetup);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(GetToKnowActivity.this, HomeActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });


    }
}
