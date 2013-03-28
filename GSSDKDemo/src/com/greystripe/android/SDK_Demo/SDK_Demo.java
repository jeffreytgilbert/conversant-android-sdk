package com.greystripe.android.SDK_Demo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.greystripe.android.GSSDKDemo.R;
import com.greystripe.sdk.*;

public class SDK_Demo extends Activity {
    private static final String LOG_TAG = "GS_SDK_DEMO";
    
    // UI Elements that need to be shared at this level
    private static GSFullscreenAd myFullscreenAd;
    private static GSMobileBannerAdView myBannerAd;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "Main activity created.");
        super.onCreate(savedInstanceState);
        
        // Set up the UI
        setContentView(R.layout.main);
        
        // Create ad object instances
        myFullscreenAd = new GSFullscreenAd(this);
        
        //Get the banner included in the layout.xml
        myBannerAd = (GSMobileBannerAdView) this.findViewById(R.id.gsBanner);
        
        // Add listeners for the ad objects
        myBannerAd.addListener(new MyBannerListener());
        myFullscreenAd.addListener(new MyFullscreenListener());
    }
    
    /**
     * Handle configuration changes (e.g. rotation)
     */
    @Override
    public void onConfigurationChanged(Configuration cfg) {
        Log.d(LOG_TAG, "Configuration changed.");
        super.onConfigurationChanged(cfg);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOG_TAG, String.format("Activity result: (%d, %d, %s)",
                requestCode, resultCode, data));
    }
    
    /**
     * Click handler for button to request a fullscreen Ad from the server
     * 
     * @param v
     */
    public void fetchFullscreenClicked(View v) {
        Log.d(LOG_TAG, "Fetch fullscreen clicked.");
        if (!myFullscreenAd.isAdReady()) {
            myFullscreenAd.fetch();
        } else {
            Toast.makeText(SDK_Demo.this.getApplicationContext(),
                    "Ad ready!  Display it!", Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * Click handler for button to request a fullscreen Ad be displayed
     * 
     * @param v
     */
    public void displayFullscreenClicked(View v) {
        Log.d(LOG_TAG, "Fullscreen display clicked.");
        
        if (!myFullscreenAd.isAdReady()) {
            Toast.makeText(SDK_Demo.this.getApplicationContext(),
                    "Ad not ready!", Toast.LENGTH_SHORT).show();
        } else {
            myFullscreenAd.display();
        }
    }
    
    /**
     * Click handler for refreshing a banner
     * 
     * @param v
     */
    public void refreshBannerClicked(View v) {
        Log.d(LOG_TAG, "Banner refresh clicked.");
        myBannerAd.refresh();
    }
    
    /**
     * Simple listener for Greystripe SDK events from the banner
     */
    private class MyBannerListener implements GSAdListener {
        
        @Override
        public void onFailedToFetchAd(GSAd ad, final GSAdErrorCode error) {
            String errorString = error.toString();
            Toast.makeText(SDK_Demo.this.getApplicationContext(), errorString,
                    Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onAdClickthrough(GSAd ad) {
            Toast.makeText(SDK_Demo.this.getApplicationContext(),
                    "Banner ad clicked!", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onAdDismissal(GSAd ad) {
            Toast.makeText(SDK_Demo.this.getApplicationContext(),
                    "Banner click-through dismissed!", Toast.LENGTH_SHORT)
                    .show();
        }
        
        @Override
        public void onFetchedAd(GSAd ad) {
            Toast.makeText(SDK_Demo.this.getApplicationContext(),
                    "Banner fetched!", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onAdExpansion(GSAd ad) {
            Toast.makeText(SDK_Demo.this.getApplicationContext(),
                    "Ad Expanded!", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onAdCollapse(GSAd ad) {
            Toast.makeText(SDK_Demo.this.getApplicationContext(),
                    "Ad Collapsed!", Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * Simple listener for Greystripe SDK events from the fullscreen ad
     */
    private class MyFullscreenListener implements GSAdListener {
        
        @Override
        public void onFailedToFetchAd(GSAd ad, final GSAdErrorCode error) {
            String errorString = error.toString();
            Toast.makeText(SDK_Demo.this.getApplicationContext(), errorString,
                    Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onAdClickthrough(GSAd ad) {
            Toast.makeText(SDK_Demo.this.getApplicationContext(),
                    "Fullscreen clicked!", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onAdDismissal(GSAd ad) {
            Toast.makeText(SDK_Demo.this.getApplicationContext(),
                    "Fullscreen dismissed!", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onFetchedAd(GSAd ad) {
            Toast.makeText(SDK_Demo.this.getApplicationContext(),
                    "Fullscreen fetched!", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onAdExpansion(GSAd ad) {
            Toast.makeText(SDK_Demo.this.getApplicationContext(),
                    "Ad Expanded!", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onAdCollapse(GSAd ad) {
            Toast.makeText(SDK_Demo.this.getApplicationContext(),
                    "Ad Collapsed!", Toast.LENGTH_SHORT).show();
        }
    }
}
