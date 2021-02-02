package com.example.pycanmessenger.Fregments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pycanmessenger.Activity.NewChat;
import com.example.pycanmessenger.Adapters.ChatAdapter;
import com.example.pycanmessenger.Models.interfaces.OnItemClickListener;
import com.example.pycanmessenger.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class GroupChat extends Fragment {

    private RecyclerView gRecyclerView;
    private ProgressBar gProgressBar;
    private ChatAdapter gAdapter;

    //errors
    private TextView erTitle , erText , erHelp ;
    private ImageView erLogo;

    public GroupChat(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_group_chat, container, false);
        gRecyclerView = view.findViewById(R.id.RV_Group);
        erLogo = view.findViewById(R.id.errorLogo);
        erTitle = view.findViewById(R.id.errorTitle);
        erText = view.findViewById(R.id.errorText);
        erHelp = view.findViewById(R.id.errorHelp);
        gProgressBar = view.findViewById(R.id.gProgress);
        gRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ParseQuery<ParseObject> object = ParseQuery.getQuery("Chats");
        object.whereStartsWith("Name" , "1");
        object.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    gProgressBar.setVisibility(View.GONE);
                    if (objects.size() <= 0) {
                        erLogo.setVisibility(View.VISIBLE);erLogo.animate().translationY(0);
                        erTitle.setVisibility(View.VISIBLE);erTitle.animate().translationY(0);
                        erText.setVisibility(View.VISIBLE);erText.animate().translationY(0);
                        erHelp.setVisibility(View.VISIBLE);erHelp.animate().translationY(0);
                    }
                    gAdapter= new ChatAdapter(objects, getContext(), new OnItemClickListener() { // Todo : implement log & on click
                        @Override
                        public boolean onItemClick(ParseObject parseObject) {
                            return false;
                        }

                        @Override
                        public boolean onLongItemClick(ParseObject parseObject) {
                            return false;
                        }
                    });
                    gRecyclerView.setAdapter(gAdapter);
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

    private void delete(ParseObject parseObject) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Chats");
        query.getInBackground(parseObject.getObjectId(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e ==null){
                    object.deleteInBackground();
                    // delete also in RV
                }
            }
        });
    }
    private void edit(ParseObject parseObject) {
        Intent intent = new Intent(getContext() , NewChat.class);
        intent.putExtra("open for edit" ,parseObject );
        startActivity(intent);
    }
}