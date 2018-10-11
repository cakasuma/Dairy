package com.example.amam.dairy;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CustomDialogAppointment extends Dialog implements View.OnClickListener {

    FirebaseDatabase database;
    DatabaseReference veteranshistory;
    DatabaseReference veterans;

    FirebaseAuth mAuth;
    FirebaseUser currUser;

    public Activity c;
    public Dialog d;
    private Button yes, no;
    private String pos;
    private Veteran mod;


    public CustomDialogAppointment(Activity a, String position, Veteran model) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.pos = position;
        this.mod = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom_dialog_appointment);

        database = FirebaseDatabase.getInstance();
        veteranshistory = database.getReference("appointments");
        veterans = database.getReference("veterans");
        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();
        yes = (Button) findViewById(R.id.btn_yes_app);
        no = (Button) findViewById(R.id.btn_no_app);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_yes_app:
                Date currentDate = Calendar.getInstance().getTime();
                Appointment apphistory = new Appointment(currentDate.toString(), mod.getName(), mod.getTitle(), mod.getPhone(), mod.getPrice());
                veteranshistory.child(currUser.getUid()).child(pos).setValue(apphistory);
                veterans.child(pos).child("status").setValue("rented");
                Toast.makeText(c, "Veterans will contact you within 4 hours", Toast.LENGTH_SHORT).show();
                Log.d("here", pos);
                break;
            case R.id.btn_no_app:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
