package com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business;

import android.util.Log;

import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.RequestBean;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.UserBean;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by JOEL on 4/21/2017.
 */

public class UserDataHelper {

    public static ArrayList<UserBean> userBeans=new ArrayList<UserBean>();
    public static ArrayList<RequestBean> userRequests=new ArrayList<RequestBean>();
    public static ArrayList<RequestBean> donorRequests=new ArrayList<RequestBean>();
    public static ArrayList<RequestBean> userPendingRequests=new ArrayList<RequestBean>();
    public static ArrayList<RequestBean> userAcceptedRequests=new ArrayList<RequestBean>();
    public static ArrayList<RequestBean> userDeclinedRequests=new ArrayList<RequestBean>();

    public static boolean validateEmail(String email){
        return  email.matches(".+\\@.+\\..+");
    }
    public static boolean validateAge(String dob){


        try {
            Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
            //create calendar object for birth day
            Calendar birthDay = Calendar.getInstance();
            birthDay.setTimeInMillis(birthDate.getTime());
            //create calendar object for current day
            long currentTime = System.currentTimeMillis();
            Calendar now = Calendar.getInstance();
            now.setTimeInMillis(currentTime);
            //Get difference between years
            int years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
            if (years>=18)
                return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static UserBean getUserBeanFromJSON(JSONObject userJSON){
        UserBean userBean= new UserBean();
        try {
            userBean.setUserId(userJSON.getInt("userId"));
            userBean.setGivenName(userJSON.getString("givenName"));
            userBean.setSurname(userJSON.getString("surname"));
            userBean.setDob(userJSON.getString("dob"));
            userBean.setGenderAsString(userJSON.getString("gender"));
            userBean.setPhone(userJSON.getString("phone"));
            userBean.setBloodGroup(userJSON.getString("bloodGroup"));
            userBean.setEmail(userJSON.getString("email"));
            userBean.setAddress(userJSON.getString("address"));
            userBean.setLoginName(userJSON.getString("loginName"));
            userBean.setPassword(userJSON.getString("password"));
            userBean.setDonor(userJSON.getBoolean("isDonor"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return  userBean;
    }

    public static void categorizeRequests(){

        Log.i("USerDataHelper","Size="+userRequests.size());
        userPendingRequests.removeAll(userPendingRequests);
        userAcceptedRequests.removeAll(userAcceptedRequests);
        userDeclinedRequests.removeAll(userDeclinedRequests);
        for(RequestBean bean:userRequests){
            switch(bean.getStatus())
            {
                case 0: userPendingRequests.add(bean);
                        break;
                case 1: userAcceptedRequests.add(bean);
                          break;
                case 2: userDeclinedRequests.add(bean);
                           break;

            }
        }

    }
}

