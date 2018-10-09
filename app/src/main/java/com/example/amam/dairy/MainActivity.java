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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button mLogBut;
    private TextView mRegLogBut;
    private EditText mLogEmail;
    private EditText mLogPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mRegLogBut = (TextView)findViewById(R.id.policamistake);
        mLogBut = (Button)findViewById(R.id.logbut);
        mLogEmail = (EditText)findViewById(R.id.logemail);
        mLogPass = (EditText)findViewById(R.id.logpass);

        mLogBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(mLogEmail.getText().toString(), mLogPass.getText().toString());
            }
        });

        mRegLogBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(MainActivity.this, Register.class);
                startActivity(register);
                finish();
            }
        });
    }

    public void signIn(String email, String password){

        if (!validateForm()) {
            return;
        }

        Toast.makeText(MainActivity.this, "Loading...",
                Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mLogEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mLogEmail.setError("Required.");
            valid = false;
        } else {
            mLogEmail.setError(null);
        }

        String password = mLogPass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mLogPass.setError("Required.");
            valid = false;
        } else {
            mLogPass.setError(null);
        }

        return valid;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser!=null){
            Intent home = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(home);
            Toast.makeText(MainActivity.this, "Login Successful",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
