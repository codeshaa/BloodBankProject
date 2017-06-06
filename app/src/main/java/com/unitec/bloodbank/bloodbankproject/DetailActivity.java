package com.unitec.bloodbank.bloodbankproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.UserBean;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.UserDataHelper;

public class DetailActivity extends AppCompatActivity {

    public static final String USER_ADDRESS = "com.unitec.bloodbank.address";
    public static final String USER_BLOOD = "com.unitec.bloodbank.blood";
    public static final String USER_NAME = "com.unitec.bloodbank.name";
//ss
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        TextView nameText = (TextView) findViewById(R.id.name_text);
        TextView bloodText = (TextView) findViewById(R.id.text_blood);
        TextView addressText = (TextView) findViewById(R.id.text_address);
        addressText.setMovementMethod(new ScrollingMovementMethod());
        TextView mobileText = (TextView) findViewById(R.id.text_mobile);
        TextView emailText = (TextView) findViewById(R.id.text_email);
        String email = null;
        String mobile = null;
        String address = null;
        String name = null;
        String blood = null;
        ImageButton iButtonMobile = (ImageButton) findViewById(R.id.imageButtonMobile);
        ImageButton iButtonEmail = (ImageButton) findViewById(R.id.imageButtonEmail);
        ImageButton iButtonLocation = (ImageButton) findViewById(R.id.imageButtonLocation);
        ImageButton iButtonCompleted = (ImageButton) findViewById(R.id.imageButtonComplete);
        LinearLayout completeLayout = (LinearLayout) findViewById(R.id.layout_completed);

        Intent intent = getIntent();
        int message = intent.getIntExtra(HomePageActivity.USER_ID, 0);

        for(UserBean userBean: UserDataHelper.userBeans){

            if (userBean.getUserId() == message){
                name = userBean.getGivenName() + " " + userBean.getSurname();
                nameText.setText(name);
                bloodText.setText(userBean.getBloodGroup());
                blood = userBean.getBloodGroup();
                addressText.setText(userBean.getAddress());
                address = userBean.getAddress();
                mobileText.setText(userBean.getPhone());
                emailText.setText(userBean.getEmail());
                email = userBean.getEmail();
                mobile = "tel:" + userBean.getPhone();
                break;

                }
            }

        final String finalMobile = mobile;
        iButtonMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(finalMobile));
                startActivity(intent);
            }
        });

        final String finalEmail = email;
        iButtonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.setType("text/html");
                String[] recipients={finalEmail};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Request for blood donation via Blood Bank App");
                intent.putExtra(Intent.EXTRA_TEXT,"Hi, \n\n\tI would like to recieve your blood as I am in an emergency condition.");

                try {
                    startActivity(Intent.createChooser(intent, "Send mail"));
                    finish();
                    Log.i("Sent Email", "");
                }
                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(DetailActivity.this, "There are no email clients available.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        final String finalAddress = address;
        final String finalName = name;
        final String finalBlood = blood;
        iButtonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MapsActivity.class );
                intent.putExtra(USER_ADDRESS, finalAddress);
                intent.putExtra(USER_NAME, finalName);
                intent.putExtra(USER_BLOOD, finalBlood);
                startActivity(intent);
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
