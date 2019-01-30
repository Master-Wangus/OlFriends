package com.example.wesle.olfriends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageHelpActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GuideAdapter adapter;
    List<Guide> guideList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_help);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        guideList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        guideList.add(new Guide(1,
                "Message (Part 1)",
                "To start messaging a friend, click the Message button depicted by the arrow in the picture."
                ,
                R.drawable.message1));

        guideList.add(new Guide(2,
                "Message (Part 2)",
                "You will be brought to your friend list where you can select the friend you wish to message."
                ,
                R.drawable.message2));

        guideList.add(new Guide(3,
                "Message (Part 3)",
                "Select the Send Message option to start messaging your friend."
                ,
                R.drawable.message3));

        guideList.add(new Guide(4,
                "Message (Part 4)",
                "You can view your entire search history with this friend, as well as send messages to one another!"
                ,
                R.drawable.message4));

        adapter = new GuideAdapter(this, guideList);
        recyclerView.setAdapter(adapter);
    }
}
