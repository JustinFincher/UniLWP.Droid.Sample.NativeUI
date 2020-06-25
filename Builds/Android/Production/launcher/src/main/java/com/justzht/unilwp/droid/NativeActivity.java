package com.justzht.unilwp.droid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;

import com.justzht.unilwp.droid.databinding.ActivityNativeBinding;
import com.justzht.unity.lwp.LiveWallpaperManager;
import com.justzht.unity.lwp.LiveWallpaperPresentationEventWrapper;

public class NativeActivity extends AppCompatActivity {

    private ActivityNativeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_native);
        binding.setActivity(this);
        binding.setLifecycleOwner(this);
        // setup SurfaceView
        LiveWallpaperPresentationEventWrapper.getInstance().setupSurfaceViewInActivityOnCreate(binding.unityPlayerSurfaceView);
    }

    // callbacks
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LiveWallpaperPresentationEventWrapper.getInstance().intent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LiveWallpaperPresentationEventWrapper.getInstance().activityVisibility(true, binding.unityPlayerSurfaceView.getHolder());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LiveWallpaperPresentationEventWrapper.getInstance().onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LiveWallpaperPresentationEventWrapper.getInstance().onLowMemory();
    }

    @Override
    protected void onPause() {
        LiveWallpaperPresentationEventWrapper.getInstance().activityVisibility(false, binding.unityPlayerSurfaceView.getHolder());
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LiveWallpaperPresentationEventWrapper.getInstance().setupSurfaceViewInActivityOnDestroy(binding.unityPlayerSurfaceView);
    }

    @Override public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        LiveWallpaperPresentationEventWrapper.getInstance().configurationChanged(newConfig);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int[] windowPos = new int[2];
        binding.unityPlayerSurfaceView.getLocationInWindow(windowPos);
        return LiveWallpaperPresentationEventWrapper.getInstance().touchEvent(event, new int[]{-windowPos[0],-windowPos[1]});
    }

    // logic
    public void setLiveWallpaper()
    {
        LiveWallpaperManager.getInstance().openLiveWallpaperPreview();
    }
}