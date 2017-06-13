package com.unitec.bloodbank.bloodbankproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.RequestBean;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.UserBean;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.RequestListViewAdapter;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.UpdateRequestService;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.UserDataHelper;

public class DonorDetailsDeclined extends AppCompatActivity {

    public static final String USER_ADDRESS = "com.unitec.bloodbank.address";
    public static final String USER_BLOOD = "com.unitec.bloodbank.blood";
    public static final String USER_NAME = "com.unitec.bloodbank.name";
    protected static RequestBean requestBean;
    protected static UserBean donorBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_details_declined);

        TextView nameText = (TextView) findViewById(R.id.name_text);
        TextView bloodText = (TextView) findViewById(R.id.text_blood);
        String name = null;

        Intent intent = getIntent();
        int requestId = intent.getIntExtra(RequestListViewAdapter.REQUEST_ID, 0);
        for(RequestBean requestBean:UserDataHelper.userRequests){
            if(requestBean.getRequestId()==requestId){
                this.requestBean=requestBean;
                this.donorBean=requestBean.getDonor();
                break;
            }
        }

            if (donorBean!=null){
                name = donorBean.getGivenName() + " " + donorBean.getSurname();
                nameText.setText(name);
                bloodText.setText(donorBean.getBloodGroup());
            }


        Button btnSearchAgain = (Button) findViewById(R.id.buttonSearchAgain);

        btnSearchAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestBean.setStatus(3);
                new UpdateRequestService().execute(requestBean);
                Intent newintent = new Intent(DonorDetailsDeclined.this, HomePageActivity.class);
                startActivity(newintent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
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
