package com.sailesh.weatherapp.Common;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Arrays;


public class PermissionManager {
    private String permissionIdentifier;
    private OnPermissionStatusListener statusListener;
    private OnPermissionRationaleListener rationaleListener;

    public PermissionManager(String permissionIdentifier) {
        this.permissionIdentifier = permissionIdentifier;
    }

    public boolean invokeWithOrRequestPermissionThenInvoke(Activity activity) {
        if (haveNoPermission(activity)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permissionIdentifier)) {
                if (rationaleListener != null) {
                    rationaleListener.onShowRationale();
                }
            } else {
                requestPermissionThenInvoke(activity);
            }

            return false;
        }

        if (statusListener != null) {
            statusListener.onGranted();
        }
        return true;
    }

    public boolean haveNoPermission(Context context) {
        return ContextCompat.checkSelfPermission(context,
                permissionIdentifier)
                != PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissionThenInvoke(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{permissionIdentifier},
                1);
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (statusListener == null || !Arrays.asList(permissions).contains(permissionIdentifier)) {
            return;
        }

        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            statusListener.onGranted();
        } else {
            statusListener.onDenied();
        }
    }

    public void setStatusListener(OnPermissionStatusListener statusListener) {
        this.statusListener = statusListener;
    }

    public void setRationaleListener(OnPermissionRationaleListener rationaleListener) {
        this.rationaleListener = rationaleListener;
    }

    public String getPermissionIdentifier() {
        return permissionIdentifier;
    }

    public interface OnPermissionStatusListener {
        void onGranted();

        void onDenied();
    }

    public interface OnPermissionRationaleListener {
        void onShowRationale();
    }
}
