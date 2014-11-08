package com.mygdx.mopubtest.android;

import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.mopubtest.MopubTest;
import com.mopub.mobileads.MoPubView;
import com.mygdx.mopubtest.MopubTest;

public class AndroidLauncher extends AndroidApplication {
    MoPubView mAdView;
    MopubTest app;
    boolean adLoaded = false;
    boolean adShown = false;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        app = new MopubTest();

        // Setup window
        RelativeLayout layout = new RelativeLayout(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        // Setup listeners
        ShowAdListener show = new ShowAdListener();
        HideAdListener hide = new HideAdListener();
        show.setBase(this);
        hide.setBase(this);
        app.addListener(show);
        app.addListener(hide);

        // Setup the 2 views. 1 for content, and 1 for ads
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        View appView = initializeForView(app, config);
        mAdView = new MoPubView(this);
        mAdView.setAdUnitId("ac57a063cc324a14b143e775bedfbfe3"); // Your MoPub Ad Unit ID

        // Setup the layout
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(appView);
        layout.addView(mAdView, adParams);

        // Set the content view to the layout
        setContentView(layout);
	}

    @Override
    public void onDestroy() {
        mAdView.destroy();
        super.onDestroy();
    }


    public void showAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(!adLoaded) {
                    System.out.println("Ad Loaded!");
                    mAdView.loadAd();
                    adLoaded = true;
                }
                if(!adShown) {
                    System.out.println("Showing Ad");
                    mAdView.setVisibility(View.VISIBLE);
                    adShown = true;
                }
            }
        });
    }

    public void hideAd() {
        if(adShown) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdView.setVisibility(View.GONE);
                    System.out.println("Hiding Ad");
                    adShown = false;
                }
            });
        }
    }

}
