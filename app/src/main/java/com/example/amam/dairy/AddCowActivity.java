package com.example.amam.dairy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
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

public class AddCowActivity extends AppCompatActivity {
    private TextView mQuantityProgress;
    private Button mUploadBut;
    private SeekBar mQuantitySlider;
    private FirebaseDatabase fireDat;
    private DatabaseReference myRef;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currUser;
    private EditText description;
    Spinner age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cow);

        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();
        fireDat = FirebaseDatabase.getInstance();
        myRef = fireDat.getReference("markets");
        userRef = fireDat.getReference("users");
        mQuantitySlider = (SeekBar)findViewById(R.id.quantity_slider);
        mQuantityProgress = (TextView)findViewById(R.id.quantity_progress);
        mUploadBut = (Button)findViewById(R.id.uploadDairyBut);
        age = (Spinner)findViewById(R.id.spinner1);
        description = (EditText) findViewById(R.id.description);


        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        mQuantityProgress.setText("Age: "+mQuantitySlider.getProgress()+" Year(s)");

        mQuantitySlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressval, boolean fromUser) {

                mQuantityProgress.setText("Age: "+progressval+" Year(s)");
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
                userRef.child(currUser.getUid()).child("name").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Cow cow = new Cow( Integer.toString(mQuantitySlider.getProgress()), age.getSelectedItem().toString(), description.getText().toString());
                        myRef = fireDat.getReference().child("users").child(mAuth.getUid().toString()).child("cows");
                        myRef.push().setValue(cow);
                        Toast.makeText(AddCowActivity.this, "Cow added...", Toast.LENGTH_LONG).show();
                        Intent cowintent = new Intent(AddCowActivity.this, CowActivity.class);
                        startActivity(cowintent);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.marketM:
                Intent homeIntent = new Intent(AddCowActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                return true;
            case R.id.veterinarianM:
                Intent veteranIntent = new Intent(AddCowActivity.this, Veterinarian.class);
                startActivity(veteranIntent);
                return true;

            case R.id.logoutM:
                FirebaseAuth.getInstance().signOut();
                Intent signoutIntent = new Intent(AddCowActivity.this, MainActivity.class);
                startActivity(signoutIntent);

                return true;
            case R.id.addcowM:


                return true;
            case R.id.cowM:
                Intent cowMarketIntent = new Intent(AddCowActivity.this, CowActivity.class);
                startActivity(cowMarketIntent);

                return true;
            case R.id.alarmM:
                Intent alarmIntent = new Intent(AddCowActivity.this, AlarmMain.class);
                startActivity(alarmIntent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
