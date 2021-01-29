package com.example.pycanmessenger.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pycanmessenger.Models.BitMapHolder;
import com.example.pycanmessenger.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ProfileEditor extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgRotate, imgFilter, imgFlip;
    private TextView txtPath;
    private CropImageView img_edt;
    private FloatingActionButton saveFab;
    private Uri uri;
    private boolean dicard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_editor);

        imgRotate = findViewById(R.id.img_rotate);
        imgFilter = findViewById(R.id.img_filter);
        imgFlip = findViewById(R.id.img_flip);
        txtPath = findViewById(R.id.txtPath);

        img_edt = findViewById(R.id.img_edit);

        imgFilter.setOnClickListener(this);
        imgRotate.setOnClickListener(this);
        imgFlip.setOnClickListener(this);


        saveFab = findViewById(R.id.saveFab);
        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                BitMapHolder.getBitMapHolder().setBitmap(img_edt.getCroppedImage());
                setResult(RESULT_OK, intent);
                onBackPressed();
            }
        });


    }

    @Override
    protected void onResume() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.get("Uri")!=null){
                uri = (Uri) bundle.get("Uri");
                img_edt.setImageUriAsync(uri);
                StringBuilder path = new StringBuilder("");
                String[] pathArray = uri.getEncodedPath().split("%2F");
                for (int i = pathArray.length - 3; i < pathArray.length; i++) {
                    pathArray[i] = pathArray[i].replaceAll("%20"," ");
                    path.append(pathArray[i]);
                    if (i < pathArray.length - 1) path.append("/");
                }
                txtPath.setText(path.toString());
            }
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (!dicard) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)

                    .setTitle("Editor")
                    .setMessage("Are Your Sure To Discard ? ")

                    .setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dicard = true;
                            dialogInterface.dismiss();
                            onBackPressed();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })

                    .show();
        } else
            super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        ColorFilter filter = new PorterDuffColorFilter(getResources().getColor(R.color.ac_color), PorterDuff.Mode.SRC_IN);
        switch (view.getId()) {
            case R.id.img_filter:
                AlertDialog alertDialog = new AlertDialog.Builder(this)

                        .setTitle("Sorry")
                        .setMessage("This feature is not available in this version" + "\n" + "Coming soon ...")

                        .setPositiveButton("Yes",null)
                        .show();
                break;
            case R.id.img_flip:
                img_edt.flipImageHorizontally();
                if (imgFlip.getScaleX() == 1) {
                    imgFlip.setScaleX(-1);
                    imgFlip.getDrawable().setColorFilter(filter);
                } else if (imgFlip.getScaleX() == -1) {
                    imgFlip.setScaleX(1);
                    imgFlip.getDrawable().setColorFilter(null);
                }
                break;
            case R.id.img_rotate:
                imgRotate.setRotation(imgRotate.getRotation() - 90);
                img_edt.rotateImage(-90);
                if (imgRotate.getRotation() % 360 == 0)
                    imgRotate.getDrawable().setColorFilter(null);
                else
                    imgRotate.getDrawable().setColorFilter(filter);
                break;
        }
    }
}