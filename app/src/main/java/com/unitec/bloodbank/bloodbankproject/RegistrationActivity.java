package com.unitec.bloodbank.bloodbankproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ParseException;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.TimeUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.UserBean;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.DataFetchService;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.SaveUserInterface;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.UserDataHelper;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegistrationActivity extends AppCompatActivity {
    Uri profilepicURI;
    Bitmap bitmap = null;
    String[] arraySpinner;
    //private  static int GET_FROM_GALLERY=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Populate Blood type drop down
        this.arraySpinner = new String[] {
                "A+", "A-", "B+", "B-", "AB+" , "AB-", "O+", "O-"
        };
        Spinner s = (Spinner) findViewById(R.id.bloodgroup);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);

        TextView formErrors= (TextView)findViewById(R.id.formErrors);
        formErrors.setVisibility(View.INVISIBLE);

        Button register= (Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAndValidate();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("uri", profilepicURI);
    }

    private byte[] getLogoImage(String url){
        try {
            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();

            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] imageByteArray= new byte[700];
            int current = 0,i=0;
            while ((current = bis.read()) != -1) {
                imageByteArray[i]=((byte) current);
                i++;
            }

            return imageByteArray;
        } catch (Exception e) {
            Log.d("ImageManager", "Error: " + e.toString());
        }
        return null;
    }

    private void fetchAndValidate(){

        try {
            boolean isValidated = false;
            StringBuilder errorText=new StringBuilder();
            TextView formErrors= (TextView)findViewById(R.id.formErrors);
            UserBean newUser = new UserBean();

            //Get Form Data from XML
            String givenName=((EditText) findViewById(R.id.givenname)).getText().toString();
            String surname=((EditText)findViewById(R.id.surname)).getText().toString();
            String dobDDMMYYYY=((EditText) findViewById(R.id.dob)).getText().toString();
            int selectedId=((RadioGroup) findViewById(R.id.gender)).getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            String bloodGroup=((Spinner)findViewById(R.id.bloodgroup)).getSelectedItem().toString();
            String email=((EditText) findViewById(R.id.email)).getText().toString();
            String address = ((EditText) findViewById(R.id.streetname)).getText().toString() + "," + ((EditText) findViewById(R.id.suburb)).getText().toString() + "," + ((EditText) findViewById(R.id.city)).getText().toString() + "," + ((EditText) findViewById(R.id.postcode)).getText().toString();
            String phone=((EditText) findViewById(R.id.phone)).getText().toString();
            String loginName=((EditText) findViewById(R.id.loginname)).getText().toString();
            String pass = ((EditText) findViewById(R.id.password)).getText().toString();
            String confirmPass = ((EditText) findViewById(R.id.confirmpass)).getText().toString();

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


            if(((CheckBox) findViewById(R.id.checkBox)).isChecked())
                newUser.setDonor(true);
            else
                newUser.setDonor(false);


            if (pass.equals(confirmPass)) {
                newUser.setPassword(pass);
           }
           else {
                errorText.append("* Passwords donot match \n");
                isValidated=false;
            }

            if(isValidated){
                formErrors.setVisibility(View.INVISIBLE);
                formErrors.clearComposingText();

                newUser.setGivenName(givenName);
                newUser.setSurname(surname);
                newUser.setDob(formatDate(dobDDMMYYYY,"dd/MM/yyyy","yyyy/MM/dd"));
                newUser.setGenderAsString(radioButton.getText().toString());
                newUser.setBloodGroup(bloodGroup);
                newUser.setEmail(email);
                newUser.setAddress(address);
                newUser.setPhone(phone);
                newUser.setLoginName(loginName);

                new SaveUserInterface().execute(newUser);
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


        /*ImageView profilePic= (ImageView)findViewById(R.id.profilepic);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

            }
        });
        if (savedInstanceState != null) {
            profilepicURI = savedInstanceState.getParcelable("uri");
            profilePic.setImageURI(profilepicURI);
            profilePic.setImageURI(profilepicURI);
        }*/
  /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                profilepicURI=selectedImage;
                ImageView profilePic=(ImageView)findViewById(R.id.profilepic);
                profilePic.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }*/