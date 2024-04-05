package com.example.poppylove;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpdateUserActivity extends AppCompatActivity {

    EditText nameInput;
    String Name;
    String Bio;
    String PictureData;
    Button submitButton;

    ImageView imageView;

    FloatingActionButton button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);


        button = findViewById(R.id.floatingActionButton);
        imageView = findViewById(R.id.profileID);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(UpdateUserActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        Log.d("STARTED", "Started UpdateUser");

        Bundle bundle = getIntent().getExtras();
        String phoneID = bundle.getString("PhoneID");


        nameInput = findViewById(R.id.NameInput);
        submitButton = findViewById(R.id.SubmitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = nameInput.getText().toString();
                Bio = "Bio";
                PictureData = "PICTUREDATA";
                FirebaseController.CreateProfile(phoneID, Name, Bio, PictureData);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        imageView.setImageURI(uri);
    }
}