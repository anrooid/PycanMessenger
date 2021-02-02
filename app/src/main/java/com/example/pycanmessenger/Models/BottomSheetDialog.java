package com.example.pycanmessenger.Models;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pycanmessenger.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.parse.ParseObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    private ParseObject parseObject;

    public BottomSheetDialog(BottomSheetListener mListene , ParseObject parseObject ) {
        this.mListener = mListener;
        this.parseObject = parseObject;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.bottom_sheet_layout,container,false);

        Button btnD = view.findViewById(R.id.editBtn);
        Button btnE = view.findViewById(R.id.deleteBtn);
        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked(1 , parseObject);
                dismiss();
            }
        });
        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked(2 , parseObject);
                dismiss();
            }
        });
        return view;
    }
    public interface BottomSheetListener{
        void onButtonClicked(int a , ParseObject parseObject);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must implement BottomSheetListener");
        }

    }
}
