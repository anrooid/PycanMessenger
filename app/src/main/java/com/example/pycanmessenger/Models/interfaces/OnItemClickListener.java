package com.example.pycanmessenger.Models.interfaces;

import com.parse.ParseObject;

public interface OnItemClickListener {
    public boolean onItemClick(ParseObject parseObject);

    public boolean onLongItemClick(ParseObject parseObject);
}