package com.example.poppylove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText mPhone;
    private EditText mPass;
    private Button btnReg;
    private TextView mSignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registration();
    }

    private void registration(){

        mPhone = findViewById(R.id.PhoneID);
        mPass = findViewById(R.id.PassID);
        btnReg = findViewById(R.id.ResBID);
        mSignin = findViewById(R.id.HaveAccount);


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ErrorCheck = false;
                String phone = mPhone.getText().toString().trim();
                String pass = mPass.getText().toString().trim();

                if (TextUtils.isEmpty(phone)){
                    mPhone.setError("Phone its required");
                    ErrorCheck = true;
                }
                if(TextUtils.isEmpty(pass)){
                    mPass.setError("Password is required");
                    ErrorCheck = true;
                }

                //Exit if Error found
                if(ErrorCheck){
                    return;
                }


                //If no Error found

                //register
                FirebaseController.Register(phone, pass);
            }
        });

        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });


    }
}