package com.example.signuploginrealtime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    EditText signupName, signupEmail, signupUsername, signUpPassword;
    TextView loginRedirectText;
    Button signupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signupName = (EditText) findViewById(R.id.signup_name);
        signupEmail = (EditText)findViewById(R.id.signup_email);
        signupUsername = (EditText)findViewById(R.id.signup_username);
        signUpPassword = (EditText)findViewById(R.id.signup_password);
        signupButton = (Button) findViewById(R.id.signup_button);
        loginRedirectText = (TextView) findViewById(R.id.loginRedirectText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userInput = signupName.getText().toString();
                String userEmail = signupEmail.getText().toString();
                String userInput2 = signupUsername.getText().toString();
                String userPass = signUpPassword.getText().toString();

                addToDB(userInput,userEmail,userInput2,userPass);


            }
        });




        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addToDB(String userName, String userEmail, String userName2, String userPass){

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name",userName);
        hashMap.put("email",userEmail);
        hashMap.put("username",userName2);
        hashMap.put("password",userPass);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("User Data");

        String key = reference.push().getKey();
        hashMap.put("key",key);

        reference.child(key).setValue(hashMap) .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SignupActivity.this, "You have signup successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

    }
}