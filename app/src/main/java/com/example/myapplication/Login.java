package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;


public class Login extends AppCompatActivity {
    Button GoToSignUpBtn,login_btn;
    ImageView image;
    TextView logo_name,slogan_name;
    TextInputLayout username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        GoToSignUpBtn = findViewById(R.id.GoToSignUpBtn);
        image = findViewById(R.id.image_logo);
        logo_name = findViewById(R.id.logo_name);
        slogan_name = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);

        GoToSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<>(image, "logo_image");
                pairs[1] = new Pair<>(logo_name, "logo_name");
                pairs[2] = new Pair<>(slogan_name, "slogan_text");
                pairs[3] = new Pair<>(username, "logo_image");
                pairs[4] = new Pair<>(password, "logo_image");
                pairs[5] = new Pair<>(login_btn, "button_trans");
                pairs[6] = new Pair<>(GoToSignUpBtn, "login_signup_trans");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                startActivity(intent,options.toBundle());


            }

        });
    }
}
