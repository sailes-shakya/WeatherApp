package com.sailesh.weatherapp.Common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Window;

import com.sailesh.weatherapp.R;


public class CustomProgressbar {
    public static Dialog progressDialog;
    public static Context ctx;
    public static int appWidth;
    public static int appHeight;
    public static float scale;
    static Activity thisActivity;


    public CustomProgressbar(Context ctx, Activity thisActivity) {
        CustomProgressbar.ctx = ctx;
        CustomProgressbar.thisActivity = thisActivity;

        DisplayMetrics dm = new DisplayMetrics();
        scale = ctx.getResources().getDisplayMetrics().density;
        thisActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        appWidth = dm.widthPixels;
        appHeight = dm.heightPixels;

    }


    public static Dialog showProgressDialog(Context thisActivity) {

        if (thisActivity == null) {

            CustomProgressbar.progressDialog.isShowing();
            CustomProgressbar.progressDialog.dismiss();

        }

        Dialog customProgressDialog = null;

        customProgressDialog = new Dialog(thisActivity);
        customProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        customProgressDialog.setContentView(R.layout.custom_progress_dialog_box);
        customProgressDialog.setCancelable(false);
        // for Rounded Corner background Transparent
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return customProgressDialog;


    }
}
