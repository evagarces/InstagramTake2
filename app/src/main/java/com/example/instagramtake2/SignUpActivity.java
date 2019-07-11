package com.example.instagramtake2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private EditText newUsername;
    private EditText newPassword;
    private EditText newEmail;
    private Button goBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        newUsername = findViewById(R.id.newuser_et);
        newPassword = findViewById(R.id.newpass_et);
        newEmail = findViewById(R.id.email_et);
        goBtn = findViewById(R.id.goBtn);

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = newUsername.getText().toString();
                final String pass = newPassword.getText().toString();
                final String email = newEmail.getText().toString();

                createAccount(user, pass, email);
            }
        });
    }

    private void createAccount(String username, String password, String email) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("Sign up", "Sign up succeeded");
                    final Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Log.e("Sign Up", "Sign up failed");
                    e.printStackTrace();
                }
            }
        });
    }
}
