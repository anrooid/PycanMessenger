package com.example.pycanmessenger.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.pycanmessenger.Adapters.ChatAdapter;
import com.example.pycanmessenger.Adapters.TabAdapter;
import com.example.pycanmessenger.Models.BottomSheetDialog;
import com.example.pycanmessenger.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends AppCompatActivity implements ChatAdapter.OnItemClickListener , com.example.pycanmessenger.Models.BottomSheetDialog.BottomSheetListener {

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
        tabAdapter = new TabAdapter(getSupportFragmentManager() , this);

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


    @Override
    public void onItemClick(ParseObject parseObject) {

    }

    @Override
    public void onLongItemClick(ParseObject parseObject) {
        //BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        com.example.pycanmessenger.Models.BottomSheetDialog bottomSheetDialog= new BottomSheetDialog(this ,parseObject);
        bottomSheetDialog.show(getSupportFragmentManager(),"BottomSheet");
    }


    @Override
    public void onButtonClicked(int a , ParseObject parseObject) {
        switch (a){
            case 1:
                edit(parseObject);
                break;
            case 2:
                delete(parseObject);
                break;
            default:
        }

    }

    private void delete(ParseObject parseObject) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Chats");

        query.getInBackground(parseObject.getObjectId(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e ==null){
                    object.deleteInBackground();
                }
            }
        });
    }

    private void edit(ParseObject parseObject) {
        Intent intent = new Intent(MainActivity.this , NewChat.class);
        intent.putExtra("open for edit" ,parseObject );
        startActivity(intent);

    }
}