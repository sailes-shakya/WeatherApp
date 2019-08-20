package com.sailesh.weatherapp.View.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.sailesh.weatherapp.Common.CustomProgressbar;
import com.sailesh.weatherapp.Common.PermissionManager;
import com.sailesh.weatherapp.Model.WeatherModel;
import com.sailesh.weatherapp.Presenter.MainActivityPresenter;
import com.sailesh.weatherapp.R;
import com.sailesh.weatherapp.View.Adaptor.WeatherListAdaptor;
import com.sailesh.weatherapp.databinding.ActivityMainBinding;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private PermissionManager permissionsManager;
    private FusedLocationProviderClient mFusedLocationClient;
    MainActivityPresenter activityPresenter;
    private boolean showProgres=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recWethear.setLayoutManager(new LinearLayoutManager(this));

        activityPresenter = new MainActivityPresenter(this);
        requestloation();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        permissionsManager = new PermissionManager(Manifest.permission.ACCESS_FINE_LOCATION);

        permissionsManager.setRationaleListener(new PermissionManager.OnPermissionRationaleListener() {
            @Override
            public void onShowRationale() {
                permissionsManager.requestPermissionThenInvoke(MainActivity.this);
            }
        });
        permissionsManager.setStatusListener(new PermissionManager.OnPermissionStatusListener() {
            @Override
            public void onGranted() {
                getLocation();
                Log.d("tag", "call location api");
            }

            @Override
            public void onDenied() {

            }
        });
        permissionsManager.invokeWithOrRequestPermissionThenInvoke(this);
        activityPresenter.setObeserve(new MainActivityPresenter.Obeserve() {
            @Override
            public void sucess(WeatherModel model) {
                WeatherListAdaptor weatherListAdaptor = new WeatherListAdaptor(MainActivity.this,model);
                weatherListAdaptor.setOnclick(new WeatherListAdaptor.Onclick() {
                    @Override
                    public void send(WeatherModel model, int postion) {
                        Intent intent=new Intent(MainActivity.this,DetailActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("data", (Serializable) model);
                        bundle.putInt("postion",postion);
                        intent.putExtras(bundle);
                        MainActivity.this.startActivity(intent);
                    }
                });
                binding.recWethear.setAdapter(weatherListAdaptor);
            }

            @Override
            public void fail(String message) {

            }
        });



    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LocationRequest.PRIORITY_HIGH_ACCURACY:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        permissionsManager.invokeWithOrRequestPermissionThenInvoke(this);
                        break;
                    case Activity.RESULT_CANCELED:

                        break;
                    default:
                        break;
                }
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @SuppressLint("MissingPermission")
    private void getLocation() {


        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    activityPresenter.getlist(location);
                    Log.d("location", "onSuccess: "+location.getLatitude());

                }else {
                    final Handler handler = new Handler();
                   // Toast.makeText(MainActivity.this,"Location null",Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            permissionsManager.invokeWithOrRequestPermissionThenInvoke(MainActivity.this);
                        }
                    }, 600);

                }
            }
        });
        }


       void requestloation()
       {
           LocationRequest locationRequest = LocationRequest.create();
           locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
           LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                   .addLocationRequest(locationRequest);
          Task<LocationSettingsResponse> result =
                   LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());

           result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
               @Override
               public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                   try {
                       LocationSettingsResponse response = task.getResult(ApiException.class);
                       // All location settings are satisfied. The client can initialize location
                       // requests here.
                   } catch (ApiException exception) {
                       switch (exception.getStatusCode()) {
                           case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                               try {

                                   ResolvableApiException resolvable = (ResolvableApiException) exception;

                                   resolvable.startResolutionForResult(
                                           MainActivity.this,
                                           LocationRequest.PRIORITY_HIGH_ACCURACY);
                               } catch (IntentSender.SendIntentException e) {

                               } catch (ClassCastException e) {

                               }
                               break;
                           case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                               break;
                       }
                   }
               }
           });
       }
    }
