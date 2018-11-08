package com.example.wesle.olfriends;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(this);

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btnSignUp).setOnClickListener(this);
        findViewById(R.id.txtViewSignUp).setOnClickListener(this);
    }

    /* Method to register the user */
    private void registerUser(){
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
        if(password.length()<6){
            /* Set error message */
            editTextPassword.setError("Password must contain at least 6 characters!");
            /* Direct focus on the textbox with the error */
            editTextPassword.requestFocus();
            return;
        }

        /* Display dialog upon clicking sign up button */
        progressDialog.setMessage("Registering...");
        progressDialog.show();

        /* Method executed when task is completed */
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    /* Automatically signs user in the HomePageActivity.class */
                    startActivity(new Intent(SignupActivity.this, HomePageActivity.class));
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Successfully Registered!", Toast.LENGTH_SHORT).show();
                }else{
                    /* Condition to check if email is already in use */
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Email is already in use!", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnSignUp:
                registerUser();
                break;
            case R.id.txtViewSignUp:
                finish();
                /* Navigates to LoginActivity.class */
                startActivity(new Intent(this, LoginActivity.class));
                break;

        }
    }
}