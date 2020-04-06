package com.example.elearningfordeafanddumbkids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

import static com.google.firebase.auth.FirebaseAuth.*;

public class Kid_registration extends AppCompatActivity {
    private static int Pick_image = 123;
    String Name, Email, Password;
    int age;
    int kidage;
    Uri ImagePath;
    private EditText email, password, name, Age;
    private Button kidregister;
    private TextView userlogin;
    private FirebaseAuth firebaseAuth = getInstance();
    private ImageView userimage;
    private StorageReference storageReference;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Pick_image && resultCode == RESULT_OK && data.getData() != null) {
            ImagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImagePath);
                userimage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kid_registration);
        setupUI();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Pick_image);
            }
        });


        kidregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    final String user_email = email.getText().toString().trim();
                    final String user_password = password.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendUserData();
                                Toast.makeText(Kid_registration.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                Sessions sessions=new Sessions(getApplicationContext());
                                sessions.setUsername(user_email);
                                sessions.setPassword(user_password);
                                sessions.setType("kid");
                    startActivity(new Intent(getApplicationContext(),choose_menu.class));
                            } else {
                                Toast.makeText(Kid_registration.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }


            }
        });
        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Kid_registration.this, Kid_login.class));
            }
        });

    }



    private void setupUI() {
        email = (EditText) findViewById(R.id.kid_email);
        password = (EditText) findViewById(R.id.kid_pass);
        kidregister = (Button) findViewById(R.id.Register_kid);
        userlogin = (TextView) findViewById(R.id.Login);
        name = (EditText) findViewById(R.id.kid_name);
        Age = (EditText) findViewById(R.id.kid_age);

        userimage = (ImageView) findViewById(R.id.kid_iamge);

    }


    private Boolean validate() {
        Boolean result = false;
        Email = email.getText().toString();
        Password = password.getText().toString();
        Name = name.getText().toString();
        try {
            age = Integer.parseInt(Age.getText().toString().trim());
        } catch (NumberFormatException nfe) {
            System.out.println("cant parse");
        }


        if (Email.isEmpty() || Password.isEmpty() || Name.isEmpty()   ) {

            Toast.makeText(this, "Please Enter all the details and correctly", Toast.LENGTH_SHORT).show();


        }
        else if(!Email.matches(emailPattern)) {
            Toast.makeText(Kid_registration.this, "Enter Email correctly", Toast.LENGTH_SHORT).show();

        }
        else if(age > 5){
            Toast.makeText(this, "Please Enter Age below 5", Toast.LENGTH_SHORT).show();
        }
        else if (userimage.getDrawable() == null){
            Toast.makeText(this, "Please upload image", Toast.LENGTH_SHORT).show();
           // userimage.setImageDrawable(R.drawable.ic_launcher_background);

        }
        else {
            result = true;
        }
        return result;
    }


    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference(firebaseAuth.getUid());

        StorageReference imagereferance = storageReference.child(firebaseAuth.getUid()).child("Images").child("profile pic");
        UploadTask uploadTask = imagereferance.putFile(ImagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Kid_registration.this, "upload failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Kid_registration.this, "upload Success", Toast.LENGTH_SHORT).show();

            }
        });
        UserProfile userProfile = new UserProfile(age, Email, Name);
        myref.setValue(userProfile);
    }

}
