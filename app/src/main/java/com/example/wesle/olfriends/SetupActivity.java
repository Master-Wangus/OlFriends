package com.example.wesle.olfriends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SetupActivity extends AppCompatActivity {

    private EditText editTextName, editTextInterests;
    private Button btnSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        btnSetup = (Button)findViewById(R.id.btnSetup);
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextInterests = (EditText)findViewById(R.id.editTextInterests);
    }
}
