package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    Button regs_btn, goToLogIn;
    TextInputLayout fullname, username, email, password, phone;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        regs_btn = findViewById(R.id.regs_btn);
        goToLogIn = findViewById(R.id.goToLogIn);


        // Initialize Firebase
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        // Find your views by their IDs here (e.g., regs_btn, goToLogIn, fullname, etc.)

    }

    private Boolean validateName() {

        String val = fullname.getEditText().getText().toString();

        if (val.isEmpty()) {
            fullname.setError("field cannot be empty");
            return false;
        } else {
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateUsername() {

        String val = username.getEditText().getText().toString();
        String noSpace= "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            username.setError("field cannot be empty");
            return false;
        } else if (val.length()>=15) {
            username.setError("Username too long");
            return false;

        } else if (!val.matches(noSpace)) {
            username.setError("No white spaces allowed");
            return false;

        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }}
    private Boolean validateEmail() {

        String val = email.getEditText().getText().toString();
        String emailRegex= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("field cannot be empty");
            return false;
        } else if (!val.matches(emailRegex)) {
            email.setError("Invalid Email Address");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
    private Boolean validatePhone() {

        String val = phone.getEditText().getText().toString();

        if (val.isEmpty()) {
            phone.setError("field cannot be empty");
            return false;
        } else {
            phone.setError(null);
            return true;
        }
    }
    private Boolean validatePassword() {

        String val = password.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=.])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            password.setError("Password is too weak");
            return false;
        }else {
            password.setError(null);
            return true;
        }
    }

    public void registerUser(View view) {
        Log.d("SignUpActivity", "registerUser method called");

        // Get all the values in String
        if (!validateName() | !validateEmail() | !validatePassword() | !validatePhone() | !validateUsername()){
            return;
        }

        String namereg = fullname.getEditText().getText().toString();
        String usernamereg = username.getEditText().getText().toString();
        String emailreg = email.getEditText().getText().toString();
        String phonereg = phone.getEditText().getText().toString();
        String passwordreg = password.getEditText().getText().toString();

        // Use push to generate a unique key for the user entry
        DatabaseReference userReference = reference.child(usernamereg);

        // Set the values using setters
        UserHelperClass helperClass = new UserHelperClass();
        helperClass.setFullName(namereg);
        helperClass.setUserName(usernamereg);
        helperClass.setEmail(emailreg);
        helperClass.setPhone(phonereg);
        helperClass.setPassword(passwordreg);

        // Use setValue to save the userHelperClass object
        userReference.setValue(helperClass)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data successfully written to Firebase
                        Toast.makeText(SignUp.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Log.e("SignUpActivity", "Error registering user", e);
                        Toast.makeText(SignUp.this, "Failed to register user. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
