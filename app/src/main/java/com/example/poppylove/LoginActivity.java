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

public class LoginActivity extends AppCompatActivity {

    private EditText mPhone;
    private EditText mPass;
    private Button BtmLogin;

    private TextView mSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://gc-puppylove-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, login");

        login();
    }


    private void login(){
        mPhone = findViewById(R.id.phoID);
        mPass = findViewById(R.id.PasswordID);
        BtmLogin = findViewById(R.id.btnID);
        mSignup = findViewById(R.id.signupID);

        BtmLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = mPhone.getText().toString().trim();
                String password = mPass.getText().toString().trim();

                if (TextUtils.isEmpty(phone)) {
                    mPhone.setError("Phone number its required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPass.setError("Personal password its required");
                    return;
                }

                //Logins in if details match
                /*LoggedIn values
                 * 2 success but profile incomplete
                 * 1 success
                 * -1 failure incorrect password
                 * -2 failure not registered
                 */

                Intent intent;
                Bundle bundle = new Bundle();
                bundle.putString("PhoneID",phone);

                switch (FirebaseController.login(phone, password)){
                    case 2:
                        intent = new Intent(getApplicationContext(),UpdateUserActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    break;
                    case 1:
                        intent = new Intent(getApplicationContext(), SwipeActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    break;
                    case -1:
                        //show error
                    break;
                    case -2:
                        //show error
                    break;
                    default:
                    break;

                }

            }
        });


        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

    }
}