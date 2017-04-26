package com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business;

import android.os.AsyncTask;
import android.util.Log;

import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.UserBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JOEL on 4/23/2017.
 */

public class SaveUserInterface extends AsyncTask {

    String getUrl = "https://bloodbankweb.mybluemix.net/bloodbank/adduser";
    URL url;
    UserBean newUser;
    public void insertUser(UserBean newUser){
        this.newUser=newUser;
        StringBuilder responseOutput = (StringBuilder) doInBackground(url);

    }
    @Override
    protected  Object doInBackground(Object... u){
        StringBuilder responseOutput = new StringBuilder();

        try {
            this.newUser=(UserBean)u[0];
            URL url = new URL(getUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Host", "android.bloodbank.gr");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            JSONObject userJSON= new JSONObject();
            userJSON.put("userId",newUser.getUserId());
            userJSON.put("givenName",newUser.getGivenName());
            userJSON.put("surname",newUser.getSurname());
            userJSON.put("dob",newUser.getDob());
            userJSON.put("gender",newUser.getGender());
            userJSON.put("bloodGroup",newUser.getBloodGroup());
            userJSON.put("phone",newUser.getPhone());
            userJSON.put("email",newUser.getEmail());
            userJSON.put("loginName",newUser.getLoginName());
            userJSON.put("address",newUser.getAddress());
            userJSON.put("password",newUser.getPassword());
            userJSON.put("isDonor",newUser.isDonor());
            DataOutputStream printout = new DataOutputStream(conn.getOutputStream ());
            printout.writeBytes(userJSON.toString());
            printout.flush ();
            printout.close ();

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
                    userBean.setPhone(jsonObject.getString("bloodGroup"));
                    userBean.setBloodGroup(jsonObject.getString("phone"));
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
