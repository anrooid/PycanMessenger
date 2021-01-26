package com.example.pycanmessenger.Models;

import android.graphics.Bitmap;

public class BitMapHolder {

    private static BitMapHolder bitMapHolder;
    private  Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private BitMapHolder() { }

    public static BitMapHolder getBitMapHolder() {
        if (bitMapHolder == null) {
            bitMapHolder= new BitMapHolder();
        }
        return bitMapHolder;
    }
}