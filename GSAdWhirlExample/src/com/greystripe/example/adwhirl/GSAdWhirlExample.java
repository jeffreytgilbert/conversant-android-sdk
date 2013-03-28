package com.greystripe.example.adwhirl;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.widget.*;

import com.adwhirl.AdWhirlLayout;
import com.adwhirl.AdWhirlLayout.AdWhirlInterface;
import com.adwhirl.util.AdWhirlUtil;
import com.greystripe.sdk.*;

/**
 * This is an example AdWhirl implementation using Custom Events to request Greystripe ads.
 */
public class GSAdWhirlExample extends Activity implements AdWhirlInterface, GSAdListener {
    private static String TAG = "GSAdWhirlExample";

    private static GSFullscreenAd fullscreen;

    private LinearLayout layout;
    private AdWhirlLayout adWhirlLayout;
    private GSMobileBannerAdView banner;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: Set the Greystripe guid in the Android Manifest to your Greystripe app guid
        //TODO: Set the AdWhirl app id in the Android Manifest to your AdWhirl app id

        setContentView(R.layout.main);

        layout = (LinearLayout)findViewById(R.id.layout_main);

        setUpBannerAd();
        setUpFullscreenAd();

        layout.invalidate();
    }

    private void setUpBannerAd() {
        banner = new GSMobileBannerAdView(this);
        banner.addListener(this);

        int width = 320;
        int height = 50;

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float density = displayMetrics.density;

        width = (int) (width * density);
        height = (int) (height * density);

        //Please replace with your app's key.
        adWhirlLayout = new AdWhirlLayout(this, "REPLACE ME");
        adWhirlLayout.setAdWhirlInterface(this);
        adWhirlLayout.setMaxWidth(width);
        adWhirlLayout.setMaxHeight(height);

        TextView text = new TextView(this);
        text.setText("Below is an AdWhirl banner slot implemented programmatically:");
        layout.addView(text);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        layout.addView(adWhirlLayout, params);
    }

    private void setUpFullscreenAd() {
        fullscreen = new GSFullscreenAd(this);
        fullscreen.addListener(this);

        TextView text = new TextView(this);
        text.setText("A fullscreen ad will automatically appear when it is ready.");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 20;
        layout.addView(text, params);
    }

    /**
     * This is the method name provided for the custom event
     * to fetch a banner ad.
     */
    public void greystripeBanner() {
        Log.d(TAG, "Banner requested");

        if(banner == null) {
            return;
        }

        banner.refresh();

        adWhirlLayout.adWhirlManager.resetRollover();
        adWhirlLayout.rotateThreadedDelayed();
    }

    /**
     * This is the method name provided for the custom event
     * to fetch/display a fullscreen ad.
     *
     * If a fullscreen ad was cached and ready to display when
     * AdWhirl calls this method, we display it immediately.
     * Otherwise, we attempt to fetch and display it on fetch success.
     */
    public void greystripeFullscreen() {
        Log.d(TAG, "Fullscreen requested " + fullscreen);

        if(fullscreen == null) {
            Log.d("", "greystripeFullscreen was called too early. Ignoring request.");
            adWhirlLayout.rollover();
            return;
        }

        if(fullscreen.isAdReady()) {
            fullscreen.display();
            adWhirlLayout.adWhirlManager.resetRollover();
            adWhirlLayout.rotateThreadedDelayed();
        } else {
            Log.d(TAG, "Fullscreen wasn't ready. Let's try fetching again");
            fullscreen.fetch();
        }
    }

    /**
     * This is a method for the BannerLister interface.
     * If the activity failed to receive a banner ad, we log and display
     * a Toast message about its failure and call rollover() on the AdWhirlLayout
     * to move onto the next ad network.
     */
    public void onFailedToFetchAd(GSAd ad, GSAdErrorCode error) {
        Log.v(TAG, "*****FAILED to receive ad: " + ad + ", " + error);
        Toast toast = Toast.makeText(this, "Failed to receive " + ad, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();

        adWhirlLayout.rollover();
    }

    /**
     * This is a method for the BannerListener interface.
     * If the activity successfully received a banner ad, we log and display
     * a Toast message about its success and update the AdWhirlLayout to
     * display the banner ad.
     */
    public void onFetchedAd(GSAd ad) {
        Log.v(TAG, "*****SUCCESSFULLY received ad " + ad);
        Toast toast = Toast.makeText(this, "Successfully received an ad " + ad, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();

        if(ad instanceof GSMobileBannerAdView) {
            adWhirlLayout.handler.post(new AdWhirlLayout.ViewAdRunnable(adWhirlLayout, (GSMobileBannerAdView) ad));
        }

        if(ad instanceof GSFullscreenAd) {
            fullscreen.display();
        }

        adWhirlLayout.adWhirlManager.resetRollover();
        adWhirlLayout.rotateThreadedDelayed();
    }

    @Override
    public void onAdClickthrough(GSAd ad) {
        //ignored
    }

    @Override
    public void onAdDismissal(GSAd ad) {
        //ignored
    }

    @Override
    public void onAdExpansion(GSAd ad) {
        //ignored    	
    }
    
    @Override
    public void onAdCollapse(GSAd ad) {
        //ignored
    }
    
    /**
     * This is an auto-generated method for AdWhirlInterface.
     * It does nothing more at this point.
     */
    @Override
    public void adWhirlGeneric() {
        Log.d(AdWhirlUtil.ADWHIRL, "In adWhirlGeneric()");
    }
}