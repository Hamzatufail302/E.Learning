package com.example.elearningfordeafanddumbkids;

import android.content.Context;
import android.content.SharedPreferences;

public class Sessions {

    String Username;
    String Password;
    String Type;
    Context context;

     SharedPreferences sharedPreferences;
    public Sessions(Context context) {
        this.context=context;
        sharedPreferences=context.getSharedPreferences("session",Context.MODE_PRIVATE);
    }
    public  String getUsername() {
        Username=sharedPreferences.getString("username","");
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
        sharedPreferences.edit().putString("username",username).apply();
    }

    public String getPassword() {
        Password=sharedPreferences.getString("password","");
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
        sharedPreferences.edit().putString("password",password).apply();
    }

    public String getType() {
        Type=sharedPreferences.getString("type","");
        return Type;
    }

    public void setType(String type) {
        Type = type;
        sharedPreferences.edit().putString("type",type).apply();

    }
    public void removeSession(){
        sharedPreferences.edit().clear().apply();
    }
}
