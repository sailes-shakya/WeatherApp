package com.sailesh.weatherapp.Presenter;

import android.app.Activity;
import android.content.Context;
import android.location.Location;

import com.sailesh.weatherapp.Model.WeatherModel;
import com.sailesh.weatherapp.Network.ApiInterface;
import com.sailesh.weatherapp.Network.RetroCustomCallback;
import com.sailesh.weatherapp.Network.ServiceGenerator;
import com.sailesh.weatherapp.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivityPresenter {

    Context context;
    Obeserve obeserve;
    public MainActivityPresenter(Context context) {
        this.context=context;
    }


    public  void getlist(Location location)
    {
        ApiInterface apiInterface= ServiceGenerator.createNormalService(ApiInterface.class,context);
        Call<WeatherModel> callretrofit = apiInterface.getContact(""+location.getLatitude(),""+location.getLongitude(),""+16,"6514f4dabb73b6a0c357f8f7a5bfe48d","metric");
        callretrofit.enqueue(new RetroCustomCallback<WeatherModel>(context, true, (Activity)context) {
            @Override
            public void onResponseCustom(Call<WeatherModel> call, Response<WeatherModel> response) {
                if (response.isSuccessful()) {
                    if (response.body()!=null) {

                    obeserve.sucess(response.body());
                    } else {
                        obeserve.fail("Error");
                    }
                }
            }

            @Override
            public void onFailureCustom(Call<WeatherModel> call, Throwable t) {
                obeserve.fail("Error");
            }
        });
    }
    public interface Obeserve {
        void sucess(WeatherModel bcpStep);
        void fail(String message);


    }

    public void setObeserve(Obeserve obeserve)
    {
        this.obeserve=obeserve;
    }

    }

