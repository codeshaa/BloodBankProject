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
 * Created by JOEL on 5/18/2017.
 */

public class UpdateUserService extends AsyncTask {

    String getUrl = "https://bloodbankweb.mybluemix.net/bloodbank/updateuser";

    URL url;
    UserBean updatedUser;

    public void UpdateUser(UserBean updatedUser){
        this.updatedUser=updatedUser;
        StringBuilder responseOutput = (StringBuilder) doInBackground(url);

    }
    @Override
    protected  Object doInBackground(Object... u){
        StringBuilder responseOutput = new StringBuilder();

        try {
            UserDataHelper.userBeans.removeAll(UserDataHelper.userBeans);
            this.updatedUser=(UserBean)u[0];
Log.i("AAAAAAAAAAAAA",""+this.updatedUser.getUserId());
            URL url = new URL(getUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type","application/json");
         //   conn.setRequestProperty("Host", "android.bloodbank.gr");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            JSONObject userJSON= new JSONObject();
            userJSON.put("userId",updatedUser.getUserId());

            userJSON.put("givenName",updatedUser.getGivenName());
            userJSON.put("surname",updatedUser.getSurname());
            userJSON.put("dob",updatedUser.getDob());
            userJSON.put("gender",updatedUser.getGender());
            userJSON.put("bloodGroup",updatedUser.getBloodGroup());
            userJSON.put("phone",updatedUser.getPhone());
            userJSON.put("email",updatedUser.getEmail());
            userJSON.put("loginName",updatedUser.getLoginName());
            userJSON.put("address",updatedUser.getAddress());
            userJSON.put("password",updatedUser.getPassword());
            userJSON.put("isDonor",updatedUser.isDonor());
            DataOutputStream printout = new DataOutputStream(conn.getOutputStream ());
            printout.writeBytes(userJSON.toString());
            printout.flush ();
            printout.close ();
           // Log.i("SASSAAS",new BufferedReader(new InputStreamReader(conn.getErrorStream())).readLine());
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
