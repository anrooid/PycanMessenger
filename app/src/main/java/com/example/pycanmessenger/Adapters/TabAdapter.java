package com.example.pycanmessenger.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.pycanmessenger.Fregments.AllChat;
import com.example.pycanmessenger.Fregments.ChannelChat;
import com.example.pycanmessenger.Fregments.GroupChat;
import com.example.pycanmessenger.Fregments.PvChat;
import com.example.pycanmessenger.Models.interfaces.OnItemClickListener;

public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(@NonNull FragmentManager fm ) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                AllChat allChat = new AllChat();
                return allChat;
            case 1:
                PvChat pvChat = new PvChat();
                return pvChat;
            case 2:
                GroupChat groupChat = new GroupChat();
                return groupChat;
            case 3:
                ChannelChat channelChat = new ChannelChat();
                return channelChat;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "All";
            case 1:
                return "Pv";
            case 2:
                return "Group";
            case 3:
                return "Channel";
            default:
                return null;
        }
    }
}
