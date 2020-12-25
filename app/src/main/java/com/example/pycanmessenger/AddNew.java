package com.example.pycanmessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toolbar;

public class AddNew extends AppCompatActivity {


    String prefix;
    private RadioButton checkPV, checkGroup , checkChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        checkPV = findViewById(R.id.checkPV);
        checkGroup = findViewById(R.id.checkGroup);
        checkChannel = findViewById(R.id.checkChannel);

        checkPV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNew.this , NewChat.class);
                intent.putExtra("prefixP", "prefixP");
                startActivity(intent);
            }
        });

        checkGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNew.this , NewChat.class);
                intent.putExtra("prefixG", "prefixG");
                startActivity(intent);
            }
        });

        checkChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNew.this , NewChat.class);
                intent.putExtra("prefix" , "prefixC");
                startActivity(intent);
            }
        });
    }

}
