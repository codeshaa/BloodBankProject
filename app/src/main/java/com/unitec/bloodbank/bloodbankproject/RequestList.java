package com.unitec.bloodbank.bloodbankproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.RequestBean;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.UserBean;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.ListViewAdapter;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.RequesterListViewAdapter;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.UserDataHelper;

public class RequestList extends Activity {

    private RequesterListViewAdapter adapter;
    public static final String USER_ID = "com.unitec.bloodbank";
    public static final String REQUEST_ID = "com.unitec.bloodbank";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        final ListView listView = (ListView) findViewById(R.id.request_list);

        // initiate requester listview adapter
        adapter = new RequesterListViewAdapter(UserDataHelper.donorRequests, getApplicationContext());


        // set adapter
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RequestBean requestBean = UserDataHelper.donorRequests.get(i);
                int userBeanId = requestBean.getRequester().getUserId();
                Intent intent = new Intent(RequestList.this, RequesterDetail.class);
                intent.putExtra(REQUEST_ID, requestBean.getRequestId());

                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent home = new Intent(RequestList.this, UserDashboard.class );
        startActivity(home);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
