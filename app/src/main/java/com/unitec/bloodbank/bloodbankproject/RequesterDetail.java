package com.unitec.bloodbank.bloodbankproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.RequestBean;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.UserBean;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.RequesterListViewAdapter;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.UpdateRequestService;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business.UserDataHelper;

public class RequesterDetail extends AppCompatActivity {

    protected static RequestBean requestBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_detail);
        String address = null;
        String name = null;
        String blood = null;

        TextView nameText = (TextView) findViewById(R.id.name_text);
        TextView bloodText = (TextView) findViewById(R.id.text_blood);
        TextView addressText = (TextView) findViewById(R.id.text_address);
        addressText.setMovementMethod(new ScrollingMovementMethod());
        TextView requesterMessage = (TextView) findViewById(R.id.requester_message);

        LinearLayout decline = (LinearLayout) findViewById(R.id.layout_request_decline);
        LinearLayout accept = (LinearLayout) findViewById(R.id.layout_request_accept);

        Intent intent = getIntent();

        int requestId = intent.getIntExtra(RequesterListViewAdapter.REQUEST_ID, 0);

        Log.i("HAAA","Haaaaaa"+requestId);

        for(RequestBean request: UserDataHelper.donorRequests){
            if (request.getRequestId() == requestId){
                UserBean userBean= request.getRequester();
                requestBean=request;
                Log.i("HAAA","Heeee"+request.getRequester().getUserId());
                name = userBean.getGivenName() + " " + userBean.getSurname();
                nameText.setText(name);
                bloodText.setText(userBean.getBloodGroup());
                blood = userBean.getBloodGroup();
                addressText.setText(userBean.getAddress());
                address = userBean.getAddress();
                requesterMessage.setText(request.getRequesterMessage());
                break;
            }
        }
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestBean.setStatus(1);
                Log.i("HAAA","HOOOO");
                new UpdateRequestService().execute(requestBean);
                Intent home = new Intent(RequesterDetail.this, UserDashboard.class );
                startActivity(home);
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestBean.setStatus(2);
                Log.i("HAAA","HOOOO");

                new UpdateRequestService().execute(requestBean);
                Intent home = new Intent(RequesterDetail.this, UserDashboard.class );
                startActivity(home);
            }
        });
    }
}
