package com.example.wesle.olfriends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsHelpActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GuideAdapter adapter;
    List<Guide> guideList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_help);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        guideList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        guideList.add(new Guide(1,
                "Read News (Part 1)",
                "To start reading worldwide news, click the News button depicted by the arrow in the picture."
                ,
                R.drawable.news1));

        guideList.add(new Guide(2,
                "Read News (Psrt 2)",
                "You will be brought to a page listing the different news sources available worldwide." +
                        " Select a news source."
                ,
                R.drawable.news2));

        guideList.add(new Guide(2,
                "Read News (Psrt 3)",
                "The latest news will be displayed for your reading convenience!"
                ,
                R.drawable.news3));

        adapter = new GuideAdapter(this, guideList);
        recyclerView.setAdapter(adapter);
    }
}
