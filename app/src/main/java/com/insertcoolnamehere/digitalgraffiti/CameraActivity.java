package com.insertcoolnamehere.digitalgraffiti;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.*;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class CameraActivity extends AppCompatActivity {
    CameraDevice mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            manager.openCamera(manager.getCameraIdList()[0], new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice cameraDevice) {

                }

                @Override
                public void onDisconnected(@NonNull CameraDevice cameraDevice) {

                }

                @Override
                public void onError(@NonNull CameraDevice cameraDevice, int i) {

                }
            }, new Handler());

            try {
                String cameraId = manager.getCameraIdList()[0];
            } catch (Exception e) {
                Log.e(getString(R.string.app_name), "failed at getcameraid");
                e.printStackTrace();
            }
        }
        else {
            requestPermissions(new String[] {Manifest.permission.CAMERA}, 0);
        }
    }

    private boolean safeCameraOpen(int id) {
        boolean qOpened = false;

        try {
            releaseCameraAndPreview();
            mCamera = Camera.open(id);
            qOpened = (mCamera != null);
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }

        return qOpened;
    }

    private void releaseCameraAndPreview() {
        //mPreview.setCamera(null); //todo unresolved method
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

}


