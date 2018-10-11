package com.example.amam.dairy;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomDialogCow extends Dialog implements View.OnClickListener{
    FirebaseDatabase database;
    DatabaseReference dataref;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    public Activity c;
    public Dialog d;
    public Button yes, no;
    private String pos;


    public CustomDialogCow(Activity a, String position) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.pos = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom_dialog_cow);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        dataref = database.getReference("users").child(mUser.getUid().toString()).child("cows");
        yes = (Button) findViewById(R.id.btn_yes_cow);
        no = (Button) findViewById(R.id.btn_no_cow);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes_cow:
                dataref.child(pos).removeValue();
                Toast.makeText(c, "Remove Successful ", Toast.LENGTH_SHORT).show();
                Log.d("here", pos);
                break;
            case R.id.btn_no_cow:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}