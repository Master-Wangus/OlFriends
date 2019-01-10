package com.example.wesle.olfriends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ClickPostActivity extends AppCompatActivity {
    private ImageView PostImage;
    private TextView PostDescription;
    private Button DeletePostButton,EditPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_post);
    }

}
