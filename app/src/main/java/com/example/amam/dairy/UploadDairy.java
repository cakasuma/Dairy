package com.example.amam.dairy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class UploadDairy extends AppCompatActivity {

    private EditText mPriceUpload;
    private TextView mQuantityProgress;
    private Button mUploadBut;
    private SeekBar mQuantitySlider;
    private FirebaseDatabase fireDat;
    private DatabaseReference myRef;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currUser;
    private String curname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_dairy);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();
        fireDat = FirebaseDatabase.getInstance();
        myRef = fireDat.getReference("markets");
        userRef = fireDat.getReference("users");
        mPriceUpload = (EditText) findViewById(R.id.price_market);
        mQuantityProgress = (TextView)findViewById(R.id.quantity_progress);
        mUploadBut = (Button)findViewById(R.id.uploadDairyBut);
        mQuantitySlider = (SeekBar)findViewById(R.id.quantity_slider);


        mQuantityProgress.setText("Quantity: "+mQuantitySlider.getProgress()+" L");

        mQuantitySlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressval, boolean fromUser) {

                mQuantityProgress.setText("Quantity: "+progressval+" L");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        mUploadBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Date currentDate = Calendar.getInstance().getTime();
                userRef.child(currUser.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Market market = new Market(currentDate.toString(), Integer.toString(mQuantitySlider.getProgress()), (String) dataSnapshot.child("phone").getValue(), (String) dataSnapshot.child("name").getValue(), currUser.getUid(), mPriceUpload.getText().toString());
                        myRef.push().setValue(market);
                        Toast.makeText(UploadDairy.this, "Dairy has been uploaded to the market, lasts 120 days...", Toast.LENGTH_LONG).show();
                        Intent homeintent = new Intent(UploadDairy.this, HomeActivity.class);
                        startActivity(homeintent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
                Intent homeintent = new Intent(UploadDairy.this, HomeActivity.class);
                startActivity(homeintent);
                finish();
                return true;
            case R.id.veterinarianM:
                Intent veteranIntent = new Intent(UploadDairy.this, Veterinarian.class);
                startActivity(veteranIntent);
                finish();
                return true;
            case R.id.logoutM:
                FirebaseAuth.getInstance().signOut();
                Intent signoutIntent = new Intent(UploadDairy.this, MainActivity.class);
                startActivity(signoutIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
