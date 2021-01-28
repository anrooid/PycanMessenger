package com.example.pycanmessenger.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
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
        //img_edt.setImageBitmap(BitMapHolder.getBitMapHolder().getBitmap());

        //BitMapHolder.getBitMapHolder().setBitmap(img_edt.getCroppedImage());

        imgFilter.setOnClickListener(this);
        imgRotate.setOnClickListener(this);
        imgFlip.setOnClickListener(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!= null){
            String s ;
            String a ;
            String path = bundle.getString("picturePath");
            s = path.substring(path.indexOf("/")+1);
            a = s.substring(s.indexOf("/")+1);
            txtPath.setText(a);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_filter :
                AlertDialog alertDialog = new AlertDialog.Builder(this)

                        .setIcon(android.R.drawable.ic_dialog_alert)

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
                }else if (imgFlip.getScaleX() == -1){
                    imgFlip.setScaleX(1);
                }
                imgFlip.setColorFilter(R.style.Theme_Vector);

                break;
            case  R.id.img_rotate:
                imgRotate.setRotation(imgRotate.getRotation()-90);
                imgRotate.setColorFilter(R.style.Theme_Vector);

                break;
        }
    }
}