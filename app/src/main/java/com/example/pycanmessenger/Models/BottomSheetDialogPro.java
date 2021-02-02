package com.example.pycanmessenger.Models;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pycanmessenger.Activity.AddNew;
import com.example.pycanmessenger.Activity.NewChat;
import com.example.pycanmessenger.Activity.ProfileEditor;
import com.example.pycanmessenger.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;



public class BottomSheetDialogPro extends BottomSheetDialogFragment {
    TextView txtRemove , txtNew , txtEdit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.buttom_sheet_dialog_pro , container , false);
        txtRemove = v.findViewById(R.id.txtRemovePro);
        txtEdit = v.findViewById(R.id.txtEditPro);
        txtNew = v.findViewById(R.id.txtNewPro);
        txtNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("prefixN", "new");
                txtEdit.setOnClickListener(null); txtRemove.setOnClickListener(null);
                dismiss();
            }
        });
        txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("prefixE", "edit");
                txtNew.setOnClickListener(null); txtRemove.setOnClickListener(null);
                //TODO : go to ProfileEdit & get bit map & send to Editor
                Bundle bundle1 = intent.getExtras();
                Byte img;
                if (bundle1.getString("bitmap") != null){
                    img = bundle1.getByte("bitmap");
                }
                dismiss();
            }
        });
        txtRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("prefixR", "Remove");
                txtNew.setOnClickListener(null); txtEdit.setOnClickListener(null);
                dismiss();
            }
        });

        return v;
    }

}
