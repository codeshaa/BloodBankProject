package com.unitec.bloodbank.bloodbankproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.DataFetchService;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.UserBean;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.UserDataHelper;

public class LoginActivity extends AppCompatActivity  {

    public static UserBean homeUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new DataFetchService().execute("http://localhost:8080/BloodBankWeb/bloodbank/getalluser");
        TextView errorMessage=(TextView) findViewById(R.id.errormsg);
        errorMessage.setVisibility(View.INVISIBLE);
        final EditText username= (EditText)findViewById(R.id.username);
        final EditText password= (EditText)findViewById(R.id.password);
        Button signInButton = (Button) findViewById(R.id.email_sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(username.getText().toString(),password.getText().toString());
            }
        });
        Button registeruserButton = (Button) findViewById(R.id.register);
        registeruserButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUserActivity();
            }
        });
    }

    private void registerUserActivity(){
        Intent intent= new Intent(this,RegistrationActivity.class);
        startActivity(intent);
    }

    private void attemptLogin(String username,String password) {
        Boolean flag=false;
        for(UserBean userBean: UserDataHelper.userBeans){
            if(userBean.getLoginName().equalsIgnoreCase(username) && userBean.getPassword().equalsIgnoreCase(password)){
                Log.i("LoginActivity",userBean.getLoginName());
                Log.i("LoginActivity",userBean.getPassword());
                  flag=true;
                homeUser=userBean;
            }
        }
        if(flag==false){
            Log.i("LoginActivity","Failure");
            TextView errorMessage=(TextView) findViewById(R.id.errormsg);
             errorMessage.setVisibility(View.VISIBLE);
        }
        else{
            Log.i("LoginActivity","Login Successful :User Set As:"+homeUser.getGivenName()+" " +homeUser.getSurname());
            TextView errorMessage=(TextView) findViewById(R.id.errormsg);
            errorMessage.setVisibility(View.INVISIBLE);
            //Intent intent= new Intent(this,);
            //startActivity(intent);
        }
    }

}

