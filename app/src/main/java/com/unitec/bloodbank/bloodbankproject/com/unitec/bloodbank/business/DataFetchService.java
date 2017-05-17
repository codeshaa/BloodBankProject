package com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business;

import android.os.AsyncTask;
import android.util.Log;

import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.UserBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JOEL on 4/20/2017.
 */

public class DataFetchService extends AsyncTask {


    String getUrl = "https://bloodbankweb.mybluemix.net/bloodbank/getalluser";
    URL url;
    public void getAllUser(){
        StringBuilder responseOutput = (StringBuilder) doInBackground(url);
    }
    @Override
    protected  Object doInBackground(Object... u){
        StringBuilder responseOutput = new StringBuilder();

        try {
            URL url = new URL(getUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while((line = br.readLine()) != null ) {
                Log.i("User Data","=" +line);
                responseOutput.append(line);

                JSONArray jsonArray = new JSONArray(line);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject= jsonArray.getJSONObject(i);
                    UserBean userBean= new UserBean();
                    userBean.setUserId(jsonObject.getInt("userId"));
                    userBean.setGivenName(jsonObject.getString("givenName"));
                    userBean.setSurname(jsonObject.getString("surname"));
                    userBean.setDob(jsonObject.getString("dob"));
                    userBean.setGenderAsString(jsonObject.getString("gender"));
                    userBean.setPhone(jsonObject.getString("phone"));
                    userBean.setBloodGroup(jsonObject.getString("bloodGroup"));
                    userBean.setEmail(jsonObject.getString("email"));
                    userBean.setAddress(jsonObject.getString("address"));
                    userBean.setLoginName(jsonObject.getString("loginName"));
                    userBean.setPassword(jsonObject.getString("password"));
                    userBean.setDonor(jsonObject.getBoolean("isDonor"));
                    UserDataHelper.userBeans.add(userBean);
                }

            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return responseOutput;
    }


}
