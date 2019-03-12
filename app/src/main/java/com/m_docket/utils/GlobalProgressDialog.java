package com.m_docket.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.m_docket.R;
import com.m_docket.utils.progress_bar.MaterialProgressBar;

public class GlobalProgressDialog {

    private static MaterialProgressBar materialProgressBar;

    private static ProgressDialog progressDialog = null;
    private static ProgressBar custom_progress_id;

    private GlobalProgressDialog() {

    }

    synchronized public static ProgressDialog getInstanceOfProgressDialog(Context context) {

        progressDialog = new ProgressDialog(context, R.style.MyTheme);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);


        return progressDialog;

    }


    synchronized public static MaterialProgressBar getInstanceOfCustomProgressDialog(Context context) {


        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_progress_bar, null, false);

        materialProgressBar = view.findViewById(R.id.progress);
        materialProgressBar.bringToFront();


        return materialProgressBar;

    }

}
