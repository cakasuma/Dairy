package com.example.amam.dairy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fabTable;

    FirebaseRecyclerAdapter<Market, MarketViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference tables;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = FirebaseDatabase.getInstance();
        tables = database.getReference("markets");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        fabTable = (FloatingActionButton)findViewById(R.id.fabtable);

        fabTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploaddairyintent = new Intent(HomeActivity.this, UploadDairy.class);
                startActivity(uploaddairyintent);

            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.marketrecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Query queries = tables.orderByChild("farmerId");
        queries.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                }
                else{

                    Toast.makeText(HomeActivity.this,"No Item in the market. Click + button to add table booking",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new FirebaseRecyclerAdapter<Market, MarketViewHolder>(
                Market.class,
                R.layout.card_market,
                MarketViewHolder.class,
                tables.orderByChild("farmerId").equalTo(mUser.getUid())
        ) {
            @Override
            protected void populateViewHolder(MarketViewHolder viewHolder, Market model, int position) {
                viewHolder.txtMarketId.setText("ID: "+adapter.getRef(position).getKey());
                viewHolder.txtMarketDate.setText(model.getDatePublish());
                viewHolder.txtFarmerName.setText("Farmer: "+model.getFarmerName());
                viewHolder.txtPhoneUpload.setText("Phone: "+model.getContactPerson());
                viewHolder.txtQuantity.setText("Quantity: "+model.getMilkQuantity()+" L");
                viewHolder.txtMarketPrice.setText("RM "+model.getPrice());
            }
        };
        recyclerView.setAdapter(adapter);
        RecyclerView recyclerView = findViewById(R.id.marketrecycler);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(HomeActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        CustomDailog cdd=new CustomDailog(HomeActivity.this, adapter.getRef(position).getKey().toString());
                        cdd.show();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
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
                //your code
                // EX : call intent if you want to swich to other activity
                return true;
            case R.id.veterinarianM:
                Intent veteranIntent = new Intent(HomeActivity.this, Veterinarian.class);
                startActivity(veteranIntent);

                return true;
            case R.id.logoutM:
                FirebaseAuth.getInstance().signOut();
                Intent signoutIntent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(signoutIntent);

                return true;
            case R.id.addcowM:
                Intent addcowIntent = new Intent(HomeActivity.this, AddCowActivity.class);
                startActivity(addcowIntent);

                return true;
            case R.id.cowM:
                Intent cowMarketIntent = new Intent(HomeActivity.this, CowActivity.class);
                startActivity(cowMarketIntent);

                return true;
            case R.id.alarmM:
                Intent alarmIntent = new Intent(HomeActivity.this, AlarmMain.class);
                startActivity(alarmIntent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
