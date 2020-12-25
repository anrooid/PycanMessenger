package com.example.pycanmessenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.zip.Inflater;

import static com.example.pycanmessenger.R.menu.menu_menu;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    Toolbar toolbar;
    ViewPager viewPager ;
    TabLayout tabLayout;
    TabAdapter tabAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Pycan Messenger");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        tabAdapter = new TabAdapter(getSupportFragmentManager());

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager,false);
        viewPager.setAdapter(tabAdapter);

        // go to activity AddNew
        fab = findViewById(R.id.fabAddNewChat);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , AddNew.class);
                startActivity(intent);
            }
        });

        // app id = HRW78F396y8HrbHQI9uIPe9tk2032E8EMed0q12k
        // client key  = HUBUNIcnJ9nUtGL76GZAZ3glWMKYYTsO9USf4zUC
        // server = https://parseapi.back4app.com/

    }
}