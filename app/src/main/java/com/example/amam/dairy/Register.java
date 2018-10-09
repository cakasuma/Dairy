package com.example.amam.dairy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private EditText mRegName;
    private EditText mRegEmail;
    private DatabaseReference myRef;
    private EditText mRegPass;
    private Button mRegBut;
    private TextView mToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        mRegName = (EditText)findViewById(R.id.regname);
        mRegEmail = (EditText)findViewById(R.id.regemail);
        mRegPass = (EditText)findViewById(R.id.regpass);
        mRegBut = (Button) findViewById(R.id.regbut);
        mToLogin = (TextView) findViewById(R.id.tologin);

        mToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginintent = new Intent(Register.this, MainActivity.class);
                startActivity(loginintent);
                finish();
            }
        });

        mRegBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(mRegEmail.getText().toString(), mRegPass.getText().toString());

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(String email, String password) {
        Log.d("TAG", "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        Toast.makeText(Register.this, "Loading...",
                Toast.LENGTH_SHORT).show();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mRegEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mRegEmail.setError("Required.");
            valid = false;
        } else {
            mRegEmail.setError(null);
        }

        String password = mRegPass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mRegPass.setError("Required.");
            valid = false;
        } else {
            mRegPass.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser!=null){
            insertDataAccount();
            Intent home = new Intent(Register.this, HomeActivity.class);
            startActivity(home);
            Toast.makeText(Register.this, "Registration Successful",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void insertDataAccount(){
        FirebaseUser currUser = mAuth.getCurrentUser();
        Map<String,String> datas = new HashMap<>();
        datas.put("name", mRegName.getText().toString());
        datas.put("email", mRegEmail.getText().toString());
        myRef.child(currUser.getUid()).setValue(datas);
    }


}
