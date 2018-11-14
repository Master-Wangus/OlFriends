package com.example.wesle.olfriends;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PersonProfileActivity extends AppCompatActivity {

    private TextView personName, personAge, personInterest;
    private Button btnSend, btnDecline;

    private FirebaseAuth mAuth;
    private DatabaseReference friendReqRef, userRef, friendRef;
    private String senderUserId, receiverUserId, current_state, saveCurrentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_profile);

        personName = (TextView)findViewById(R.id.person_name);
        personAge = (TextView)findViewById(R.id.person_age);
        personInterest = (TextView)findViewById(R.id.person_interest);
        btnSend = (Button)findViewById(R.id.btnSend);
        btnDecline = (Button)findViewById(R.id.btnDecline);
        current_state = "not_friends";

        mAuth = FirebaseAuth.getInstance();
        senderUserId = mAuth.getCurrentUser().getUid();

        receiverUserId = getIntent().getExtras().get("selected_uid").toString();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        friendReqRef = FirebaseDatabase.getInstance().getReference().child("Friend Requests");
        friendRef = FirebaseDatabase.getInstance().getReference().child("Friends");

        userRef.child(receiverUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String myProfileName = dataSnapshot.child("Name").getValue().toString();
                    String myProfileAge = dataSnapshot.child("Age").getValue().toString();
                    String myProfileInterest = dataSnapshot.child("Interest").getValue().toString();

                    personName.setText("Name: " + myProfileName);
                    personAge.setText("Age: " + myProfileAge);
                    personInterest.setText("Interest: " + myProfileInterest);

                    buttonValidation();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnDecline.setVisibility(View.INVISIBLE);
        btnDecline.setEnabled(false);

        if(!senderUserId.equals(receiverUserId)){
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnSend.setEnabled(false);
                    if (current_state.equals("not_friends")){
                        SendFriendRequest();
                    }
                    if (current_state.equals("request_sent")){
                        CancelFriendRequest();
                    }
                    if (current_state.equals("request_received")){
                        AcceptFriendRequest();
                    }
                    if (current_state.equals("friends")){
                        Unfriend();
                    }
                }
            });
        }
        else {
            btnDecline.setVisibility(View.INVISIBLE);
            btnSend.setVisibility(View.INVISIBLE);
        }
    }

    private void buttonValidation(){
        friendReqRef.child(senderUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(receiverUserId)){
                    String request_type = dataSnapshot.child(receiverUserId).child("request_type").getValue().toString();

                    if (request_type.equals("sent")){
                        current_state = "request_sent";
                        btnSend.setText("Cancel Friend Request");

                        btnDecline.setVisibility(View.INVISIBLE);
                        btnDecline.setEnabled(false);
                    }
                    else if (request_type.equals("received")){
                        current_state = "request_received";
                        btnSend.setText("Accept Friend Request");

                        btnDecline.setVisibility(View.VISIBLE);
                        btnDecline.setEnabled(true);

                        btnDecline.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CancelFriendRequest();
                            }
                        });
                    }
                }
                else
                {
                    friendRef.child(senderUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(receiverUserId)){
                                current_state = "friends";
                                btnSend.setText("Unfriend");

                                btnDecline.setVisibility(View.INVISIBLE);
                                btnDecline.setEnabled(false);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void SendFriendRequest(){
        friendReqRef.child(senderUserId).child(receiverUserId).child("request_type").setValue("sent")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    friendReqRef.child(receiverUserId).child(senderUserId).child("request_type").setValue("received")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        btnSend.setEnabled(true);
                                        current_state = "request_sent";
                                        btnSend.setText("Cancel Friend Request");

                                        btnDecline.setVisibility(View.INVISIBLE);
                                        btnDecline.setEnabled(false);
                                    }
                                }
                            });
                }
            }
        });
    }

    private void CancelFriendRequest(){
        friendReqRef.child(senderUserId).child(receiverUserId).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            friendReqRef.child(receiverUserId).child(senderUserId).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                btnSend.setEnabled(true);
                                                current_state = "not_friends";
                                                btnSend.setText("Send Friend Request");

                                                btnDecline.setVisibility(View.INVISIBLE);
                                                btnDecline.setEnabled(false);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void AcceptFriendRequest(){
        Calendar calender = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calender.getTime());

        friendRef.child(senderUserId).child(receiverUserId).child("Date").setValue(saveCurrentDate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            friendRef.child(receiverUserId).child(senderUserId).child("Date").setValue(saveCurrentDate)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        friendReqRef.child(senderUserId).child(receiverUserId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    friendReqRef.child(receiverUserId).child(senderUserId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                btnSend.setEnabled(true);
                                                                current_state = "friends";
                                                                btnSend.setText("Unfriend");

                                                                btnDecline.setVisibility(View.INVISIBLE);
                                                                btnDecline.setEnabled(false);
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }

                                }
                            });
                        }
                    }
                });
    }

    private void Unfriend(){
        friendRef.child(senderUserId).child(receiverUserId).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            friendRef.child(receiverUserId).child(senderUserId).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                btnSend.setEnabled(true);
                                                current_state = "not_friends";
                                                btnSend.setText("Send Friend Request");

                                                btnDecline.setVisibility(View.INVISIBLE);
                                                btnDecline.setEnabled(false);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}
