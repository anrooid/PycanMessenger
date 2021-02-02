package com.example.pycanmessenger.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pycanmessenger.Models.interfaces.OnItemClickListener;
import com.example.pycanmessenger.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> implements Filterable {

    private List<ParseObject> parseObjects;
    private List<ParseObject> parseObjectsAll;
    private Context mContext;
    private OnItemClickListener mListener;



    public ChatAdapter(List<ParseObject> parseObjects, Context mContext , OnItemClickListener listener) {
        this.mContext = mContext;
        this.parseObjects = parseObjects;
        this.parseObjectsAll = new ArrayList<>(parseObjects);
        this.mListener = listener;
    }
    private void deleteItem(int position) {
        parseObjects.remove(position);
        parseObjectsAll.remove(parseObjects.get(position));
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, parseObjects.size());
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View aView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(aView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = "No Name";
        ParseObject chat = parseObjects.get(position);
        if (chat.getString("Name") != null) {
            name = chat.getString("Name").trim().substring(1);
        }
        holder.getaName().setText(name);
        if (chat.get("Descripton") == null || chat.getString("Descripton").matches("") ) {
            holder.getaMessage().setText("No Chat Yet");
        } else {
            holder.getaMessage().setText(chat.getString("Descripton"));
        }
        if (chat.get("Time") == null) {
            holder.getaTime().setText("");
        } else {
            holder.getaTime().setText(chat.getString("Time"));
        }
        if (chat.get("Seen") != null) {
            if (chat.getBoolean("Seen")) {
                holder.getaSeen().setVisibility(View.VISIBLE);
            } else {
                holder.getaSeen().setVisibility(View.GONE);
            }
        }
        if (chat.get("Profile") == null) {
            String c2 = "", c1 = String.valueOf(name.charAt(0));
            if (name.contains(" "))
                c2 = String.valueOf(name.charAt(name.indexOf(" ") + 1));
            holder.getaOnprofile().setText(c1 + c2);
        } else {
            chat.getParseFile("Profile")
                    .getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                holder.getaOnprofile().setText("");
                                holder.getaAvatar().setImageBitmap(bitmap);
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return parseObjects.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ParseObject> filteredList = new ArrayList<>();
            if (parseObjectsAll != null && parseObjectsAll.size() != 0) {
                if (charSequence.toString().isEmpty()) {
                    filteredList.addAll(parseObjectsAll);

                } else {
                    for (ParseObject SparseObject : parseObjectsAll) {
                        if (SparseObject.getString("Name").toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filteredList.add(SparseObject);
                        }
                    }
                }
            } else {
                Toast.makeText(mContext, "Not able to Search on a empty list !", Toast.LENGTH_SHORT).show();
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            parseObjects.clear();
            parseObjects.addAll((Collection<? extends ParseObject>) filterResults.values);
            notifyDataSetChanged();
        }
    };

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

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            aAvatar = itemView.findViewById(R.id.imgAvatar);
            aName = itemView.findViewById(R.id.txtName);
            aMessage = itemView.findViewById(R.id.txtMessege);
            aTime = itemView.findViewById(R.id.txtTime);
            aSeen = itemView.findViewById(R.id.imgSeen);
            aOnprofile = itemView.findViewById(R.id.Onprofile);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onLongItemClick(parseObjects.get(position));

                        }
                    }
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(parseObjects.get(position));

                        }
                    }
                }
            });

        }
    }
}
