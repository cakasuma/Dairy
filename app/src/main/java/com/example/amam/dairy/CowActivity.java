package com.example.amam.dairy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CowActivity extends AppCompatActivity {


    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Cow, CowViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference tables;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cow);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        tables = database.getReference("users").child(mUser.getUid().toString()).child("cows");





        recyclerView = (RecyclerView)findViewById(R.id.marketrecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Query queries = tables.orderByChild("age");
        queries.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                }
                else{

                   }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new FirebaseRecyclerAdapter<Cow, CowViewHolder>(
                Cow.class,
                R.layout.card_cow,
                CowViewHolder.class,
                tables.orderByChild("age")
        ) {
            @Override
            protected void populateViewHolder(CowViewHolder viewHolder, Cow model, int position) {
                viewHolder.txtCowId.setText("ID: "+adapter.getRef(position).getKey());
                viewHolder.txtCowAge.setText("Age: "+ model.getAge());
                viewHolder.txtCowGender.setText("Gender: "+model.getGender());
                viewHolder.txtCowDescription.setText("Description: "+model.getDescription());
            }
        };
        recyclerView.setAdapter(adapter);


        RecyclerView recyclerView = findViewById(R.id.marketrecycler);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(CowActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        CustomDialogCow cdd=new CustomDialogCow(CowActivity.this, adapter.getRef(position).getKey().toString());
                        cdd.show();
                        adapter.notifyDataSetChanged();
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
                Intent homeIntent = new Intent(CowActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                return true;
            case R.id.veterinarianM:
                Intent veteranIntent = new Intent(CowActivity.this, Veterinarian.class);
                startActivity(veteranIntent);
                return true;

            case R.id.logoutM:
                FirebaseAuth.getInstance().signOut();
                Intent signoutIntent = new Intent(CowActivity.this, MainActivity.class);
                startActivity(signoutIntent);

                return true;
            case R.id.addcowM:
                Intent addcowIntent = new Intent(CowActivity.this, AddCowActivity.class);
                startActivity(addcowIntent);

                return true;
            case R.id.cowM:


                return true;
            case R.id.alarmM:
                Intent alarmIntent = new Intent(CowActivity.this, AlarmMain.class);
                startActivity(alarmIntent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
