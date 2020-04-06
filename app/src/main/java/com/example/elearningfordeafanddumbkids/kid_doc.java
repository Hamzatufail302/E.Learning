package com.example.elearningfordeafanddumbkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class kid_doc extends AppCompatActivity {
    private Button button16;
    private Button button17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.kid_doc);
        button16 = (Button) findViewById(R.id.Kidprofile);
        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(kid_doc.this,Kid_registration.class));
            }
        });
        button17 = (Button) findViewById(R.id.Doctorprofile);
        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(kid_doc.this, Registration.class));
            }
        });

    }


}