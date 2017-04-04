package com.suryapavan.gopetting.challengecode.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.suryapavan.gopetting.challengecode.R;


/**
 * Created by surya on 4/4/2017.
 */


public class LoadDialogNoInternet {
    Dialog dialog;
    TextView textView,textViewHeading,textClose;
    public void LoadDialog(final Context context, String heading, String matter) {

        dialog = new Dialog(context, R.style.MyDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        dialog.setContentView(R.layout.loading_dialog_1);
        dialog.show();

        textView = (TextView) dialog.findViewById(R.id.textview);
        textViewHeading = (TextView) dialog.findViewById(R.id.cdd);
        textClose = (TextView) dialog.findViewById(R.id.DismissDialogButton);


        textView.setText(""+matter);
        textViewHeading.setText(""+heading);
        textClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }
    public void DismissDialog(){
        dialog.dismiss();
    }
}
