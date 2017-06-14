package com.unitec.bloodbank.bloodbankproject;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.UserBean;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.ListViewAdapter;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.UserDataHelper;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {

    public static final String USER_ID = "com.unitec.bloodbank";
    String[] bloodString;
    private ListViewAdapter adapter;
    private ArrayList<UserBean> userBloodModels = UserDataHelper.userBeans;
    private ArrayList<UserBean> bPositiveDonors = buildBloodGroup("B+");
    private ArrayList<UserBean> bNegativeDonors = buildBloodGroup("B-");
    private ArrayList<UserBean> aPositiveDonors = buildBloodGroup("A+");
    private ArrayList<UserBean> aNegativeDonors = buildBloodGroup("A-");
    private ArrayList<UserBean> oNegativeDonors = buildBloodGroup("O-");
    private ArrayList<UserBean> oPositiveDonors = buildBloodGroup("O+");
    private ArrayList<UserBean> aBPositiveDonors = buildBloodGroup("AB+");
    private ArrayList<UserBean> aBNegativeDonors = buildBloodGroup("AB-");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Populate Blood type drop down
        this.bloodString = new String[] {
                "My Blood", "A+", "A-", "B+", "B-", "AB+" , "AB-", "O+", "O-"
        };

        final Spinner bloodSpinner = (Spinner) findViewById(R.id.bloods);
        final ListView listView = (ListView) findViewById(R.id.donor_list);

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this,
                R.layout.spinner_layout, bloodString);
        bloodSpinner.setAdapter(adapterSpinner);

        // showing appropriate donors with blood rules as per the selection
        bloodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                String bloodGroup = bloodSpinner.getSelectedItem().toString();
                if (bloodGroup.equalsIgnoreCase("My Blood")){
                    bloodGroup = LoginActivity.homeUser.getBloodGroup();
                }
                switch (bloodGroup){

                    case "A+" : userBloodModels.clear();
                                userBloodModels.addAll(aPositiveDonors);
                                userBloodModels.addAll(aNegativeDonors);
                                userBloodModels.addAll(oPositiveDonors);
                                userBloodModels.addAll(oNegativeDonors);
                                Toast.makeText(HomePageActivity.this, "You may receive A+, A-, O+ & O- blood types", Toast.LENGTH_SHORT).show();
                                adapter = new ListViewAdapter(userBloodModels, getApplicationContext());
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            UserBean userBean = userBloodModels.get(i);
                                            int userBeanId = userBean.getUserId();
                                            Intent intent = new Intent(HomePageActivity.this, DonorDetails.class);
                                            intent.putExtra(USER_ID, userBeanId);
                                            startActivity(intent);

                                        }
                                    });
                                break;

                    case "A-" : userBloodModels.clear();
                                userBloodModels.addAll(aNegativeDonors);
                                userBloodModels.addAll(oNegativeDonors);
                                Toast.makeText(HomePageActivity.this, "You may receive A- & O- blood types", Toast.LENGTH_SHORT).show();
                                adapter = new ListViewAdapter(userBloodModels, getApplicationContext());
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            UserBean userBean = userBloodModels.get(i);
                                            int userBeanId = userBean.getUserId();
                                            Intent intent = new Intent(HomePageActivity.this, DonorDetails.class);
                                            intent.putExtra(USER_ID, userBeanId);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                        }
                                    });
                                break;

                    case "B+" : userBloodModels.clear();
                                userBloodModels.addAll(bPositiveDonors);
                                userBloodModels.addAll(bNegativeDonors);
                                userBloodModels.addAll(oPositiveDonors);
                                userBloodModels.addAll(oNegativeDonors);
                                Toast.makeText(HomePageActivity.this, "You may receive B+, B-, O+ & O- blood types", Toast.LENGTH_SHORT).show();
                                adapter = new ListViewAdapter(userBloodModels, getApplicationContext());
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        UserBean userBean = userBloodModels.get(i);
                                        int userBeanId = userBean.getUserId();
                                        Intent intent = new Intent(HomePageActivity.this, DonorDetails.class);
                                        intent.putExtra(USER_ID, userBeanId);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                    }
                                });
                                break;

                    case "B-" : userBloodModels.clear();
                                userBloodModels.addAll(bNegativeDonors);
                                userBloodModels.addAll(oNegativeDonors);
                                Toast.makeText(HomePageActivity.this, "You may receive B- & O- blood types", Toast.LENGTH_SHORT).show();
                                adapter = new ListViewAdapter(userBloodModels, getApplicationContext());
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        UserBean userBean = userBloodModels.get(i);
                                        int userBeanId = userBean.getUserId();
                                        Intent intent = new Intent(HomePageActivity.this, DonorDetails.class);
                                        intent.putExtra(USER_ID, userBeanId);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                    }
                                });
                                break;

                    case "AB+": userBloodModels.clear();
                                userBloodModels.addAll(aBPositiveDonors);
                                userBloodModels.addAll(aBNegativeDonors);
                                userBloodModels.addAll(oPositiveDonors);
                                userBloodModels.addAll(oNegativeDonors);
                                userBloodModels.addAll(aPositiveDonors);
                                userBloodModels.addAll(aNegativeDonors);
                                userBloodModels.addAll(bPositiveDonors);
                                userBloodModels.addAll(bNegativeDonors);
                                Toast.makeText(HomePageActivity.this, "You may receive any blood types", Toast.LENGTH_SHORT).show();
                                adapter = new ListViewAdapter(userBloodModels, getApplicationContext());
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        UserBean userBean = userBloodModels.get(i);
                                        int userBeanId = userBean.getUserId();
                                        Intent intent = new Intent(HomePageActivity.this, DonorDetails.class);
                                        intent.putExtra(USER_ID, userBeanId);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                    }
                                });
                                break;

                    case "AB-": userBloodModels.clear();
                                userBloodModels.addAll(aBNegativeDonors);
                                userBloodModels.addAll(aNegativeDonors);
                                userBloodModels.addAll(bNegativeDonors);
                                userBloodModels.addAll(oNegativeDonors);
                                Toast.makeText(HomePageActivity.this, "You may receive AB-, A-, B- & O- blood types", Toast.LENGTH_SHORT).show();
                                adapter = new ListViewAdapter(userBloodModels, getApplicationContext());
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        UserBean userBean = userBloodModels.get(i);
                                        int userBeanId = userBean.getUserId();
                                        Intent intent = new Intent(HomePageActivity.this, DonorDetails.class);
                                        intent.putExtra(USER_ID, userBeanId);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                    }
                                });
                                break;

                    case "O+" : userBloodModels.clear();
                                userBloodModels.addAll(oPositiveDonors);
                                userBloodModels.addAll(oNegativeDonors);
                                Toast.makeText(HomePageActivity.this, "You may receive O+ & O- blood types", Toast.LENGTH_SHORT).show();
                                adapter = new ListViewAdapter(userBloodModels, getApplicationContext());
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        UserBean userBean = userBloodModels.get(i);
                                        int userBeanId = userBean.getUserId();
                                        Intent intent = new Intent(HomePageActivity.this, DonorDetails.class);
                                        intent.putExtra(USER_ID, userBeanId);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                    }
                                });
                                break;

                        case "O-" : userBloodModels.clear();
                                userBloodModels.addAll(oNegativeDonors);
                                Toast.makeText(HomePageActivity.this, "You may receive only O- blood types", Toast.LENGTH_SHORT).show();
                                adapter = new ListViewAdapter(userBloodModels, getApplicationContext());
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        UserBean userBean = userBloodModels.get(i);
                                        int userBeanId = userBean.getUserId();
                                        Intent intent = new Intent(HomePageActivity.this, DonorDetails.class);
                                        intent.putExtra(USER_ID, userBeanId);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                    }
                                });
                                break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent home = new Intent(HomePageActivity.this, UserDashboard.class );
        startActivity(home);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent= new Intent(HomePageActivity.this, ProfileInfo.class);
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.account:
                startActivity(intent);
                return true;
            case R.id.exit:
                finish();
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Getting list of blood donors with specific blood group
    private  ArrayList<UserBean> buildBloodGroup(String bloodGroup){
        ArrayList<UserBean> userList = new ArrayList<>();
        for(UserBean userBean: UserDataHelper.userBeans){
            if (userBean.isDonor() && userBean.getBloodGroup().equalsIgnoreCase(bloodGroup)){
                if (!userBean.getLoginName().equals(LoginActivity.homeUser.getLoginName())) {
                    userList.add(userBean);
                }
            }
        }
        return userList;
    }
}
