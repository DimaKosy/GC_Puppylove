package com.example.poppylove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

                String phone = mPhone.getText().toString().trim();
                String pass = mPass.getText().toString().trim();

                if (TextUtils.isEmpty(phone)){
                    mPhone.setError("Phone its required");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    mPass.setError("Password is required");
                }

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