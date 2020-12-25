package com.example.pycanmessenger;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class GroupChat extends Fragment {

    private RecyclerView gRecyclerView;
    private TextView gError;
    private ProgressBar gProgressBar;
    private ChatAdapter gAdapter;


    public GroupChat(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_group_chat, container, false);
        gRecyclerView = view.findViewById(R.id.RV_Group);
        gError = view.findViewById(R.id.errorGroup);
        gProgressBar = view.findViewById(R.id.gProgress);
        gRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ParseQuery<ParseObject> object = ParseQuery.getQuery("Chats");
        object.whereStartsWith("Name" , "1");
        object.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    gProgressBar.setVisibility(View.GONE);
                    if (objects.size()>0){
                        gAdapter= new ChatAdapter(objects,getContext());
                        gRecyclerView.setAdapter(gAdapter);
                    }else {
                        gError.setVisibility(View.VISIBLE);
                    }

                }else {
                    e.printStackTrace();                }
            }
        });
        return view;
    }
    //menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.menu_menu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                gAdapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onStart() {
        setHasOptionsMenu(true);
        super.onStart();
    }
}