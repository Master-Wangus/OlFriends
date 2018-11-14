package com.example.wesle.olfriends;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class FriendListActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        mAuth = FirebaseAuth.getInstance();
        BottomNavigationView btmNavView = (BottomNavigationView)findViewById(R.id.bottomNavView);
        Menu menu = btmNavView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                /* Navigation methods executed based on case */
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent home = new Intent(FriendListActivity.this, HomePageActivity.class);
                        startActivity(home);
                        break;
                    case R.id.ic_myprofile:
                        Intent view = new Intent(FriendListActivity.this, ProfileActivity.class);
                        startActivity(view);
                        break;
                    case R.id.ic_updateprofile:
                        Intent update = new Intent(FriendListActivity.this, UpdateProfileActivity.class);
                        startActivity(update);
                        break;
                    case R.id.ic_friendlist:
                        break;
                    case R.id.ic_logout:
                        mAuth.signOut();
                        finish();
                        Intent logout = new Intent(FriendListActivity.this, LoginActivity.class);
                        logout.setFlags(logout.FLAG_ACTIVITY_NEW_TASK | logout.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logout);
                        break;
                }
                return false;
            }
        });
    }
}
