package com.example.pycanmessenger.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pycanmessenger.Models.BitMapHolder;
import com.example.pycanmessenger.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ProfileEditor extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgRotate, imgFilter, imgFlip , changeImg;
    private TextView txtPath;
    private CropImageView img_edt;
    private FloatingActionButton saveFab;
    private Uri uri;
    private boolean discard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_editor);

        imgRotate = findViewById(R.id.img_rotate);
        imgFilter = findViewById(R.id.img_filter);
        imgFlip = findViewById(R.id.img_flip);
        txtPath = findViewById(R.id.txtPath);
        changeImg = findViewById(R.id.changeImg);

        img_edt = findViewById(R.id.img_edit);

        imgFilter.setOnClickListener(this);
        imgRotate.setOnClickListener(this);
        imgFlip.setOnClickListener(this);
        changeImg.setOnClickListener(this);


        saveFab = findViewById(R.id.saveFab);
        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                BitMapHolder.getBitMapHolder().setBitmap(img_edt.getCroppedImage());
                setResult(RESULT_OK, intent);
                discard =true ;
                onBackPressed();
            }
        });

        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().get("Uri")!=null){
                uri = (Uri) getIntent().getExtras().get("Uri");
                img_edt.setImageUriAsync(uri);
                StringBuilder path = new StringBuilder("");
                String[] pathArray = uri.getPath().split("/");
                for (int i = pathArray.length - 3; i < pathArray.length; i++) {
                    path.append(pathArray[i]);
                    if (i < pathArray.length - 1) path.append("/");
                }
                txtPath.setText(path.toString());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (!discard) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)

                    .setTitle("Editor")
                    .setMessage("Are Your Sure To Discard ? ")

                    .setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            discard = true;
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
                    discard = false ;
                    imgFlip.setScaleX(-1);
                    imgFlip.getDrawable().setColorFilter(filter);
                } else if (imgFlip.getScaleX() == -1) {
                    imgFlip.setScaleX(1);
                    discard =true ;
                    imgFlip.getDrawable().setColorFilter(null);
                }
                break;
            case R.id.img_rotate:
                imgRotate.setRotation(imgRotate.getRotation() - 90);
                img_edt.rotateImage(-90);
                if (imgRotate.getRotation() % 360 == 0) {
                    imgRotate.getDrawable().setColorFilter(null);
                    discard =true ;
                }
                else{
                    imgRotate.getDrawable().setColorFilter(filter);
                    discard = false ;
                }
                break;
            case R.id.changeImg:
                if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(ProfileEditor.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                            4000
                    );
                } else {
                    getChosenImage();
                }
                break;
        }
    }
    private void getChosenImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 5000);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 4000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getChosenImage();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5000) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    uri = (Uri) data.getData();
                    img_edt.setImageUriAsync(uri);
                    StringBuilder path = new StringBuilder("");
                    String[] pathArray = uri.getPath().split("/");
                    for (int i = pathArray.length - 3; i < pathArray.length; i++) {
                        path.append(pathArray[i]);
                        if (i < pathArray.length - 1) path.append("/");
                    }
                    txtPath.setText(path.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}