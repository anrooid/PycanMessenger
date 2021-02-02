package com.example.pycanmessenger.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.pycanmessenger.Adapters.TabAdapter;
import com.example.pycanmessenger.Models.BottomSheetDialog;
import com.example.pycanmessenger.Models.interfaces.OnItemClickListener;
import com.example.pycanmessenger.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends AppCompatActivity  {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAdapter tabAdapter;


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
        tabLayout.setupWithViewPager(viewPager, false);
        viewPager.setAdapter(tabAdapter);




        fab = findViewById(R.id.fabAddNewChat);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        Intent intent0 = new Intent(MainActivity.this, AddNew.class);
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, NewChat.class);
                        intent1.putExtra("prefixP", "Pv");
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(MainActivity.this, NewChat.class);
                        intent2.putExtra("prefixG", "Group");
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MainActivity.this, NewChat.class);
                        intent3.putExtra("prefixC", "Channel");
                        startActivity(intent3);
                        break;

                }

            }
        });

        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get("isCreating") != null) {
                }
            }
        }
    }




}