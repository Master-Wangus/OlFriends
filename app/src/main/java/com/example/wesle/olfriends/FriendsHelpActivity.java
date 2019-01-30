package com.example.wesle.olfriends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FriendsHelpActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GuideAdapter adapter;
    List<Guide> guideList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_help);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        guideList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        guideList.add(new Guide(1,
                "Friend Search (Part 1)",
                "To begin searching for friends, click the Friend Search button as depicted by the arrow in the picture."
                ,
                R.drawable.friend1));

        guideList.add(new Guide(2,
                "Friend Search (Part 2)",
                "You will be brought to the search page. The Red arrow allows you to search by Name, " +
                        "Blue arrow allows you to search by Interests.",
                R.drawable.friend2));

        guideList.add(new Guide(3,
                "Friend Search (Part 3)",
                "Search results will be displayed with the names and ages of the users.",
                R.drawable.friend3));

        guideList.add(new Guide(4,
                "Adding a Friend (Part 1)",
                "To add a friend, select the user you want to befriend from the search results.",
                R.drawable.friend4));

        guideList.add(new Guide(5,
                "Adding a Friend (Part 2)",
                "You will be brought to his profile where you can send a friend request.",
                R.drawable.friend5));

        guideList.add(new Guide(6,
                "Adding a Friend (Part 3)",
                "You ware able to cancel your friend request should you no longer wish to befriend the user.",
                R.drawable.friend6));

        guideList.add(new Guide(7,
                "Accept/Reject Friend Requests",
                "You will be able to accept/reject friend requests sent to you here.",
                R.drawable.friend7));

        guideList.add(new Guide(8,
                "Unfriend a User",
                "You can unfriend the user should you no longer want to be friends with him.",
                R.drawable.friend8));

        adapter = new GuideAdapter(this, guideList);
        recyclerView.setAdapter(adapter);
    }
}
