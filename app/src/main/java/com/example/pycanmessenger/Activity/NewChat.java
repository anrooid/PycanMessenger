package com.example.pycanmessenger.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.pycanmessenger.Models.BitMapHolder;
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
    private ImageView imgProfile, imgCamera;
    EditText edtNameChat, edtDescription;
    TextView txtCounter;
    Bitmap receivedImageBitmap;
    Uri selectedImage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

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
                txtCounter.setText(length + "/100");
                if (length == 100) {// vibrate ~!
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
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //pv OR group OR channel
        if (bundle != null) {

            String dataC = bundle.getString("prefixC");
            String dataG = bundle.getString("prefixG");
            String dataPv = bundle.getString("prefixP");
            String s;
            if (dataC != null) {
                s = dataC;
                Channel = true;
            } else if (dataG != null) {
                s = dataG;
                Group = true;
            } else {
                s = dataPv;
                PV = true;
                txtseen.setVisibility(View.VISIBLE);
                checkSeen.setVisibility(View.VISIBLE);
            }
            setTitle("New " + s);
        }

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
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2000);
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
                     selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,
                            null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                    // onpen a new activity ! in order to edit ! Crop Fillter
                    BitMapHolder.getBitMapHolder().setBitmap(receivedImageBitmap);
                    Intent intent = new Intent(NewChat.this, ProfileEditor.class);
                    startActivityForResult(intent, 3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (resultCode == 3000) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //BitMapHolder.getBitMapHolder().getBitmap();
                    receivedImageBitmap= BitMapHolder.getBitMapHolder().getBitmap();
                    imgProfile.setImageBitmap(receivedImageBitmap);
                    imgCamera.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
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
            // Todo : add an on change validator !
            if (edtNameChat.getText().toString().equals("")) {
                edtNameChat.setError("set name");
            }
            if (PV) {
                Chats.put("Name", "0" + edtNameChat.getText().toString());
            } else if (Group) {
                Chats.put("Name", "1" + edtNameChat.getText().toString());
            } else if (Channel) {
                Chats.put("Name", "2" + edtNameChat.getText().toString());
            }
            // Todo : add an on change validator in order to handel enter !
            Chats.put("Descripton", edtDescription.getText().toString());
            if (PV)
                Chats.put("Seen", checkSeen.isChecked());
            if (receivedImageBitmap != null ) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();
                ParseFile parseFile = new ParseFile("img.png", bytes);
                Chats.put("Profile", parseFile);
            }
            Chats.put("Time", ft.format(time).toString());
            // Todo : fix the time delay for uploading profile !
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
