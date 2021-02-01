package com.example.pycanmessenger.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pycanmessenger.R;

public class AddNew extends AppCompatActivity implements View.OnClickListener {


    private TextView Pv , Group , Channel ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        Pv = findViewById(R.id.txtPv);
        Channel = findViewById(R.id.txtChannel);
        Group = findViewById(R.id.txtGroup);


    }

    @Override
    protected void onResume() {
        Pv.setOnClickListener(this); Group.setOnClickListener(this); Channel.setOnClickListener(this);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(AddNew.this , NewChat.class);
        switch (v.getId()){
            case R.id.txtPv :
                Group.setOnClickListener(null); Channel.setOnClickListener(null);
                intent.putExtra("prefixP", "Pv");
                break;
            case R.id.txtGroup :
                Pv.setOnClickListener(null); Channel.setOnClickListener(null);
                intent.putExtra("prefixG", "Group");
                break;
            case R.id.txtChannel :
                Pv.setOnClickListener(null); Group.setOnClickListener(null);
                intent.putExtra("prefixC", "Channel");
                break;

        }
        startActivity(intent);
    }
}
