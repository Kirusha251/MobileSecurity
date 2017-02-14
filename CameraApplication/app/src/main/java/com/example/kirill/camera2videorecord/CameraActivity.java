package com.example.kirill.camera2videorecord;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Toast;

import java.util.Arrays;

public class CameraActivity extends AppCompatActivity {private TextureView mTextureView;
    private CameraDevice mCameraDevice;
    private String mCameraId;
    private HandlerThread mBackgroundHandlerThread;
    private Handler mBackgroundHandler;
    private CaptureRequest.Builder mCaptureRequestBuilder;
    private CameraDevice.StateCallback mCameraDeviceStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            mCameraDevice = camera;
            startPreview();
            Toast.makeText(getApplicationContext(),"Camera connecting",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            camera.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            camera.close();
            mCameraDevice = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mTextureView = (TextureView)findViewById(R.id.textureView);
    }
    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            setupCamera(width,height);
            connectCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundThread();

        if(mTextureView.isAvailable()){
            setupCamera(mTextureView.getWidth(), mTextureView.getHeight());
            connectCamera();
        }else{
            mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }

    @Override
    protected void onPause() {

        stopBackgroundThread();
        super.onPause();
    }

    private void setupCamera(int width, int height){
        CameraManager cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try{
            for(String cameraId : cameraManager.getCameraIdList()){
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                if(cameraCharacteristics.get(CameraCharacteristics.LENS_FACING)==
                        CameraCharacteristics.LENS_FACING_FRONT){
                    continue;
                }
                mCameraId = cameraId;
                return;
            }
        }catch (Exception e){

        }
    }
    private void connectCamera(){
        CameraManager cameraManager =(CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED) {
                cameraManager.openCamera(mCameraId, mCameraDeviceStateCallback, mBackgroundHandler);
            }
        }catch (CameraAccessException e){

        }
    }
    private void startPreview(){
        SurfaceTexture surfaceTexture = mTextureView.getSurfaceTexture();
        surfaceTexture.setDefaultBufferSize(500,400);
        Surface previewSurface = new Surface(surfaceTexture);
        try {
            mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mCaptureRequestBuilder.addTarget(previewSurface);

            mCameraDevice.createCaptureSession(Arrays.asList(previewSurface),
                    new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(CameraCaptureSession session) {
                            try{
                                session.setRepeatingRequest(mCaptureRequestBuilder.build(),
                                        null,mBackgroundHandler);
                            }catch (CameraAccessException e){

                            }

                        }

                        @Override
                        public void onConfigureFailed(CameraCaptureSession session) {

                        }
                    },null);
        }catch (CameraAccessException e){

        }

    }
    private void startBackgroundThread(){
        mBackgroundHandlerThread = new HandlerThread(CameraActivity.class.toString());
        mBackgroundHandlerThread.start();
        mBackgroundHandler = new Handler(mBackgroundHandlerThread.getLooper());
    }
    private void stopBackgroundThread(){
        mBackgroundHandlerThread.quitSafely();
        try {
            mBackgroundHandlerThread.join();
            mBackgroundHandlerThread = null;
            mBackgroundHandler = null;
        }catch (InterruptedException e){

        }
    }
}
