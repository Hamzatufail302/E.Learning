package com.example.elearningfordeafanddumbkids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.google.firebase.auth.FirebaseAuth.*;

public class Registration extends AppCompatActivity {
    private EditText email, password, name;
    private Button register;
    private TextView userlogin;
    private FirebaseAuth firebaseAuth = getInstance();
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        setupUI();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String user_email = email.getText().toString().trim();
                    String user_password = password.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(Registration.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registration.this, login.class));
                            } else {
                                Toast.makeText(Registration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, login.class));
            }
        });

    }

    private void setupUI() {
        email = (EditText) findViewById(R.id.Doc_email);
        password = (EditText) findViewById(R.id.Doc_pass);
        register = (Button) findViewById(R.id.Register_Doc);
        userlogin = (TextView) findViewById(R.id.DocLogin);
        name = (EditText) findViewById(R.id.Doc_name);

    }

    private Boolean validate() {
        Boolean result = false;
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String Name = name.getText().toString();
        if (Email.isEmpty() || Password.isEmpty() || Name.isEmpty()) {
            Toast.makeText(this, "Please Enter all the details", Toast.LENGTH_SHORT).show();
        }
        else if(!Email.matches(emailPattern)) {

            Toast.makeText(Registration.this, "Enter Email correctly", Toast.LENGTH_SHORT).show();

        }
        else {
            result = true;
        }

        return result;
    }


}
