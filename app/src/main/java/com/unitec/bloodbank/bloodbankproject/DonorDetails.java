package com.unitec.bloodbank.bloodbankproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.RequestBean;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.UserBean;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.NewRequestService;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.UserDataHelper;

public class DonorDetails extends AppCompatActivity {

    public static final String USER_ADDRESS = "com.unitec.bloodbank.address";
    public static final String USER_BLOOD = "com.unitec.bloodbank.blood";
    public static final String USER_NAME = "com.unitec.bloodbank.name";

    public static UserBean donorUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_details);


        TextView nameText = (TextView) findViewById(R.id.name_text);
        TextView bloodText = (TextView) findViewById(R.id.text_blood);
        TextView addressText = (TextView) findViewById(R.id.text_address);
        EditText userText = (EditText) findViewById(R.id.userEdittext);
        addressText.setMovementMethod(new ScrollingMovementMethod());
        String address = null;
        String name = null;
        String blood = null;
        ImageButton iButtonLocation = (ImageButton) findViewById(R.id.imageButtonLocation);
        ImageButton iButtonRequested = (ImageButton) findViewById(R.id.imageButtonRequest);
        LinearLayout requestLayout = (LinearLayout) findViewById(R.id.layout_request);

        Intent intent = getIntent();
        int message = intent.getIntExtra(HomePageActivity.USER_ID, 0);

        for(UserBean userBean: UserDataHelper.userBeans){

            if (userBean.getUserId() == message){
                donorUser= userBean;
                name = userBean.getGivenName() + " " + userBean.getSurname();
                nameText.setText(name);
                bloodText.setText(userBean.getBloodGroup());
                blood = userBean.getBloodGroup();
                addressText.setText(userBean.getAddress());
                address = userBean.getAddress();
                break;

            }
        }

        final String finalAddress = address;
        final String finalName = name;
        final String finalBlood = blood;
        iButtonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DonorDetails.this, MapsActivity.class );
                intent.putExtra(USER_ADDRESS, finalAddress);
                intent.putExtra(USER_NAME, finalName);
                intent.putExtra(USER_BLOOD, finalBlood);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        LinearLayout requestBlood= (LinearLayout) findViewById(R.id.layout_request);
        requestBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBean newRequest= new RequestBean();
                newRequest.setDonor(donorUser);
                newRequest.setRequester(LoginActivity.homeUser);

                EditText userMessage= (EditText)findViewById(R.id.userEdittext);
                newRequest.setRequesterMessage(userMessage.getText().toString());
                newRequest.setStatus(0);

                new NewRequestService().execute(newRequest);

                Toast.makeText(DonorDetails.this, "Your Request has been sent to the user.Please wait for approval", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DonorDetails.this, UserDashboard.class);
                startActivity(intent);


            }
        });

    }


    // Registering transition when device back button pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    // Registering transition when menu back option selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id) {

            // up button transition handling
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
