package com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.unitec.bloodbank.bloodbankproject.LoginActivity;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.RequestBean;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.UserBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JOEL on 6/6/2017.
 */

public class FetchRequestService extends AsyncTask {

    String getDonorUrl = "https://bloodbankweb.mybluemix.net/bloodbank/getdonorrequests";
    String getRequesterUrl = "https://bloodbankweb.mybluemix.net/bloodbank/getuserrequests";


    @Override
    protected Object doInBackground(Object... u) {

        StringBuilder responseOutput = new StringBuilder();

        UserBean user= LoginActivity.homeUser;
        Log.i("User data fetch compl",""+user.getUserId());

        try {
            URL donorUrl = new URL(getDonorUrl);
            URL requesterUrl = new URL(getRequesterUrl);

            JSONObject userJSON= new JSONObject();
            userJSON.put("userId",user.getUserId());
            userJSON.put("givenName",user.getGivenName());
            userJSON.put("surname",user.getSurname());
            userJSON.put("dob",user.getDob());
            userJSON.put("gender",user.getGender());
            userJSON.put("bloodGroup",user.getBloodGroup());
            userJSON.put("phone",user.getPhone());
            userJSON.put("email",user.getEmail());
            userJSON.put("loginName",user.getLoginName());
            userJSON.put("address",user.getAddress());
            userJSON.put("password",user.getPassword());
            userJSON.put("isDonor",user.isDonor());

            HttpURLConnection conn = (HttpURLConnection) donorUrl.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            DataOutputStream printout = new DataOutputStream(conn.getOutputStream ());
            printout.writeBytes(userJSON.toString());
            printout.flush ();
            printout.close ();

            UserDataHelper.donorRequests.removeAll(UserDataHelper.donorRequests);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while((line = br.readLine()) != null ) {
                Log.i("User Data","=" +line);
                responseOutput.append(line);

                JSONArray jsonArray = new JSONArray(line);
                Log.i("Donor Requests",""+jsonArray);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject= jsonArray.getJSONObject(i);
                    RequestBean allRequesr= new RequestBean();
                    allRequesr.setRequestId(jsonObject.getInt("requestId"));
                    allRequesr.setDonor(UserDataHelper.getUserBeanFromJSON(jsonObject.getJSONObject("donor")));
                    allRequesr.setRequester(UserDataHelper.getUserBeanFromJSON(jsonObject.getJSONObject("requester")));
                    allRequesr.setStatus(jsonObject.getInt("status"));
                    allRequesr.setRequesterMessage(jsonObject.getString("requesterMessage"));
                    UserDataHelper.donorRequests.add(allRequesr);
                }

            }
            conn.disconnect();
            br.close();

            conn = (HttpURLConnection) requesterUrl.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type", "application/json");
            //  conn.setRequestProperty("Host", "android.bloodbank.gr");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            printout = new DataOutputStream(conn.getOutputStream ());
            printout.writeBytes(userJSON.toString());
            printout.flush ();
            printout.close ();

            UserDataHelper.userRequests.removeAll(UserDataHelper.userRequests);
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            line = "";
            while((line = br.readLine()) != null ) {
                Log.i("User Data","=" +line);
                responseOutput.append(line);

                JSONArray jsonArray = new JSONArray(line);
                Log.i("Donor Requests",""+jsonArray);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject= jsonArray.getJSONObject(i);
                    RequestBean allRequesr= new RequestBean();

                    allRequesr.setRequestId(jsonObject.getInt("requestId"));

                    allRequesr.setDonor(UserDataHelper.getUserBeanFromJSON(jsonObject.getJSONObject("donor")));

                    allRequesr.setRequester(UserDataHelper.getUserBeanFromJSON(jsonObject.getJSONObject("requester")));

                    allRequesr.setStatus(jsonObject.getInt("status"));

                    allRequesr.setRequesterMessage(jsonObject.getString("requesterMessage"));

                    UserDataHelper.userRequests.add(allRequesr);
                }

            }
            br.close();
        }catch (Exception e){

            e.printStackTrace();
        }
        UserDataHelper.categorizeRequests();

        return null;
    }

}
