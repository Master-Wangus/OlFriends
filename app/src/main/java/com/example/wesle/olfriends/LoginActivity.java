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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText editTextEmail, editTextPassword;
    private Button btnLogIn;
    private ProgressDialog progressDialog;
    private TextView txtViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        btnLogIn = (Button)findViewById(R.id.btnLogIn);
        mAuth = FirebaseAuth.getInstance();
        txtViewRegister = (TextView)findViewById(R.id.txtViewSignUp);

        txtViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToRegister();
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
        {
            SendToHome();
        }
    }

    /* Method to sign the user in */
    private void loginUser(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            editTextEmail.setError("Please enter an email.");
            /* Direct focus on the textbox with the error */
            editTextEmail.requestFocus();
            return;
        }

        else if (TextUtils.isEmpty(password))
        {
            editTextPassword.setError("Please enter a password.");
            /* Direct focus on the textbox with the error */
            editTextPassword.requestFocus();
            return;
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextEmail.setError("Please enter a valid email!");
            /* Direct focus on the textbox with the error */
            editTextEmail.requestFocus();
            return;
        }

        else
        {
            progressDialog.setMessage("Logging In...");
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        SendToHome();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Successfully logged in!", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void SendToHome(){
        Intent home = new Intent(LoginActivity.this, HomePageActivity.class);
        home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(home);
        finish();
    }

    private void SendToRegister(){
        Intent register = new Intent(LoginActivity.this,SignupActivity.class);
        startActivity(register);
    }


}

