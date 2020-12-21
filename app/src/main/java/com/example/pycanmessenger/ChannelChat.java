package com.example.pycanmessenger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ChannelChat extends Fragment {

    private RecyclerView myRecycler;
    private ChatAdapter mAdapter;
    private TextView mText ;
    private ProgressBar mProgress;


    public ChannelChat(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view  = inflater.inflate(R.layout.fragment_channel_chat, container, false);
         myRecycler = view.findViewById(R.id.RV_channel);
         mText = view.findViewById(R.id.Error);
         mProgress = view.findViewById(R.id.loadProg);
         myRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        ParseQuery<ParseObject> objects = ParseQuery.getQuery("Chats");
        objects.whereStartsWith("Name","2");
        objects.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    mProgress.setVisibility(View.GONE);
                    if (objects.size()>0){
                        mAdapter = new ChatAdapter(objects,getContext());
                        myRecycler.setAdapter(mAdapter);
                    }else {
                        // show a text to user that no chats yet !
                        mText.setVisibility(View.VISIBLE);
                    }
                }else {
                    e.printStackTrace();
                }
            }
        });

        return view ;
    }
}