package com.example.pycanmessenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

   List<ParseObject> parseObjects ;
    Context mContext;

    public ChatAdapter(List<ParseObject> parseObjects , Context mContext) {
        this.mContext = mContext;
        this.parseObjects = parseObjects;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View aView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(aView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = "" ;
        ParseObject chat = parseObjects.get(position);
        if (chat.getString("Name")==null) {
            Toast.makeText(mContext,"Error : No Name Found \n please check your network connection",Toast.LENGTH_SHORT).show();
        }else{
            name = chat.getString("Name").substring(1);
        }
        String description  = chat.getString("Description");
        String time = chat.getString("Time");
        boolean seen = chat.getBoolean("Seen");
        ParseFile avatar = chat.getParseFile("Profile");


        holder.getaName().setText(name);
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
