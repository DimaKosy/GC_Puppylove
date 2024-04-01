package com.example.poppylove;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateUserActivity extends AppCompatActivity {

    EditText nameInput;
    String Name;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        Log.d("STARTED", "Started UpdateUser");

        Bundle bundle = getIntent().getExtras();
        String phoneID = bundle.getString("PhoneID");


        nameInput = findViewById(R.id.NameInput);
        submitButton = findViewById(R.id.SubmitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Name = nameInput.getText().toString();
                 FirebaseController.CreateProfile(phoneID, Name);

            }
        });
    }
}