package com.example.amam.dairy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class VeteranViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtNameVet, txtTitleVet, txtPhoneVet, txtPriceVet;
    public RatingBar rateVet;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public VeteranViewHolder(View itemView) {
        super(itemView);

        txtNameVet = (TextView)itemView.findViewById(R.id.namevet);
        txtTitleVet = (TextView)itemView.findViewById(R.id.titlevet);
        txtPhoneVet = (TextView)itemView.findViewById(R.id.phonevet);
        txtPriceVet = (TextView)itemView.findViewById(R.id.pricevet);
        rateVet = (RatingBar) itemView.findViewById(R.id.ratingvet);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
