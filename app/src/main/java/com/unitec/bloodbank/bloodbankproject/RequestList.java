package com.unitec.bloodbank.bloodbankproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.ListViewAdapter;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.RequesterListViewAdapter;

public class RequestList extends Activity {

    private RequesterListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        final ListView listView = (ListView) findViewById(R.id.request_list);

        // initiate requester listview adapter
 //       adapter = new RequesterListViewAdapter( put userbean list with request here, getApplicationContext());


        // set adapter
        listView.setAdapter(adapter);
    }
}
