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
import android.widget.TextView;
import android.widget.Toolbar;

public class AddNew extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);


        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNew.this , NewChat.class);
                switch (v.getId()){
                    case R.id.txtGroup :
                        intent.putExtra("prefixG", "prefixG");
                        break;
                    case R.id.txtChannel :
                        intent.putExtra("prefixC", "prefixC");
                        break;
                    case R.id.txtPv :
                        intent.putExtra("prefixP", "prefixP");
                        break;
                }
                startActivity(intent);

            }
        };

        findViewById(R.id.txtPv).setOnClickListener(clickListener);
        findViewById(R.id.txtChannel).setOnClickListener(clickListener);
        findViewById(R.id.txtGroup).setOnClickListener(clickListener);


    }

}
