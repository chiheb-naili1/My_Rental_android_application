package com.example.myapplication;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    Button GoToSignUpBtn, login_btn;
    ImageView image;
    TextView logo_name, slogan_name;
    TextInputLayout username, password;

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

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUser();
            }
        });

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

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString();
        String noSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            username.setError("field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            username.setError("Username too long");
            return false;

        } else if (!val.matches(noSpace)) {
            username.setError("No white spaces allowed");
            return false;

        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {

        String val = password.getEditText().getText().toString().trim();


        if (val.isEmpty()) {
            password.setError("field cannot be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    public void loginUser(View view) {

        if (!validateUsername() && validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        String usernameEnteredVal = username.getEditText().getText().toString().trim();
        String passwordEnteredVal = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = reference.orderByChild("username").equalTo(usernameEnteredVal);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(usernameEnteredVal).child("password").getValue(String.class);

                    if(passwordFromDB.equals(passwordEnteredVal)){

                        password.setError(null);
                        password.setErrorEnabled(false);

                        String fullnameFromDB = snapshot.child(usernameEnteredVal).child("fullname").getValue(String.class);
                        String usernameFromDB = snapshot.child(usernameEnteredVal).child("username").getValue(String.class);
                        String phoneFromDB = snapshot.child(usernameEnteredVal).child("phone").getValue(String.class);
                        String emailFromDB = snapshot.child(usernameEnteredVal).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(),UserProfile.class);

                        intent.putExtra("name",fullnameFromDB);
                        intent.putExtra("username",usernameFromDB);
                        intent.putExtra("phone",phoneFromDB);
                        intent.putExtra("email",emailFromDB);
                        intent.putExtra("password",passwordFromDB);

                        startActivity(intent);

                    }else {
                        password.setError("Wrong password");
                        password.requestFocus();
                    }
                }else{
                    username.setError("User does not exist");
                    username.requestFocus();
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
