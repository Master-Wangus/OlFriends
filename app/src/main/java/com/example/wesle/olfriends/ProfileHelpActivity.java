package com.example.wesle.olfriends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfileHelpActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GuideAdapter adapter;
    List<Guide> guideList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_help);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        guideList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        guideList.add(new Guide(1,
                "View Your Profile (Part 1)",
                "To view your own profile, click the icon located at the left of the navigation bar as depicted by the " +
                        "arrow in the picture.",
                R.drawable.profile1));

        guideList.add(new Guide(2,
                "View Your Profile (Part 2)",
                "You will be able to successfully view your profile!",
                R.drawable.profile2));

        guideList.add(new Guide(3,
                "Update Profile (Part 1)",
                "To update your profile, click the icon second from the left of the navigation bar as depicted by the" +
                        " arrow in the picture.",
                R.drawable.profile3));

        guideList.add(new Guide(4,
                "Update Profile (Part 2)",
                "You will be able to edit your personal particulars and to update them, simply click the blue button labeled" +
                        " Update as shown in the picture.",
                R.drawable.profile4));

        guideList.add(new Guide(5,
                "View Friend's Profile (Part 1)",
                "To view a friend's profile, click the icon second from the right of the navigation bar as depicted by the" +
                        " arrow in the picture.",
                R.drawable.profile5));

        guideList.add(new Guide(6,
                "View Friend's Profile (Part 2)",
                "You will see a list of your friends here. Click on a friend to trigger an alert option.",
                R.drawable.profile6));

        guideList.add(new Guide(7,
                "View Friend's Profile (Part 3)",
                "Select the View Profile option.",
                R.drawable.profile7));

        guideList.add(new Guide(8,
                "View Friend's Profile (Part 4)",
                "You will be able to see the personal particulars of the selected friend!",
                R.drawable.profile8));

        adapter = new GuideAdapter(this, guideList);
        recyclerView.setAdapter(adapter);

    }
}
