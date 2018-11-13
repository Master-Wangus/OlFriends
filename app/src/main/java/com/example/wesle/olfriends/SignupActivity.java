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
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private TextView txtViewLogin;
    private Button btnSignUp;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(this);

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        mAuth = FirebaseAuth.getInstance();
        txtViewLogin = (TextView)findViewById(R.id.txtViewLogIn);

        txtViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToLogin();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
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

    private void SendToLogin(){
        Intent login = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(login);
    }

    /* Method to register the user */
    private void registerUser(){
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

        else if (password.length()<8)
        {
            editTextPassword.setError("Password must contain at least 8 characters!");
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
            progressDialog.setMessage("Registering...");
            progressDialog.show();
            /* Method executed when task is completed */
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        SendToSetup();
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
    }

    private void SendToSetup()
    {
        Intent setup = new Intent(SignupActivity.this, SetupActivity.class);
        setup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setup);
        finish();
    }

    private void SendToHome(){
        Intent home = new Intent(SignupActivity.this, HomePageActivity.class);
        home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(home);
        finish();
    }
}