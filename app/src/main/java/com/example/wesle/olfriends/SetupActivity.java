package com.example.wesle.olfriends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SetupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText editTextName, editTextAge;
    private Spinner ddlInterests;
    private Button btnSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        ddlInterests = (Spinner)findViewById(R.id.ddlInterests);
        btnSetup = (Button)findViewById(R.id.btnSetup);
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextAge = (EditText)findViewById(R.id.editTextAge);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Interests, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddlInterests.setAdapter(adapter);
        ddlInterests.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "You have selected " + selected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
