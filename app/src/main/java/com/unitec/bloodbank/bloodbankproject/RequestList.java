package com.unitec.bloodbank.bloodbankproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.ListViewAdapter;

public class RequestList extends Activity {

    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        final ListView listView = (ListView) findViewById(R.id.request_list);

        // initiate listview adapter


        // set adapter
        listView.setAdapter(adapter);
    }
}
