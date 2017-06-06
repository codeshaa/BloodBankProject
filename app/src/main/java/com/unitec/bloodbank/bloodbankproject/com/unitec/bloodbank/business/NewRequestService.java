package com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business;

import android.os.AsyncTask;
import android.util.Log;

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

public class NewRequestService extends AsyncTask {

    String getUrl = "https://bloodbankweb.mybluemix.net/bloodbank/newrequest";

    @Override
    protected Object doInBackground(Object... params) {
        StringBuilder responseOutput = new StringBuilder();

        try {
            RequestBean newRequset = (RequestBean) params[0];
            URL url = new URL(getUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            JSONObject requestJSON = new JSONObject();
            requestJSON.put("userId",newRequset.getRequestId());
            requestJSON.put("donor",newRequset.getDonor());
            requestJSON.put("requester",newRequset.getRequester());
            requestJSON.put("status",newRequset.getStatus());
            requestJSON.put("requesterMessage",newRequset.getRequesterMessage());

            DataOutputStream printout = new DataOutputStream(conn.getOutputStream ());
            printout.writeBytes(requestJSON.toString());
            printout.flush ();
            printout.close ();

            UserDataHelper.userRequests.removeAll(UserDataHelper.userRequests);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while((line = br.readLine()) != null ) {
                Log.i("User Data","=" +line);
                responseOutput.append(line);

                JSONArray jsonArray = new JSONArray(line);
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
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
