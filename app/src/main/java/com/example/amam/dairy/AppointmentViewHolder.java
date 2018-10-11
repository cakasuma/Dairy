package com.example.amam.dairy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class AppointmentViewHolder extends RecyclerView.ViewHolder {

    public TextView txtAppId, txtAppName, txtAppPhone, txtAppDate, txtAppPrice, txtAppTitle;
    public AppointmentViewHolder(View itemView) {
        super(itemView);

        txtAppId = (TextView)itemView.findViewById(R.id.history_id);
        txtAppName = (TextView)itemView.findViewById(R.id.history_name);
        txtAppPhone = (TextView)itemView.findViewById(R.id.history_phone);
        txtAppDate = (TextView)itemView.findViewById(R.id.history_datetime);
        txtAppPrice = (TextView)itemView.findViewById(R.id.history_price);
        txtAppTitle = (TextView)itemView.findViewById(R.id.history_title);
    }
}
