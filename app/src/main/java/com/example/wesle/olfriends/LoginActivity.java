package com.example.wesle.olfriends;

import com.google.firebase.auth.FirebaseAuth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    EditText editTextEmail, editTextPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.txtViewSignUp).setOnClickListener(this);
        findViewById(R.id.btnLogIn).setOnClickListener(this);
    }

    /* Method to sign the user in */
    private void LoginUser(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        /* Method to execute when email field is empty */
        if(email.isEmpty()){
            /* Set error message */
            editTextEmail.setError("Email required!");
            /* Direct focus on the textbox with the error */
            editTextEmail.requestFocus();
            return;
        }

        /* Method to execute when password field is empty */
        if(password.isEmpty()){
            /* Set error message */
            editTextPassword.setError("Password required!");
            /* Direct focus on the textbox with the error */
            editTextPassword.requestFocus();
            return;
        }

        /* Method to execute when email is invalid */
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            /* Set error message */
            editTextEmail.setError("Please enter a valid email!");
            /* Direct focus on the textbox with the error */
            editTextEmail.requestFocus();
            return;
        }

        /* Method to execute when password length is too short */
        if(password.length()<8){
            /* Set error message */
            editTextPassword.setError("Password must contain at least 8 characters!");
            /* Direct focus on the textbox with the error */
            editTextPassword.requestFocus();
            return;
        }

        /* Display dialog upon clicking sign up button */
        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    /* Ends activity so cannot navigate by pressing back from HomePageActivity.class */
                    finish();
                    progressDialog.dismiss();
                    Intent i = new Intent(LoginActivity.this, HomePageActivity.class);
                    /* Method to clear all activities stacked on top of HomePageActivity */
                    i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                    /* Signs the user in to HomePageActivity.class */
                    startActivity(i);
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*Method to execute when user is logged in*/
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, HomePageActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.txtViewSignUp:
                finish();
                startActivity(new Intent(this, SignupActivity.class));
                break;
            case R.id.btnLogIn:
                LoginUser();
                break;
        }
    }

}

