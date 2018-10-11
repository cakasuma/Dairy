package com.example.amam.dairy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CowViewHolder extends RecyclerView.ViewHolder {
    public TextView txtCowId, txtCowAge, txtCowGender, txtCowDescription;

    public CowViewHolder(View itemView) {
        super(itemView);
        txtCowId = (TextView)itemView.findViewById(R.id.cow_id);
        txtCowAge = (TextView)itemView.findViewById(R.id.cow_age);
        txtCowGender = (TextView)itemView.findViewById(R.id.cow_gender);
        txtCowDescription = (TextView)itemView.findViewById(R.id.cow_description);
    }
}
