package com.example.pycanmessenger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class AllChat extends Fragment {
    RecyclerView myRecycler;
    List<Item> mItem = new ArrayList<>();
    chatAdapter mAdapter;

    public AllChat(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_all_chat, container, false);
        myRecycler = view.findViewById(R.id.recycler_view);
        mAdapter = new chatAdapter(mItem,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        myRecycler.setLayoutManager(mLayoutManager);
        myRecycler.setAdapter(mAdapter);
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Chats");
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

            }
        });
        setData();


        return view;

    }
    private void setData(){
        mItem.add(new Item(R.drawable.cat, "mahdi","berim dasht" , "12:02"));
        mItem.add(new Item(R.drawable.ic_launcher_background, "hfd","jgffdfhh dasht" , "06:02"));
        mItem.add(new Item(R.drawable.mahdi, "recd","hgnngg dasht" , "14:02"));

        mAdapter.notifyDataSetChanged();
    }
}