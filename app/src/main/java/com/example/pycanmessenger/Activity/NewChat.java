package com.example.pycanmessenger.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.pycanmessenger.Models.BitMapHolder;
import com.example.pycanmessenger.Models.BottomSheetDialogPro;
import com.example.pycanmessenger.R;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewChat extends AppCompatActivity  {

    ParseObject parseObject;
    private CheckBox checkSeen;
    private Toolbar toolbar;
    private TextView txtseen;
    private ImageView imgProfile, imgCamera;
    EditText edtNameChat, edtDescription;
    TextView txtCounter;
    Bitmap receivedImageBitmap;
    Uri selectedImage;
    private boolean dicard , b;
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
b=false;
        setSupportActionBar(toolbar);


        edtDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.toString().length();
                txtCounter.setText(length + "/100");
                if (length == 100) {
                    vibrate();
                    Toast.makeText(NewChat.this, "no more description allowed ! ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                for (int i = s.length() - 1; i >= 0; i--) {
                    if (s.charAt(i) == '\n') {
                        vibrate();
                        Toast.makeText(NewChat.this, "you can not press enter key", Toast.LENGTH_SHORT).show();
                        s.delete(i, i + 1);
                        return;
                    }
                }
            }
        });
        edtNameChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.toString().length();
                if (length == 25) {
                    vibrate();
                    Toast.makeText(NewChat.this, "Long Name !", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                // no need to be valid name more ! but you can have codes on github commit [7080817aa692584c92f9b2dc8f5e576d5ae4eaf9]
            }
        });
        String s;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //pv OR group OR channel
        if (bundle != null) {

            if (bundle.getString("prefixC") != null) {
                s = bundle.getString("prefixC");
            } else if (bundle.getString("prefixG") != null) {
                s = bundle.getString("prefixG");
            } else {
                s = bundle.getString("prefixP");
                txtseen.setVisibility(View.VISIBLE);
                checkSeen.setVisibility(View.VISIBLE);
            }
            if (getIntent().getExtras().get("open for edit") != null) {
                setTitle("edit chat");
                getChat();

//                imgProfile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //TODO : set showBottomSheet
//                        showBottomSheet();
//                    }
//                });
            } else setTitle("New " + s); b =false;
        }
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getTitle().toString()=="edit chat"){
                    showBottomSheet();
                }else {
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
//                if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(NewChat.this,
//                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(new String[]{
//                                    Manifest.permission.READ_EXTERNAL_STORAGE},
//                            1000
//                    );
//                } else {
//                    getChosenImage();
//                }
            }
        });
    }


    @Override
    protected void onResume() {
        dicard = false;
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (!dicard && (!edtNameChat.getText().toString().matches("") || !edtDescription.getText().toString().matches("") || receivedImageBitmap != null)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)

                    .setTitle(getTitle())
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
                    Intent intent = new Intent(NewChat.this, ProfileEditor.class);
                    selectedImage = data.getData();
                    intent.putExtra("Uri", selectedImage);
                    startActivityForResult(intent, 3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == 3000) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    receivedImageBitmap = BitMapHolder.getBitMapHolder().getBitmap();
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
            if (getIntent().getExtras().get("open for edit" ) == parseObject) {
                updateChat();
            } else newChat();
        }

        return super.onOptionsItemSelected(item);

    }

    //showChatInfo
    public void getChat() {
        parseObject = (ParseObject) getIntent().getExtras().get("open for edit" );
//        parseObject.getParseObject(parseObject.getObjectId()).getBytes("Profile")  ???
//        parseObject.getBytes("Profile");   ???
//        parseObject.getParseObject("Profile");    ???
        edtNameChat.setText( parseObject.getParseObject(parseObject.getObjectId()).getString("Name"));
        edtDescription.setText( parseObject.getParseObject(parseObject.getObjectId()).getString("Descripton"));
        if (parseObject.getParseObject(parseObject.getObjectId()).getBoolean("Seen") == true) {
            checkSeen.isChecked();
        }


    }

    //update chat
    public void updateChat() {
        Intent intent = new Intent(NewChat.this, MainActivity.class);
        String name = edtNameChat.getText().toString();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Chats");
        query.getInBackground(parseObject.getObjectId(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject chats, ParseException e) {
                Date time = Calendar.getInstance().getTime();
                SimpleDateFormat ft = new SimpleDateFormat("hh:mm");
                 ParseObject Chats = new ParseObject("Chats");

                if (name.equals("")) {
                    vibrate();
                    Toast.makeText(NewChat.this, "please set a name ! ", Toast.LENGTH_SHORT).show();
                } else {
                    if (getTitle().toString().contains("Pv")) {
                        chats.put("Name", "0" + edtNameChat.getText().toString());
                    } else if (getTitle().toString().contains("Group")) {
                        chats.put("Name", "1" + edtNameChat.getText().toString());
                    } else if (getTitle().toString().contains("Channel")) {
                        chats.put("Name", "2" + edtNameChat.getText().toString());
                    }
                    intent.putExtra("isCreating", chats.getString("Name"));

                    chats.put("Time", ft.format(time).toString());
                    chats.put("Descripton", edtDescription.getText().toString());
                    chats.put("Seen", checkSeen.isChecked());
                    if (receivedImageBitmap != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bytes = stream.toByteArray();
                        ParseFile parseFile = new ParseFile("img.png", bytes);
                        chats.put("Profile", parseFile);
                    }
                    chats.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), chats.get("Name").toString().substring(1) + "is Edited successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NewChat.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), e.getCode() + "" + e.getStackTrace()[0], Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

    }


    //NewChat
    public void newChat() {

        Intent intent = new Intent(NewChat.this, MainActivity.class);
        String name = edtNameChat.getText().toString();

        //time
        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm");
        ParseObject Chats = new ParseObject("Chats");
        //prefix
        if (name.equals("")) {
            vibrate();
            Toast.makeText(NewChat.this, "please set a name ! ", Toast.LENGTH_SHORT).show();
        } else {
            if (getTitle().toString().contains("Pv")) {
                Chats.put("Name", "0" + edtNameChat.getText().toString());
            } else if (getTitle().toString().contains("Group")) {
                Chats.put("Name", "1" + edtNameChat.getText().toString());
            } else if (getTitle().toString().contains("Channel")) {
                Chats.put("Name", "2" + edtNameChat.getText().toString());
            }
            intent.putExtra("isCreating", Chats.getString("Name"));
            if (!edtDescription.getText().toString().matches(""))
                Chats.put("Descripton", edtDescription.getText().toString());
            if (getTitle().toString().contains("Pv"))
                Chats.put("Seen", checkSeen.isChecked());
            if (receivedImageBitmap != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();
                ParseFile parseFile = new ParseFile("img.png", bytes);
                Chats.put("Profile", parseFile);
                intent.putExtra("haveProf", true);
            }
            Chats.put("Time", ft.format(time).toString());
            startActivity(intent);
            Thread thread = new Thread() {
                @Override
                public void run() {
                    Chats.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), Chats.get("Name").toString().substring(1) + "is saved successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), e.getCode() + " -> " + e.getStackTrace()[0], Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    });
                }
            };
            thread.start();

        }
    }


    public void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(300);
        }
    }

//TODO : set to line 162

// the imgPro click does these:

    public void showBottomSheet(){
        BottomSheetDialogPro bottomSheetDialogPro = new BottomSheetDialogPro();
        bottomSheetDialogPro.show(getSupportFragmentManager() , "bottom sheet");

        Intent intent1 = new Intent();
        intent1.putExtra("bitmap" , receivedImageBitmap);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle.getString("prefixR") != null){
            receivedImageBitmap = null;
            imgCamera.setVisibility(View.VISIBLE);

        }else if (bundle.getString("prefixN") != null){

            if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(NewChat.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                            1000
                    );
                } else {
                    getChosenImage();
                }

        }else if (bundle.getString("prefixE") != null){
//            if (resultCode == Activity.RESULT_OK) {
                try {
                    receivedImageBitmap = BitMapHolder.getBitMapHolder().getBitmap();
                    imgProfile.setImageBitmap(receivedImageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//            }
        }

    }

}


