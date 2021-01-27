package com.example.pycanmessenger.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pycanmessenger.Models.BitMapHolder;
import com.example.pycanmessenger.R;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ProfileEditor extends AppCompatActivity {

    private CropImageView img_edt ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_editor);

        img_edt = findViewById(R.id.img_edit);
        img_edt.setImageBitmap(BitMapHolder.getBitMapHolder().getBitmap());

        BitMapHolder.getBitMapHolder().setBitmap(img_edt.getCroppedImage());
    }
}