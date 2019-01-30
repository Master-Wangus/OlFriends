package com.example.wesle.olfriends;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class HelpActivity extends AppCompatActivity {

    private Button btnProfile, btnFriends, btnContent, btnMessage, btnNews;
    RecyclerView recyclerView;
    GuideAdapter adapter;
    List<Guide> guideList;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        btnProfile = (Button)findViewById(R.id.btnProfile);
        btnContent = (Button)findViewById(R.id.btnContent);
        btnFriends = (Button)findViewById(R.id.btnFriends);
        btnNews = (Button)findViewById(R.id.btnNews);
        btnMessage = (Button)findViewById(R.id.btnMessage);
        BottomNavigationView btmNavView = (BottomNavigationView)findViewById(R.id.bottomNavView);
        mAuth = FirebaseAuth.getInstance();

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(HelpActivity.this, ProfileHelpActivity.class);
                startActivity(home);
            }
        });

        btnFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(HelpActivity.this, FriendsHelpActivity.class);
                startActivity(home);
            }
        });

        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                /* Navigation methods executed based on case */
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent home = new Intent(HelpActivity.this, HomePageActivity.class);
                        startActivity(home);
                        break;
                    case R.id.ic_myprofile:
                        Intent view = new Intent(HelpActivity.this, ProfileActivity.class);
                        startActivity(view);
                        break;
                    case R.id.ic_updateprofile:
                        Intent update = new Intent(HelpActivity.this, UpdateProfileActivity.class);
                        startActivity(update);
                        break;
                    case R.id.ic_friendlist:
                        Intent friendlist = new Intent(HelpActivity.this, FriendListActivity.class);
                        startActivity(friendlist);
                        break;
                    case R.id.ic_logout:
                        mAuth.signOut();
                        finish();
                        Intent logout = new Intent(HelpActivity.this, LoginActivity.class);
                        logout.setFlags(logout.FLAG_ACTIVITY_NEW_TASK | logout.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logout);
                        break;
                }
                return false;

            }
        });
    }
}
