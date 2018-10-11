package com.example.amam.dairy;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookedVeteran extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Appointment, AppointmentViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference appointments;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_veteran);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        database = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        appointments = database.getReference("appointments").child(mUser.getUid());


        recyclerView = (RecyclerView)findViewById(R.id.historyrecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FirebaseRecyclerAdapter<Appointment, AppointmentViewHolder>(
                Appointment.class,
                R.layout.card_app_history,
                AppointmentViewHolder.class,
                appointments
        ) {
            @Override
            protected void populateViewHolder(AppointmentViewHolder viewHolder, Appointment model, int position) {
                viewHolder.txtAppId.setText("ID: "+adapter.getRef(position).getKey());
                viewHolder.txtAppName.setText("Name: "+model.getVetName());
                viewHolder.txtAppDate.setText(model.getDatetime());
                viewHolder.txtAppPhone.setText("Phone: "+model.getVetPhone());
                viewHolder.txtAppTitle.setText("Title: "+model.getVetTitle());
                viewHolder.txtAppPrice.setText("RM "+model.getVetPrice());
            }
        };
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
