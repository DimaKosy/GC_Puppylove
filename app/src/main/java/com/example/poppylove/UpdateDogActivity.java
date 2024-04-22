package com.example.poppylove;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpdateDogActivity extends AppCompatActivity {

    EditText nameInput;
    EditText bioInput;
    String Name;
    String Bio;

    ImageView imageView;
    Uri uri;
    String phoneID;

    ImageController imageController;
    FloatingActionButton button;

    Button DogsubmitButton;

    Spinner spinner;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dog);

        Bundle bundle = getIntent().getExtras();
        this.phoneID = bundle.getString("PhoneID");

        imageController = new ImageController(this);

        nameInput = findViewById(R.id.DogNameInput);
        bioInput = findViewById(R.id.DogBIOinput);
        imageView = findViewById(R.id.DogprofileID);
        button = findViewById(R.id.DogFloatButtonID);
        DogsubmitButton = findViewById(R.id.SubmitButtonDog);
        spinner = findViewById(R.id.SpinnerID);

        String[] Dogs = {"Select", "Dog1", "Dog2","Dog3"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Dogs);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(UpdateDogActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });


        DogsubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Name = nameInput.getText().toString();
                Bio = bioInput.getText().toString();

                if(Name.isEmpty()){
                    return;
                }

                if(Bio.isEmpty()){
                    return;
                }

                if(uri == null){
                    return;
                }

                FirebaseController.CreateDogProfile(phoneID,1,Name,Bio,null,null,null);
                imageController.uploadDogImage(phoneID,1,uri);
//                Intent intent = new Intent(UpdateDogActivity.this,SwipeActivity.class);
//                startActivity(intent);

                //end Activity

                Bundle bundle = new Bundle();
                bundle.putString("PhoneID",phoneID);
                bundle.putString("URI",uri.toString());

                Intent result = new Intent();

                result.putExtras(bundle);

                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        imageView.setImageURI(uri);
    }
}