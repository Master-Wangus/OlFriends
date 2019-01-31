package com.example.wesle.olfriends;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseAuth mAuth;
    private EditText myName, myAge;
    private Spinner myInterest;
    private Button btnUpdate;
    private DatabaseReference updateUserRef;
    private String currentUserId;
    private CircleImageView profileimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        updateUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
        myInterest = (Spinner)findViewById(R.id.my_interest);
        myName = (EditText)findViewById(R.id.my_name);
        myAge = (EditText)findViewById(R.id.my_age);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        profileimage = (CircleImageView)findViewById(R.id.update_profile_image);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Interests, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myInterest.setAdapter(adapter);
        myInterest.setOnItemSelectedListener(this);

        BottomNavigationView btmNavView = (BottomNavigationView)findViewById(R.id.bottomNavView);
        Menu menu = btmNavView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                /* Navigation methods executed based on case */
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent home = new Intent(UpdateProfileActivity.this, HomePageActivity.class);
                        startActivity(home);
                        break;
                    case R.id.ic_myprofile:
                        Intent view = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
                        startActivity(view);
                        break;
                    case R.id.ic_updateprofile:
                        break;
                    case R.id.ic_friendlist:
                        Intent friendlist = new Intent(UpdateProfileActivity.this, FriendListActivity.class);
                        startActivity(friendlist);
                        break;
                    case R.id.ic_logout:
                        mAuth.signOut();
                        finish();
                        Intent logout = new Intent(UpdateProfileActivity.this, LoginActivity.class);
                        logout.setFlags(logout.FLAG_ACTIVITY_NEW_TASK | logout.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logout);
                        break;
                }
                return false;
            }
        });

        updateUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String name = dataSnapshot.child("Name").getValue().toString();
                    String age = dataSnapshot.child("Age").getValue().toString();
                    String image = dataSnapshot.child("profileimage").getValue().toString();

                    myName.setText(name);
                    myAge.setText(age);
                    Picasso.with(UpdateProfileActivity.this).load(image).placeholder(R.drawable.profile).into(profileimage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SendToSetupPic();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountInfoValidate();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "You have selected " + selected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void accountInfoValidate(){
        String myname = myName.getText().toString();
        String myage = myAge.getText().toString();
        String myinterest = myInterest.getSelectedItem().toString();

        if(TextUtils.isEmpty(myname))
        {
            myName.setError("Please enter your name.");
            myName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(myage))
        {
            myAge.setError("Please enter your age.");
            myAge.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(myinterest))
        {
            Toast.makeText(this,"Please choose an interest.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            updateAccountInfo(myname, myage, myinterest);
        }
    }

    private void updateAccountInfo(String myname, String myage, String myinterest){
        HashMap userMap = new HashMap();
        userMap.put("Name", myname);
        userMap.put("Age", myage);
        userMap.put("Interest", myinterest);
        updateUserRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    SendToHome();
                    Toast.makeText(UpdateProfileActivity.this, "Account Successfully Updated", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(UpdateProfileActivity.this, "Error, Update failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void SendToHome(){
        Intent home = new Intent(UpdateProfileActivity.this, HomePageActivity.class);
        home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(home);
        finish();
    }
    private void SendToSetupPic(){
        Intent pic = new Intent(UpdateProfileActivity.this, SetupPic.class);
        pic.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(pic);
        finish();
    }
}
