package com.example.wesle.olfriends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContentHelpActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GuideAdapter adapter;
    List<Guide> guideList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_help);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        guideList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        guideList.add(new Guide(1,
                "Content (Part 1)",
                "To access the content page, click the Content button as depicted by the arrow in the picture."
                ,
                R.drawable.content1));

        guideList.add(new Guide(2,
                "Content (Part 12)",
                "You will be able to access the posts made by all existing users of the application!"
                ,
                R.drawable.content2));

        guideList.add(new Guide(3,
                "Liking a Post",
                "To like a post, click the Heart icon as shown by the arrow. To unlike a post, simply click the Heart icon again."
                ,
                R.drawable.content3));

        guideList.add(new Guide(4,
                "Comment on a Post (Part 1)",
                "To comment on a desired post, click the icon indicated by the arrow in the picture,"
                ,
                R.drawable.content4));

        guideList.add(new Guide(5,
                "Comment on a Post (Part 2)",
                "You will see all the comments made on the post, as well as post your own comment here!"
                ,
                R.drawable.content5));

        guideList.add(new Guide(6,
                "Post content (Part 1)",
                "To start posting content, click the icon located at the bottom right as shown by the arrow" +
                        " in the picture."
                ,
                R.drawable.content6));

        guideList.add(new Guide(7,
                "Post Content (Part 2)",
                "Select an image from your gallery, as well as writing your caption, before clicking the button to" +
                        " successfully post your content!"
                ,
                R.drawable.content7));

        adapter = new GuideAdapter(this, guideList);
        recyclerView.setAdapter(adapter);
    }
}
