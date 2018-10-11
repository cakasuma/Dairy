package com.example.amam.dairy;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Veterinarian extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fabTable;

    FirebaseRecyclerAdapter<Veteran, VeteranViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference veterans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veterinarian);

        database = FirebaseDatabase.getInstance();
        veterans = database.getReference("veterans");

        fabTable = (FloatingActionButton)findViewById(R.id.fabvet);

        fabTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent veteranhistoryint = new Intent(Veterinarian.this, BookedVeteran.class);
                startActivity(veteranhistoryint);

            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.veteranrecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FirebaseRecyclerAdapter<Veteran, VeteranViewHolder>(
                Veteran.class,
                R.layout.card_veteran,
                VeteranViewHolder.class,
                veterans.orderByChild("status").equalTo("available")
        ) {
            @Override
            protected void populateViewHolder(VeteranViewHolder viewHolder, final Veteran model, int position) {
                viewHolder.txtNameVet.setText(model.getName());
                viewHolder.txtTitleVet.setText(model.getTitle());
                viewHolder.txtPhoneVet.setText(model.getPhone());
                viewHolder.txtPriceVet.setText(model.getPrice());
                viewHolder.rateVet.setRating(model.getRating());
                final Veteran local = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        CustomDialogAppointment cda = new CustomDialogAppointment(Veterinarian.this, adapter.getRef(position).getKey().toString(), local);
                        cda.show();
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.marketM:
                Intent veteranIntent = new Intent(Veterinarian.this, HomeActivity.class);
                startActivity(veteranIntent);
                return true;
            case R.id.veterinarianM:


                return true;
            case R.id.logoutM:
                FirebaseAuth.getInstance().signOut();
                Intent signoutIntent = new Intent(Veterinarian.this, MainActivity.class);
                startActivity(signoutIntent);

                return true;
            case R.id.addcowM:
                Intent addcowIntent = new Intent(Veterinarian.this, AddCowActivity.class);
                startActivity(addcowIntent);

                return true;
            case R.id.cowM:
                Intent cowMarketIntent = new Intent(Veterinarian.this, CowActivity.class);
                startActivity(cowMarketIntent);

                return true;
            case R.id.alarmM:
                Intent alarmIntent = new Intent(Veterinarian.this, AlarmMain.class);
                startActivity(alarmIntent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
