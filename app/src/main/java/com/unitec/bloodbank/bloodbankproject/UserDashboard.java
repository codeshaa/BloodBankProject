package com.unitec.bloodbank.bloodbankproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class UserDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        LinearLayout userProfile = (LinearLayout) findViewById(R.id.updateProfileLayout);
        userProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                profilePage();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              searchPage();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void searchPage(){
        Intent intent= new Intent(this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }

    public void profilePage(){
        Intent intent= new Intent(this, ProfileInfo.class);
        startActivity(intent);
        finish();
    }
}
