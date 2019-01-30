package com.example.wesle.olfriends;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText editTextName, editTextAge;
    private Spinner ddlInterests;
    private Button btnSetup, btnNextPage;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private StorageReference UserProfileImageRef;
    private ProgressDialog progressDialog;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        ddlInterests = (Spinner)findViewById(R.id.ddlInterests);
        btnSetup = (Button)findViewById(R.id.btnSetup);
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextAge = (EditText)findViewById(R.id.editTextAge);
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
        progressDialog = new ProgressDialog(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Interests, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddlInterests.setAdapter(adapter);
        ddlInterests.setOnItemSelectedListener(this);
        btnSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setup();
            }
        });
    }


    private void Setup(){
        String name = editTextName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String interest = ddlInterests.getSelectedItem().toString();

        if(TextUtils.isEmpty(name))
        {
            editTextName.setError("Please enter your name.");
            editTextName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(age))
        {
            editTextAge.setError("Please enter your age.");
            editTextAge.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(interest))
        {
            Toast.makeText(this,"Please choose an interest.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressDialog.setMessage("Setting up...");
            progressDialog.show();

            HashMap userMap = new HashMap();
            userMap.put("Name", name);
            userMap.put("Age", age);
            userMap.put("Interest", interest);
            ref.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful())
                    {
                        NextPage();
                        progressDialog.dismiss();
                        Toast.makeText(SetupActivity.this, "Your Account Is Created Successfully!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        progressDialog.dismiss();
                        Toast.makeText(SetupActivity.this,"Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "You have selected " + selected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void NextPage(){
        Intent home = new Intent(SetupActivity.this, SetupPic.class);
        home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(home);
        finish();
    }
}
