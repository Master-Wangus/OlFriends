package com.example.wesle.olfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PagesAdapter adapter;
    List<Pages> pagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        BottomNavigationView btmNavView = (BottomNavigationView)findViewById(R.id.bottomNavView);
        Menu menu = btmNavView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        //initializing the productlist
        pagesList = new ArrayList<>();


        //adding some items to our list
        pagesList.add(
                new Pages(
                        "Message"));
        pagesList.add(
                new Pages(
                        "Friends"));
        pagesList.add(
                new Pages(
                        "Contents"));
        pagesList.add(
                new Pages(
                        "News"));
        pagesList.add(
                new Pages(
                        "Events"));
        adapter = new PagesAdapter(this,pagesList);
        recyclerView.setAdapter(adapter);

        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                /* Navigation methods executed based on case */
                switch (item.getItemId()){
                    case R.id.ic_home:
                        break;
                    case R.id.ic_myprofile:
                        Intent view = new Intent(HomePageActivity.this, ProfileActivity.class);
                        startActivity(view);
                        break;
                    case R.id.ic_updateprofile:
                        Intent update = new Intent(HomePageActivity.this, UpdateProfileActivity.class);
                        startActivity(update);
                        break;
                    case R.id.ic_friendlist:
                        Intent friendlist = new Intent(HomePageActivity.this, FriendListActivity.class);
                        startActivity(friendlist);
                        break;
                    case R.id.ic_logout:
                        mAuth.signOut();
                        finish();
                        Intent logout = new Intent(HomePageActivity.this, LoginActivity.class);
                        logout.setFlags(logout.FLAG_ACTIVITY_NEW_TASK | logout.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logout);
                        break;
                }
                return false;

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
