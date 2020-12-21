package com.example.pycanmessenger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.security.PrivateKey;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<ParseObject> parseObjects;
    private Context mContext;

    public ChatAdapter(List<ParseObject> parseObjects, Context mContext) {
        this.mContext = mContext;
        this.parseObjects = parseObjects;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View aView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(aView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = "";
        String description = "";
        String time = "";
        boolean seen = false;
        String profileTxt = "";
        ParseFile profile;
        ParseObject chat = parseObjects.get(position);
        if (chat.getString("Name") == null) {
            Toast.makeText(mContext, "Error : No Name Found \n please check your network connection", Toast.LENGTH_SHORT).show();
        } else {
            name = chat.getString("Name").substring(1);
            holder.getaName().setText(name);
        }
        if (chat.get("Descripton") == null) {
            holder.getaMessage().setText("No Chat Yet");
        } else {
            description = chat.getString("Descripton");
            holder.getaMessage().setText(description);
        }
        if (chat.get("Time") == null) {
            holder.getaTime().setText("00:00");
        } else {
            time = chat.getString("Time");
            holder.getaTime().setText(time);
        }
        if (chat.get("Seen") == null) {

        } else {
            seen = chat.getBoolean("Seen");
            if (seen) {
                holder.getaSeen().setVisibility(View.VISIBLE);
            } else {
                holder.getaSeen().setVisibility(View.GONE);
            }


        }
        if (chat.get("Profile") == null) {
            String[] seperated = name.split(" ");
            if (seperated.length > 0) {
                String split = seperated[0].charAt(0) + seperated[1].charAt(0) + "";
                holder.getaOnprofile().setText(split);
            } else {
                String split = name.charAt(0) + "";
                holder.getaOnprofile().setText(split);
            }
        } else {
            profile = chat.getParseFile("Profile");
            profile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (e == null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                        holder.getaAvatar().setImageBitmap(bitmap);
                    }
                }
            });
        }
        //ParseFile avatar = chat.getParseFile("Profile");
        //holder.getaName().setText(name);
        //holder.getaMessage().setText(description);
        //holder.getaTime().setText(time);
        //holder.getaSeen().setImageResource(seen?1:0);
        //holder.getaAvatar().setImageBitmap(avatar);
    }

    @Override
    public int getItemCount() {
        return parseObjects.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView aAvatar;
        private ImageView aSeen;
        private TextView aName;
        private TextView aMessage;
        private TextView aTime;
        private TextView aOnprofile;

        public ImageView getaAvatar() {
            return aAvatar;
        }

        public ImageView getaSeen() {
            return aSeen;
        }

        public TextView getaName() {
            return aName;
        }

        public TextView getaMessage() {
            return aMessage;
        }

        public TextView getaTime() {
            return aTime;
        }

        public TextView getaOnprofile() {
            return aOnprofile;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            aAvatar = itemView.findViewById(R.id.imgAvatar);
            aName = itemView.findViewById(R.id.txtName);
            aMessage = itemView.findViewById(R.id.txtMessege);
            aTime = itemView.findViewById(R.id.txtTime);
            aSeen = itemView.findViewById(R.id.imgSeen);
            aOnprofile = itemView.findViewById(R.id.Onprofile);
        }
    }
}
