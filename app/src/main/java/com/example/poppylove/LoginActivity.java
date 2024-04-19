package com.example.poppylove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText mPhone;
    private EditText mPass;
    private Button BtmLogin;

    private TextView mSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Creates Firebase Reference
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://gc-puppylove-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("message");

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

                boolean err = false;

                //Checks if fields are empty
                if (TextUtils.isEmpty(phone)) {
                    mPhone.setError("Phone number its required");
                    err = true;
                }
                if (TextUtils.isEmpty(password)) {
                    mPass.setError("Password its required");
                    err = true;
                }
                if(err){
                    return;
                }

                //Logins in if details match
                /*LoggedIn values
                 * 2 success but profile incomplete
                 * 1 success
                 * -1 failure incorrect password
                 * -2 failure not registered
                 */

                final Intent[] intent = new Intent[1];
                Bundle bundle = new Bundle();
                bundle.putString("PhoneID",phone);

                //tries to login, if fails shows why
                FirebaseController.login(phone, password, new Callback(){
                    @Override
                    public void onComplete(int result) {
                        switch (result){
                            case 2:
                                intent[0] = new Intent(getApplicationContext(),UpdateUserActivity.class);
                                intent[0].putExtras(bundle);
                                startActivity(intent[0]);
                                break;
                            case 1:
                                intent[0] = new Intent(getApplicationContext(), SwipeActivity.class);

                                intent[0].putExtras(bundle);
                                startActivity(intent[0]);
                                break;
                            case -1:
                                //show error

                                mPass.setError("password is incorrect");


                                break;
                            case -2:
                                //show error
                                mPhone.setError("No account exists");
                                break;
                            default:
                                break;

                        }
                    }

                    @Override
                    public void onUserListComplete(List<ProfileData> result) {

                    }

                    @Override
                    public void onDogListComplete(List<DogProfile> result) {

                    }
                });





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