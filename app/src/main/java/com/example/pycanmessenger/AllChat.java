package com.example.pycanmessenger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class AllChat extends Fragment {
    private RecyclerView allRecycler;
    private ChatAdapter aAdapter;
    private TextView errorAll;
    private ProgressBar aProgressBar;

    public AllChat(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_all_chat, container, false);

        allRecycler = view.findViewById(R.id.RV_All);
        errorAll = view.findViewById(R.id.errorAll);
        aProgressBar = view.findViewById(R.id.AllProgress);
        allRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        ParseQuery<ParseObject> objects = ParseQuery.getQuery("Chats");
        //objects.whereStartsWith("Name","2");
        objects.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    aProgressBar.setVisibility(View.GONE);
                    if (objects.size()>0){
                        aAdapter = new ChatAdapter(objects,getContext());
                        allRecycler.setAdapter(aAdapter);
                    }else {
                        // show a text to user that no chats yet !
                        errorAll.setVisibility(View.VISIBLE);
                    }
                }else {
                    e.printStackTrace();
                }
            }
        });
        return view;

    }

}