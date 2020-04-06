package com.example.elearningfordeafanddumbkids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class login extends AppCompatActivity {
    private TextView textView1;
    private FirebaseAuth firebaseAuth;
    private Button button;
    private EditText name,password;
    private String Demail,Dpass;
    private ProgressDialog progressDialog;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        textView1 = (TextView) findViewById(R.id.Docregister);
        firebaseAuth=firebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        button=(Button)findViewById(R.id.loginDoc);
        name=(EditText)findViewById(R.id.Docusername);
        password=(EditText)findViewById(R.id.Docpassword);

        FirebaseAuth user=FirebaseAuth.getInstance();


        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, Registration.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Demail=name.getText().toString();
                Dpass=password.getText().toString();


                validate(Demail,Dpass);
            }

        });



    }

    private void validate(final String username, final String userpass) {
        progressDialog.setMessage("please wait");
        progressDialog.show();
        if (Demail.isEmpty() || Dpass.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(login.this, "Enter all the details corrently", Toast.LENGTH_SHORT).show();

        }
        else if(!Demail.matches(emailPattern)) {
            progressDialog.dismiss();
            Toast.makeText(login.this, "Enter Email correctly", Toast.LENGTH_SHORT).show();

        }
        else {
            firebaseAuth.signInWithEmailAndPassword(username, userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        progressDialog.dismiss();
                        Toast.makeText(login.this, "login Success", Toast.LENGTH_SHORT).show();
                        Sessions sessions=new Sessions(getApplicationContext());
                        sessions.setUsername(username);
                        sessions.setPassword(userpass);
                        startActivity(new Intent(login.this, LindexProfile.class));
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(login.this, "login failed PLease Check all details", Toast.LENGTH_SHORT).show();
                    }


                }

            });
        }
    }
}
