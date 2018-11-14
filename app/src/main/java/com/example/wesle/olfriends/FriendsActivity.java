package com.example.wesle.olfriends;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FriendsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText txtSearchInput;
    private ImageButton btnSearch, btnSearch2;

    private RecyclerView userList;

    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();

        userList = (RecyclerView)findViewById(R.id.search_list);
        userList.setHasFixedSize(true);
        userList.setLayoutManager(new LinearLayoutManager(this));

        btnSearch = (ImageButton)findViewById(R.id.btnSearch);
        btnSearch2 = (ImageButton)findViewById(R.id.btnSearch2);
        txtSearchInput = (EditText)findViewById(R.id.search_box);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchBoxInput = txtSearchInput.getText().toString();

                SearchFriends(searchBoxInput);
            }
        });

        btnSearch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchBoxInput = txtSearchInput.getText().toString();

                SearchInterests(searchBoxInput);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavView);
        /* Executed when nav bar is clicked */
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                /* Navigation methods executed based on case */
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent home = new Intent(FriendsActivity.this, HomePageActivity.class);
                        startActivity(home);
                        break;
                    case R.id.ic_settings:
                        Intent settings = new Intent(FriendsActivity.this, ProfileActivity.class);
                        startActivity(settings);
                        break;
                    case R.id.ic_logout:
                        mAuth.signOut();
                        finish();
                        Intent logout = new Intent(FriendsActivity.this, LoginActivity.class);
                        logout.setFlags(logout.FLAG_ACTIVITY_NEW_TASK | logout.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logout);
                        break;
                }

                return false;
            }
        });
    }

    private void SearchFriends(String searchBoxInput){
        Query searchQuery = userRef.orderByChild("Name").startAt(searchBoxInput).endAt(searchBoxInput + "\uf8ff");

        FirebaseRecyclerAdapter<FindFriends, FindFriendsViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<FindFriends, FindFriendsViewHolder>
                (
                        FindFriends.class,
                        R.layout.list_users,
                        FindFriendsViewHolder.class,
                        searchQuery
                ) {
            @Override
            protected void populateViewHolder(FindFriendsViewHolder viewHolder, FindFriends model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setAge(model.getAge());
            }
        };
        userList.setAdapter(firebaseRecyclerAdapter);
    }

    private void SearchInterests(String searchBoxInput){
        Query searchQuery = userRef.orderByChild("Interest").startAt(searchBoxInput).endAt(searchBoxInput + "\uf8ff");

        FirebaseRecyclerAdapter<FindFriends, FindFriendsViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<FindFriends, FindFriendsViewHolder>
                (
                        FindFriends.class,
                        R.layout.list_users,
                        FindFriendsViewHolder.class,
                        searchQuery
                ) {
            @Override
            protected void populateViewHolder(FindFriendsViewHolder viewHolder, FindFriends model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setAge(model.getAge());
            }
        };
        userList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class FindFriendsViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public FindFriendsViewHolder(View itemView){
            super(itemView);

            mView = itemView;
        }

        public void setName(String name){
            TextView myName = (TextView) mView.findViewById(R.id.txtName);
            myName.setText(name);
        }

        public void setAge(String age){
            TextView myAge =  (TextView) mView.findViewById(R.id.txtAge);
            myAge.setText(age);
        }
    }

}
