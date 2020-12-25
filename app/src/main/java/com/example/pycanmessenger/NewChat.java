package com.example.pycanmessenger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.sql.Time;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.security.AccessController.getContext;

public class NewChat extends AppCompatActivity {


    private CheckBox checkSeen;
    Boolean seen, PV, Group, Channel;
    private ImageButton btnNewChat;
    private ImageView imgProfile;
    EditText edtNameChat, edtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        PV = false; Group = false; Channel = false;
            seen = false;
            checkSeen = findViewById(R.id.checkSeen);
            imgProfile = findViewById(R.id.imgProfile);
            edtNameChat = findViewById(R.id.edtNameChat);
            edtDescription = findViewById(R.id.edtDescription);
            btnNewChat = findViewById(R.id.btnNewChat);
            ParseUser Chats = ParseUser.getCurrentUser();

        //pv OR group OR channel
        if (bundle != null) {
            String dataC = bundle.getString("prefix");
            String dataG = bundle.getString("prefixG");
            String dataPv = bundle.getString("prefixP");

            if (dataC == bundle.getString("prefix", "prefixC")) {
                Channel = true; PV = false; Group = false;

            } else if (dataG == bundle.getString("prefixG", "prefixG")) {
                Group = true; PV = false; Channel = false;

            }else if (dataPv == bundle.getString("prefixP", "prefixP")) {
                PV = true; Group = false; Channel = false;

            }
        }

            //time
            Date time = Calendar.getInstance().getTime();
            ;
            SimpleDateFormat ft = new SimpleDateFormat("hh:mm");

            //seen
            if (checkSeen.isChecked()) {
                seen = true;
            } else seen = false;


            // create new chat
            btnNewChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   //prefix
                   if(PV){
                       Chats.put("Name" ,"0"+ edtNameChat.getText().toString() );
                   }else if(Group){
                     Chats.put("Name" ,"1"+ edtNameChat.getText().toString() );
                   }else if(Channel){
                     Chats.put("Name" ,"2"+ edtNameChat.getText().toString() );
                   }
                    Chats.put("Descripton", edtDescription.getText().toString());
                    Chats.put("Seen", seen);
                    Chats.put("Time", time);
                    Chats.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(NewChat.this, Chats.get("Name") + "is saved successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(NewChat.this, e.getCode() + "" + e.getStackTrace()[0], Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });


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

        private void getChosenImage () {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2000);
        }

        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode == 1000) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getChosenImage();
                }
            }
        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
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
                        Bitmap receivedImageBitmap = BitmapFactory.decodeFile(picturePath);

                        imgProfile.setImageBitmap(receivedImageBitmap);
                    } catch (Exception e) {

                    }
                }
            }

        }

}
