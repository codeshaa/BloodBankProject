package com.unitec.bloodbank.bloodbankproject;

import android.content.Intent;
import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.UserBean;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.SaveUserInterface;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.UpdateUserService;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.UserDataHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileInfo extends AppCompatActivity {
    String[] arraySpinner;

    private UserBean homeUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        this.homeUser=LoginActivity.homeUser;

        this.arraySpinner = new String[] {
                "A+", "A-", "B+", "B-", "AB+" , "AB-", "O+", "O-"
        };
        Spinner s = (Spinner) findViewById(R.id.ubloodgroup);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);
        ((EditText)findViewById(R.id.ugivenname)).setText(homeUser.getGivenName());
        ((EditText)findViewById(R.id.usurname)).setText(homeUser.getSurname());
        ((EditText) findViewById(R.id.udob)).setText(homeUser.getDob());

        if(homeUser.getGender().equalsIgnoreCase("Male")){
            RadioButton maleButton = (RadioButton) findViewById(R.id.umaleradio);
            maleButton.setChecked(true);
        }
        else{
            RadioButton femaleButton = (RadioButton) findViewById(R.id.ufemaleradio);
            femaleButton.setChecked(true);
        }

        Spinner bloodGroup=((Spinner)findViewById(R.id.ubloodgroup));
        switch(homeUser.getBloodGroup()){
            case "A+" : bloodGroup.setSelection(0);break;
            case "A-" : bloodGroup.setSelection(1);break;
            case "B+" : bloodGroup.setSelection(2);break;
            case "B-" : bloodGroup.setSelection(3);break;
            case "AB+" : bloodGroup.setSelection(4);break;
            case "AB-" : bloodGroup.setSelection(5);break;
            case "O+" : bloodGroup.setSelection(6);break;
            case "O-" : bloodGroup.setSelection(7);break;

        }
        ((EditText) findViewById(R.id.uemail)).setText(homeUser.getEmail());
        String address=homeUser.getAddress();
        ((EditText) findViewById(R.id.uphone)).setText(homeUser.getPhone());
        ((EditText) findViewById(R.id.uloginname)).setText(homeUser.getLoginName());
        ((CheckBox) findViewById(R.id.ucheckBox)).setChecked(homeUser.isDonor());

        Button register= (Button)findViewById(R.id.uregister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAndValidate();
            }
        });
    }


    private void fetchAndValidate(){

        try {
            boolean isValidated = false;
            StringBuilder errorText=new StringBuilder();
            TextView formErrors= (TextView)findViewById(R.id.uformErrors);
            UserBean updatedUser = LoginActivity.homeUser;

            //Get Form Data from XML
            String givenName=((EditText) findViewById(R.id.ugivenname)).getText().toString();
            String surname=((EditText)findViewById(R.id.usurname)).getText().toString();
            String dobDDMMYYYY=((EditText) findViewById(R.id.udob)).getText().toString();
            int selectedId=((RadioGroup) findViewById(R.id.ugender)).getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            String bloodGroup=((Spinner)findViewById(R.id.ubloodgroup)).getSelectedItem().toString();
            String email=((EditText) findViewById(R.id.uemail)).getText().toString();
            String address = ((EditText) findViewById(R.id.ustreetname)).getText().toString() + "," + ((EditText) findViewById(R.id.usuburb)).getText().toString() + "," + ((EditText) findViewById(R.id.ucity)).getText().toString() + "," + ((EditText) findViewById(R.id.upostcode)).getText().toString();
            String phone=((EditText) findViewById(R.id.uphone)).getText().toString();
            String loginName=((EditText) findViewById(R.id.uloginname)).getText().toString();
            String pass = ((EditText) findViewById(R.id.upassword)).getText().toString();
            String confirmPass = ((EditText) findViewById(R.id.uconfirmpass)).getText().toString();

            //Validations
            if(givenName==null||givenName.length()==0 ){
                errorText.append("* Please enter your given name \n");
            }
            else if(surname==null || surname.length()==0){
                errorText.append("* Please enter you surname \n");
            }
            else if(dobDDMMYYYY==null || dobDDMMYYYY.length()==0){
                errorText.append("* Please enter your dob \n");
            }
            else if(UserDataHelper.validateAge(dobDDMMYYYY)){
                errorText.append("* Age has to be above 18 to donate \n");
            }
            else if(radioButton==null){
                errorText.append("* Please enter your gender\n");
            }
            else if(bloodGroup==null || bloodGroup.length()==0){

                errorText.append("* Please enter your blood group \n");
            }
            else if(!UserDataHelper.validateEmail(email)){
                errorText.append("* Please enter a valid email Id\n");
            }
            else if(address==null || address.length()==0){
                errorText.append("* Please enter your address \n");
            }
            else if(phone==null || phone.length()==0){
                errorText.append("* Please enter your contact details \n");
            }
            else if(loginName==null || loginName.length()<4){
                errorText.append("* Please enter a valid login more than 3 characters \n");
            }
            else{

                errorText= new StringBuilder();
                isValidated=true;
            }


            if(((CheckBox) findViewById(R.id.ucheckBox)).isChecked())
                updatedUser.setDonor(true);
            else
                updatedUser.setDonor(false);


            if (pass.equals(confirmPass)) {
                updatedUser.setPassword(pass);
            }
            else {
                errorText.append("* Passwords donot match \n");
                isValidated=false;
            }

            if(isValidated){
                formErrors.setVisibility(View.INVISIBLE);
                formErrors.clearComposingText();

                updatedUser.setGivenName(givenName);
                updatedUser.setSurname(surname);
                updatedUser.setDob(formatDate(dobDDMMYYYY,"dd/MM/yyyy","yyyy/MM/dd"));
                updatedUser.setGenderAsString(radioButton.getText().toString());
                updatedUser.setBloodGroup(bloodGroup);
                updatedUser.setEmail(email);
                updatedUser.setAddress(address);
                updatedUser.setPhone(phone);
                updatedUser.setLoginName(loginName);

                new UpdateUserService().execute(updatedUser);
                Intent intent= new Intent(this,LoginActivity.class);
                startActivity(intent);
            }
            else{
                formErrors.setText(errorText);
                formErrors.setVisibility(View.VISIBLE);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public static String formatDate (String date, String initDateFormat, String endDateFormat) throws ParseException {

        String parsedDate = null;
        try {
            Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
            parsedDate = formatter.format(initDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return parsedDate;
    }
}
