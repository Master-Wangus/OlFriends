package com.example.wesle.olfriends;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private TextView profileName, profileAge, profileInterest, profileTitle;

    private DatabaseReference profileRef;

    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        profileRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
        profileName = (TextView)findViewById(R.id.profile_name);
        profileAge = (TextView)findViewById(R.id.profile_age);
        profileInterest = (TextView)findViewById(R.id.profile_interest);
        profileTitle = (TextView)findViewById(R.id.txtTitle);
        BottomNavigationView btmNavView = (BottomNavigationView)findViewById(R.id.bottomNavView);
        Menu menu = btmNavView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                /* Navigation methods executed based on case */
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent home = new Intent(ProfileActivity.this, HomePageActivity.class);
                        startActivity(home);
                        break;
                    case R.id.ic_myprofile:
                        break;
                    case R.id.ic_updateprofile:
                        Intent update = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
                        startActivity(update);
                        break;
                    case R.id.ic_friendlist:
                        Intent friendlist = new Intent(ProfileActivity.this, FriendListActivity.class);
                        startActivity(friendlist);
                        break;
                    case R.id.ic_logout:
                        mAuth.signOut();
                        finish();
                        Intent logout = new Intent(ProfileActivity.this, LoginActivity.class);
                        logout.setFlags(logout.FLAG_ACTIVITY_NEW_TASK | logout.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logout);
                        break;
                }
                return false;
            }
        });

        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String profilename = dataSnapshot.child("Name").getValue().toString();
                    String profileage = dataSnapshot.child("Age").getValue().toString();
                    String profileinterest = dataSnapshot.child("Interest").getValue().toString();

                    profileName.setText("Displayed Name:  " + profilename);
                    profileAge.setText("Displayed Age:  " + profileage);
                    profileInterest.setText("Displayed Interest:  " + profileinterest);
                    profileTitle.setText("Welcome " + profilename);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
