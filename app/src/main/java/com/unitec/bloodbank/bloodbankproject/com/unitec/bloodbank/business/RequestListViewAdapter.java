package com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unitec.bloodbank.bloodbankproject.DetailActivity;
import com.unitec.bloodbank.bloodbankproject.DonorDetailsDeclined;
import com.unitec.bloodbank.bloodbankproject.R;
import com.unitec.bloodbank.bloodbankproject.UserDashboard;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.RequestBean;

import java.util.ArrayList;

/**
 * Created by SHAANZbook on 11/06/2017.
 */

public class RequestListViewAdapter extends ArrayAdapter<RequestBean> implements View.OnClickListener {

    private ArrayList<RequestBean> dataSet;
    Context mContext;
    public static final String USER_ID = "com.unitec.bloodbank";
    public static final String REQUEST_ID = "com.unitec.bloodbank";



    // View lookup cache
    private static class ViewHolder {
        TextView txtRequestStatus;
        TextView txtDonorName;
        TextView txtBlood;
        ImageView arrow;
    }

    public RequestListViewAdapter(ArrayList<RequestBean> data, Context context) {
        super(context, R.layout.my_requests_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        RequestBean dataModel=(RequestBean) object;

      switch (v.getId())
        {
           case R.id.item_arrow:
                                if(dataModel.getStatus()==0) {
                                    Snackbar.make(v, "Request is pending aproval with " + dataModel.getDonor().getGivenName(), Snackbar.LENGTH_LONG)
                                            .setAction("No action", null).show();
                                }
                                else if (dataModel.getStatus()==2){
                                    Intent intent= new Intent(mContext,DonorDetailsDeclined.class);
                                    intent.putExtra(REQUEST_ID, dataModel.getRequestId());
                                    v.getContext().startActivity(intent);
                                }
                                else{
                                    Intent intent= new Intent(mContext,DetailActivity.class);
                                    intent.putExtra(REQUEST_ID, dataModel.getRequestId());
                                    v.getContext().startActivity(intent);
                                }

                break;
   }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Log.i("YOOOOOOOOo","WWW");
        RequestBean dataModel = getItem(position);
        Log.i("YOOOOOOOOo","WWW"+dataModel);
        // Check if an existing view is being reused, otherwise inflate the view
        RequestListViewAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new RequestListViewAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.my_requests_item, parent, false);
            viewHolder.txtRequestStatus = (TextView) convertView.findViewById(R.id.req_status);
            viewHolder.txtDonorName = (TextView) convertView.findViewById(R.id.donor_name);
            viewHolder.txtBlood = (TextView) convertView.findViewById(R.id.blood_group);
            viewHolder.arrow = (ImageView) convertView.findViewById(R.id.item_arrow);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RequestListViewAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtRequestStatus.setText(dataModel.getDonor().getAddress());
        viewHolder.txtDonorName.setText(dataModel.getDonor().getGivenName());
        viewHolder.txtBlood.setText(dataModel.getDonor().getBloodGroup());
        viewHolder.arrow.setOnClickListener(this);
        viewHolder.arrow.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
