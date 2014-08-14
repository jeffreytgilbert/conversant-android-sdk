package com.conversantmedia.sdksample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.greystripe.sdk.*;

public class MainActivity extends Activity {
	private static final String LOG_TAG = "Conversant SDK";

	// UI Elements that need to be shared at this level 
	private static GSFullscreenAd myFullscreenAd;
	private static GSMobileBannerAdView myBannerAd;
	//private static GSMediumRectangleAdView myMedRecAd;
	//private static GSLeaderboardAdView myLeaderboardAd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	
		// Create ad object instances
		myFullscreenAd = new GSFullscreenAd(this);

		// Get the banner included in the layout main.xml
		myBannerAd = (GSMobileBannerAdView) this.findViewById(R.id.gsBanner);
		//myMedRecAd = (GSMediumRectangleAdView) this.findViewById(R.id.gsMedRec);
		//myLeaderboardAd = (GSLeaderboardAdView) this.findViewById(R.id.gsLeaderboard);

		// Add listeners for the ad objects
		myBannerAd.addListener(new MyBannerListener());
		//myMedRecAd.addListener(new MyBannerListener());
		//myLeaderboardAd.addListener(new MyBannerListener());
		myFullscreenAd.addListener(new MyFullscreenListener());
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
			Toast.makeText(MainActivity.this.getApplicationContext(),
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
			Toast.makeText(MainActivity.this.getApplicationContext(),
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
		//myMedRecAd.refresh();
		//myLeaderboardAd.refresh();
	}

	/**
	 * Simple listener for Conversant SDK events from the banner
	 */
	private class MyBannerListener implements GSAdListener {

		@Override
		public void onFailedToFetchAd(GSAd ad, final GSAdErrorCode error) {
			String errorString = error.toString();
			Toast.makeText(MainActivity.this.getApplicationContext(),
					errorString, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdClickthrough(GSAd ad) {
			Toast.makeText(MainActivity.this.getApplicationContext(),
					"Banner ad clicked!", Toast.LENGTH_SHORT).show();

			// Use ad.getId(); after fetch to retrieve the Conversant AdId for debugging
			int id = ad.getId();
			Log.d(LOG_TAG, "AdId: " + id);
		}

		@Override
		public void onAdDismissal(GSAd ad) {
			Toast.makeText(MainActivity.this.getApplicationContext(),
					"Banner click-through dismissed!", Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		public void onFetchedAd(GSAd ad) {
			Toast.makeText(MainActivity.this.getApplicationContext(),
					"Banner fetched!", Toast.LENGTH_SHORT).show();

			// Use ad.getId(); after fetch to retrieve the Conversant AdId for debugging
			int id = ad.getId();
			Log.d(LOG_TAG, "AdId: " + id);
		}

		@Override
		public void onAdExpansion(GSAd ad) {
			Toast.makeText(MainActivity.this.getApplicationContext(),
					"Ad Expanded!", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdCollapse(GSAd ad) {
			Toast.makeText(MainActivity.this.getApplicationContext(),
					"Ad Collapsed!", Toast.LENGTH_SHORT).show();
		}
		
		
		@Override
		public void onAdResize(GSAd ad, int x, int y, int width, int height) {
			Toast.makeText(MainActivity.this.getApplicationContext(),
					"Ad Resized!", Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * Simple listener for Conversant SDK events from the fullscreen ad
	 */
	private class MyFullscreenListener implements GSAdListener {

		@Override
		public void onFailedToFetchAd(GSAd ad, final GSAdErrorCode error) {
			String errorString = error.toString();
			Toast.makeText(MainActivity.this.getApplicationContext(),
					errorString, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdClickthrough(GSAd ad) {
			Toast.makeText(MainActivity.this.getApplicationContext(),
					"Fullscreen clicked!", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdDismissal(GSAd ad) {
			Toast.makeText(MainActivity.this.getApplicationContext(),
					"Fullscreen dismissed!", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onFetchedAd(GSAd ad) {
			Toast.makeText(MainActivity.this.getApplicationContext(),
					"Fullscreen fetched!", Toast.LENGTH_SHORT).show();

			// Use ad.getId(); after fetch to retrieve the Conversant AdId for debugging
			int id = ad.getId();
			Log.d(LOG_TAG, "AdId: " + id);
		}

		@Override
		public void onAdExpansion(GSAd ad) {
			Toast.makeText(MainActivity.this.getApplicationContext(),
					"Ad Expanded!", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdCollapse(GSAd ad) {
			Toast.makeText(MainActivity.this.getApplicationContext(),
					"Ad Collapsed!", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdResize(GSAd ad, int x, int y, int width, int height) {
			Toast.makeText(MainActivity.this.getApplicationContext(),
					"Ad Resized!", Toast.LENGTH_SHORT).show();
		}
	}
}
