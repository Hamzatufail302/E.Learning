package com.example.elearningfordeafanddumbkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class welcome extends AppCompatActivity {
     public static int SPLASH_TIME_OUT=4000;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(welcome.this,kid_doc.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);

    }
}
