package com.example.pycanmessenger.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pycanmessenger.R;

public class AddNew extends AppCompatActivity {


    private TextView Pv , Group , Channel ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        Pv = findViewById(R.id.txtPv);
        Group = findViewById(R.id.txtChannel);
        Channel = findViewById(R.id.txtGroup);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNew.this , NewChat.class);
                switch (v.getId()){
                    case R.id.txtGroup :
                        Pv.setVisibility(View.GONE); Channel.setVisibility(View.GONE);
                        intent.putExtra("prefixG", "Group");
                        break;
                    case R.id.txtChannel :
                        Pv.setVisibility(View.GONE); Group.setVisibility(View.GONE);
                        intent.putExtra("prefixC", "Channel");
                        break;
                    case R.id.txtPv :
                        Group.setVisibility(View.GONE); Channel.setVisibility(View.GONE);
                        intent.putExtra("prefixP", "Pv");
                        break;
                }
                startActivity(intent);

            }
        };

        Pv.setOnClickListener(clickListener);
        Channel.setOnClickListener(clickListener);
        Group.setOnClickListener(clickListener);


    }

}
