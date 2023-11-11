package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class UserProfile extends AppCompatActivity {
    TextInputLayout fullname,email,phone,password;
    TextView fullnamelabel,usernamelabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        fullnamelabel = findViewById(R.id.fullnamelabel);
        usernamelabel = findViewById(R.id.usernamelabel);

    }

    private void showAllUserData(){

        Intent intent=getIntent();

        String user_name = intent.getStringExtra("name");
        String user_username = intent.getStringExtra("username");
        String user_phone = intent.getStringExtra("phone");
        String user_email = intent.getStringExtra("email");
        String user_password = intent.getStringExtra("password");

        fullnamelabel.setText(user_name);
        usernamelabel.setText(user_username);
        fullname.getEditText().setText(user_name);
        email.getEditText().setText(user_email);
        phone.getEditText().setText(user_phone);
        password.getEditText().setText(user_password);


    }
}