package com.example.amam.dairy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MarketViewHolder extends RecyclerView.ViewHolder {

    public TextView txtMarketId, txtMarketDate, txtFarmerName, txtPhoneUpload, txtQuantity, txtMarketPrice;

    public MarketViewHolder(View itemView) {
        super(itemView);
        txtMarketId = (TextView)itemView.findViewById(R.id.market_id);
        txtMarketDate = (TextView)itemView.findViewById(R.id.market_datetime);
        txtFarmerName = (TextView)itemView.findViewById(R.id.farmer_name);
        txtPhoneUpload = (TextView)itemView.findViewById(R.id.market_phone);
        txtQuantity = (TextView)itemView.findViewById(R.id.market_quantity);
        txtMarketPrice = (TextView)itemView.findViewById(R.id.market_price);
    }
}
