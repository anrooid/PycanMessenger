package com.example.pycanmessenger.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pycanmessenger.Models.App;
import com.example.pycanmessenger.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewChat extends AppCompatActivity {


    private CheckBox checkSeen;
    private Toolbar toolbar;
    private TextView txtseen;
    Boolean PV, Group, Channel;
    private ImageView imgProfile , imgCamera;
    EditText edtNameChat, edtDescription;
    TextView txtCounter;
    Bitmap receivedImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        PV = false;
        Group = false;
        Channel = false;
        checkSeen = findViewById(R.id.chkSeen);
        toolbar = findViewById(R.id.newChatToolbar);
        imgProfile = findViewById(R.id.imgProfile);
        imgCamera = findViewById(R.id.imgCamera);
        edtNameChat = findViewById(R.id.edtNameChat);
        edtDescription = findViewById(R.id.edtDescription);
        txtCounter = findViewById(R.id.txtCounter);
        txtseen = findViewById(R.id.txtseen);
        setSupportActionBar(toolbar);

        edtDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.toString().length();
                txtCounter.setText(length+"/100");
                if (length==100){// vibrate ~!
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        v.vibrate(500);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //pv OR group OR channel
        if (bundle != null) {
            String dataC = bundle.getString("prefixC");
            String dataG = bundle.getString("prefixG");
            String dataPv = bundle.getString("prefixP");

            if (dataC == bundle.getString("prefixC", "prefixC")) {
                Channel = true;
                PV = false;
                Group = false;
                setTitle("New Channel");

            } else if (dataG == bundle.getString("prefixG", "prefixG")) {
                Group = true;
                PV = false;
                Channel = false;
                setTitle("New Group");

            } else if (dataPv == bundle.getString("prefixP", "prefixP")) {
                PV = true;
                Group = false;
                Channel = false;
                setTitle("New PV");
                txtseen.setVisibility(View.VISIBLE);checkSeen.setVisibility(View.VISIBLE);
            }
        }
        ////////////////////////
        ///////////////////////
        //////////////////////      FIX GETING PICTURE FROM STOREG
        /////////////////////
        ////////////////////
        /////////////////// Todo : fix photo picker
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(NewChat.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                            1000
                    );
                } else {
                    getChosenImage();
                }
            }
        });
    }

    private void getChosenImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        intent.putExtra("crop", "true");
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        startActivityForResult(intent, 2000);
        imgCamera.setVisibility(View.INVISIBLE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getChosenImage();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2000) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,
                            null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                    imgProfile.setImageBitmap(receivedImageBitmap);
                } catch (Exception e) {

                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.check_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnuCheck) {


            //time
            Date time = Calendar.getInstance().getTime();
            SimpleDateFormat ft = new SimpleDateFormat("hh:mm");
            ParseObject Chats = new ParseObject("Chats");
            //prefix
            if (PV) {
                Chats.put("Name", "0" + edtNameChat.getText().toString());
            } else if (Group) {
                Chats.put("Name", "1" + edtNameChat.getText().toString());
            } else if (Channel) {
                Chats.put("Name", "2" + edtNameChat.getText().toString());
            }
            Chats.put("Descripton", edtDescription.getText().toString());
            if (PV)
                Chats.put("Seen", checkSeen.isChecked());
            if (receivedImageBitmap != null){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();
                ParseFile parseFile = new ParseFile("img.png" , bytes);
                Chats.put("Profile", parseFile);}

            Chats.put("Time", ft.format(time).toString());
            Chats.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(NewChat.this, Chats.get("Name").toString().substring(1) + "is saved successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NewChat.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(NewChat.this, e.getCode() + "" + e.getStackTrace()[0], Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });
        }


        return super.onOptionsItemSelected(item);
    }

//   
}
