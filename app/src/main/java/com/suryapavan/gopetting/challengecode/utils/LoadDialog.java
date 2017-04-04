package com.suryapavan.gopetting.challengecode.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.suryapavan.gopetting.challengecode.R;


/**
 * Created by surya on 4/4/2017.
 */


public class LoadDialog {
    Dialog dialog;
    ProgressBar progressBar;

    public void LoadDialog(Context context) {

        dialog = new Dialog(context, R.style.MyDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.loading_dialog);
        dialog.show();

        progressBar = (ProgressBar) dialog.findViewById(R.id.main_progress);
        progressBar.setVisibility(View.VISIBLE);
    }
    public void DismissDialog(){
        dialog.dismiss();
    }
}
