package com.example.pycanmessenger.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pycanmessenger.Models.BitMapHolder;
import com.example.pycanmessenger.R;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ProfileEditor extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgRotate , imgFilter , imgFlip;
    private CropImageView img_edt ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_editor);

        imgRotate = findViewById(R.id.img_rotate);
        imgFilter = findViewById(R.id.img_filter);
        imgFlip = findViewById(R.id.img_flip);

        img_edt = findViewById(R.id.img_edit);
        img_edt.setImageBitmap(BitMapHolder.getBitMapHolder().getBitmap());

        //BitMapHolder.getBitMapHolder().setBitmap(img_edt.getCroppedImage());

        imgFilter.setOnClickListener(this);
        imgRotate.setOnClickListener(this);
        imgFlip.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_filter :
                Toast.makeText(ProfileEditor.this , "this image is not available in this version", Toast.LENGTH_LONG).show();
                break;
            case  R.id.img_flip:
                break;
            case  R.id.img_rotate:
                imgRotate.setRotation(imgRotate.getRotation()+90);
                break;
        }
    }
}