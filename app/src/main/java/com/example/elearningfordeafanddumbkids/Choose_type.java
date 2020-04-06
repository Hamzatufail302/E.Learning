package com.example.elearningfordeafanddumbkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Choose_type extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_type);
    }


    public void Fruit(View view) {
      // startActivity(new Intent(this,Sixth.class));
        String [] img_names={"Apple","Banana","Mango"};
        int index=0;
        String [] vid_names={"Apple","Banana","Mango"};
        Intent intent=new Intent(Choose_type.this,Sixth.class);
        intent.putExtra("images",img_names);
        intent.putExtra("videos",vid_names);
        startActivity(intent);
    }
    public void Vegitable(View view){ startActivity(new Intent(this,Sixth.class)); }
    public void Animal(View view){startActivity(new Intent(this,Sixth.class));}
    public void Sound(View view){startActivity(new Intent(this,Sixth.class));}
    public void Gestures(View view){startActivity(new Intent(this,Sixth.class));}
}
