package com.example.pycanmessenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.MyViewHolder> {

   List<ParseObject> parseObjects ;
    Context mContext;
    MainActivity.ChatKind kinds;

    public chatAdapter(List<ParseObject> parseObjects , MainActivity.ChatKind kinds, Context mContext) {
        this.mContext = mContext;
        this.parseObjects = parseObjects;
        this.kinds= kinds;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View aView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(aView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ParseObject chat = parseObjects.get(position);


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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            aAvatar = itemView.findViewById(R.id.imgAvatar);
            aName = itemView.findViewById(R.id.txtName);
            aMessage = itemView.findViewById(R.id.txtMessege);
            aTime = itemView.findViewById(R.id.txtTime);
            aSeen = itemView.findViewById(R.id.imgSeen);
        }
    }
}
