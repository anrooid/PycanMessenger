package com.example.pycanmessenger.Models;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("HRW78F396y8HrbHQI9uIPe9tk2032E8EMed0q12k")
                // if defined
                .clientKey("HUBUNIcnJ9nUtGL76GZAZ3glWMKYYTsO9USf4zUC")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}