package com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.business;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unitec.bloodbank.bloodbankproject.R;
import com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean.UserBean;

import java.util.ArrayList;

/**
 * Created by SHAANZbook on 11/06/2017.
 */

public class RequesterListViewAdapter extends ArrayAdapter<UserBean> implements View.OnClickListener {

    private ArrayList<UserBean> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtRequestStatus;
        TextView txtRequester;
        TextView txtBlood;
        ImageView arrow;
    }

    public RequesterListViewAdapter(ArrayList<UserBean> data, Context context) {
        super(context, R.layout.request_list_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        UserBean dataModel=(UserBean) object;

        switch (v.getId())
        {
            case R.id.item_arrow:
                Snackbar.make(v, "Name: " +dataModel.getGivenName(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        UserBean dataModel = (UserBean) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        RequesterListViewAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new RequesterListViewAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.request_list_item, parent, false);
            viewHolder.txtRequestStatus = (TextView) convertView.findViewById(R.id.req_status);
            viewHolder.txtRequester = (TextView) convertView.findViewById(R.id.requester);
            viewHolder.txtBlood = (TextView) convertView.findViewById(R.id.blood_group);
            viewHolder.arrow = (ImageView) convertView.findViewById(R.id.item_arrow);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RequesterListViewAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

//        viewHolder.txtRequestStatus.setText(dataModel.getStatus());
        viewHolder.txtRequester.setText(dataModel.getGivenName());
        viewHolder.txtBlood.setText(dataModel.getBloodGroup());
        viewHolder.arrow.setOnClickListener(this);
        viewHolder.arrow.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
