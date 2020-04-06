package com.example.elearningfordeafanddumbkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class choose_menu extends AppCompatActivity {
    private Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_menu);
        button5= (Button)findViewById(R.id.Time_learn);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Openactivity();
            }
        });

    }
    public void Openactivity(){
        Intent intent;
        startActivity(new Intent(this, Choose_type.class));

    }

    public void myMonitor(View view) {
        startActivity(new Intent(this,Monitor.class));
    }

    public void Addextra(View view) {
        Intent intent;
        startActivity(new Intent(this, Monitor.class));
    }
}
