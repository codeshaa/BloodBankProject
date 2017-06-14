package com.unitec.bloodbank.bloodbankproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.FetchRequestService;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.UserDataHelper;

public class UserDashboard extends AppCompatActivity {

    public ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i("Req data fetch begn","");

        progress = ProgressDialog.show(this, "Laoding",
                "Fetching request data", true,false);

        new Thread(new Runnable() {
            @Override
            public void run()
            {
                new FetchRequestService().execute(LoginActivity.homeUser);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        progress.dismiss();
                    }
                });
            }
        }).start();
/*        try {
            Thread.sleep(10000);
        }
        catch (Exception e){
            e.printStackTrace();
        }*/

        LinearLayout myRequests = (LinearLayout) findViewById(R.id.myReqLayout);
        myRequests.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                myRequestPage();
            }
        });


        LinearLayout userReqLayout = (LinearLayout) findViewById(R.id.userReqLayout);
        userReqLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                userRequestPage();
            }
        });

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

    public void myRequestPage(){
        Intent intent= new Intent(this, MyRequestsActivity.class);
        startActivity(intent);
    }
    public void userRequestPage(){
        Intent intent= new Intent(this, RequestList.class);
        startActivity(intent);
    }

    public void searchPage(){
        Intent intent= new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    public void profilePage(){
        Intent intent= new Intent(this, ProfileInfo.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();

        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
