package com.sailesh.weatherapp.Network;

import android.app.Activity;
import android.content.Context;

import com.sailesh.weatherapp.Common.CustomProgressbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pankaj on 5/16/17.
 */
abstract class BaseCallBack<T> {

    public abstract void onResponseCustom(Call<T> call, Response<T> response);

    public abstract void onFailureCustom(Call<T> call, Throwable t);
}

public abstract class RetroCustomCallback<T> extends BaseCallBack<T> implements Callback<T> {
    Context mContext;
    boolean showProgres;
    CustomProgressbar pDialog;
    Activity mActivity;

    public RetroCustomCallback(Context mContext, boolean showProgres, Activity activity) {
        this.mContext = mContext;
        this.showProgres = showProgres;
        this.mActivity = activity;
        try {
            if (CustomProgressbar.progressDialog != null && CustomProgressbar.progressDialog.isShowing())
                CustomProgressbar.progressDialog.dismiss();
            if (showProgres) {
                CustomProgressbar.progressDialog = CustomProgressbar.showProgressDialog(mActivity);
                CustomProgressbar.progressDialog.show();
                CustomProgressbar.progressDialog.setCancelable(false);
            }
        } catch (Exception ex) {
        }


    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        System.out.println("Response Code is == " + response.code());

        if (CustomProgressbar.progressDialog != null && CustomProgressbar.progressDialog.isShowing()) {
            CustomProgressbar.progressDialog.dismiss();
        }

        onResponseCustom(call, response);


   }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (CustomProgressbar.progressDialog != null && CustomProgressbar.progressDialog.isShowing()) {
            CustomProgressbar.progressDialog.dismiss();
        }

        onFailureCustom(call, t);

    }


}