package com.example.pycanmessenger.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pycanmessenger.Models.BitMapHolder;
import com.example.pycanmessenger.R;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ProfileEditor extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgRotate , imgFilter , imgFlip;
    private TextView txtPath;
    private CropImageView img_edt ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_editor);

        imgRotate = findViewById(R.id.img_rotate);
        imgFilter = findViewById(R.id.img_filter);
        imgFlip = findViewById(R.id.img_flip);
        txtPath = findViewById(R.id.txtPath);

        img_edt = findViewById(R.id.img_edit);
        img_edt.setImageBitmap(BitMapHolder.getBitMapHolder().getBitmap());

        imgFilter.setOnClickListener(this);
        imgRotate.setOnClickListener(this);
        imgFlip.setOnClickListener(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!= null){
            StringBuilder path = new StringBuilder("") ;
            String[] pathArray = bundle.getString("picturePath").split("/");
            for (int i = 4 ; i < pathArray.length ; i++) {path.append(pathArray[i]) ; if (i<pathArray.length-1) path.append("/");}
            txtPath.setText(path.toString());
        }

    }

    @Override
    public void onClick(View view) {
        ColorFilter filter = new PorterDuffColorFilter(getResources().getColor(R.color.ac_color), PorterDuff.Mode.SRC_IN);
        switch (view.getId()){
            case R.id.img_filter :
                AlertDialog alertDialog = new AlertDialog.Builder(this)

                        .setTitle("Sorry")

                        .setMessage("This feature is not available in this version" + "\n" + "Coming soon ...")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                              //  finish();
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
                break;
            case  R.id.img_flip:
                if (imgFlip.getScaleX() == 1){
                    imgFlip.setScaleX(-1);
                    imgFlip.getDrawable().setColorFilter(filter);
                }else if (imgFlip.getScaleX() == -1){
                    imgFlip.setScaleX(1);
                    imgFlip.getDrawable().setColorFilter(null);
                }
                break;
            case  R.id.img_rotate:
                imgRotate.setRotation(imgRotate.getRotation()-90);
                imgRotate.getDrawable().setColorFilter(filter);
                break;
        }
    }
}