package com.example.wesle.olfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnLogout;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Users");

        btnLogout = (Button)findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SendtoLogin();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {
            SendtoLogin();
        }
        else
        {
            checkUserExist();
        }
    }

    private void checkUserExist(){
        final String current_user_id = mAuth.getCurrentUser().getUid();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /* User authenticated but not recorded in database */
                if(!dataSnapshot.hasChild(current_user_id))
                {
                    SendToSetup();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void SendtoLogin(){
        Intent login = new Intent(HomePageActivity.this, LoginActivity.class);
        login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(login);
        finish();
    }

    private void SendToSetup(){
        Intent setup = new Intent(HomePageActivity.this, SetupActivity.class);
        setup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setup);
        finish();
    }
}
