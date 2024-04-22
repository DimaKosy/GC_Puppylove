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
import android.widget.TextView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpdateUserActivity extends AppCompatActivity {

    private int MAX_DOGS = 3;

    EditText nameInput;
    EditText bioInput;
    String Name;
    String Bio;
    Button submitButton;

    ImageController imageController;
    ImageView imageView;

    ImageView dogPic[];
    TextView dogName[];
    TextView dogBreed[];
    Uri uri;

    FloatingActionButton button;

    Button LinkDogpage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);



        button = findViewById(R.id.floatingActionButton);
        imageView = findViewById(R.id.profileID);

        dogPic = new ImageView[MAX_DOGS];
        dogName = new TextView[MAX_DOGS];
        dogBreed = new TextView[MAX_DOGS];

        dogPic[0] = findViewById(R.id.dogPic);
        dogName[0] = findViewById(R.id.dogName);
        dogBreed[0] = findViewById(R.id.dogBreed);

        imageController = new ImageController(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(UpdateUserActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(540, 720)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        Log.d("STARTED", "Started UpdateUser");

        Bundle bundle = getIntent().getExtras();
        String phoneID = bundle.getString("PhoneID");


        nameInput = findViewById(R.id.NameInput);
        bioInput = findViewById(R.id.bioInput);
        submitButton = findViewById(R.id.SubmitButton);
        LinkDogpage = findViewById(R.id.EditDogButtonID);

        LinkDogpage.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("PhoneID",phoneID);

                Intent intent = new Intent(UpdateUserActivity.this,UpdateDogActivity.class);
                intent.putExtras(bundle);

                startActivityForResult(intent, 2001);
            }
        }));{

        }
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Name = nameInput.getText().toString();
                Bio = bioInput.getText().toString();
                boolean err = false;

                if(Name.isEmpty()){
                    err = true;
                }

                if(Bio.isEmpty()){
                    err = true;
                }

                if(uri == null){
                    err = true;
                }

                if(err == true){
                    return;
                }

                //creates the profile and uploads the image
                FirebaseController.CreateProfile(phoneID, Name, Bio);

                if(uri != null) {
                    imageController.uploadProfileImage(phoneID, uri);
                }

                Bundle bundle = new Bundle();
                bundle.putString("PhoneID",phoneID);

                Intent intent = new Intent(UpdateUserActivity.this,SwipeActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("RESULT", requestCode +":"+ resultCode);

        switch(requestCode){
            case 2404:
                uri = data.getData();
                imageView.setImageURI(uri);
            break;
            case 2001:
                assert data != null;
                uri = Uri.parse(data.getStringExtra("URI"));

                dogName[0].setText(data.getStringExtra("DOG_NAME"));
                dogBreed[0].setText(data.getStringExtra("DOG_BREED"));
                dogPic[0].setImageURI(uri);
            break;

        }

    }
}