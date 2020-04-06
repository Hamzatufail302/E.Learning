package com.example.elearningfordeafanddumbkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Sessions sessions=new Sessions(getApplicationContext());
        Thread mythread=new Thread(){
            @Override
            public void run() {
               try{
                   sleep(2500);

                   if(!sessions.getUsername().equals("")){// Not Empty
                       if(sessions.getType().equals("kid")){
                           startActivity(new Intent(getApplicationContext(),choose_menu.class));
                       }
                       else{
                   //    startActivity(new Intent(getApplicationContext(),welcome.class));

                       }
                   }
                   else{
                       startActivity(new Intent(getApplicationContext(),kid_doc.class));

                   }
               }
               catch (InterruptedException e){

               }
            }
        };
        mythread.start();
    }
}
