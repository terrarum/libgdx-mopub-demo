package com.mygdx.mopubtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.*;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.foundation.NSDictionary;
import org.robovm.bindings.mopub.MPAdView;
import org.robovm.bindings.mopub.MPAdViewDelegate;
import org.robovm.bindings.mopub.MPAdViewDelegateAdapter;
import org.robovm.bindings.mopub.MPConstants;
import org.robovm.bindings.mopub.sample.MPAdViewController;

public class IOSLauncher extends IOSApplication.Delegate {

    private static CGSize AD_SIZE;
    private static String AD_ID;
    private boolean adLoaded = false;
    private UIViewController rootViewController;
    private MPAdViewController adViewController;
    private MPAdView banner;
    
    @Override
    protected IOSApplication createApplication() {
        // Create the listeners
        ShowAdListener show = new ShowAdListener();
        HideAdListener hide = new HideAdListener();
        show.setBase(this);
        hide.setBase(this);

        // Create the LibGDX app and add the listeners
        MopubTest app = new MopubTest();
        app.addListener(show);
        app.addListener(hide);

        // Run the app
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.orientationLandscape = false;
        config.orientationPortrait = true;
        return new IOSApplication(app, config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        try {
            UIApplication.main(argv, null, IOSLauncher.class);
        }
        catch (Exception e) {
            Gdx.app.log("iOS Main", "Uh Oh", e);
        }
        pool.close();
//        NSAutoreleasePool pool = new NSAutoreleasePool();
//        UIApplication.main(argv, null, IOSLauncher.class);
//        pool.close();
    }

    public boolean didFinishLaunching (UIApplication application, UIApplicationLaunchOptions launchOptions) {
        super.didFinishLaunching(application, launchOptions);
        AD_SIZE = MPConstants.MOPUB_BANNER_SIZE;
        AD_ID = "f823589bb2a74087bf14696e8fdebd20";

        // We need a view controller to see ads.
        rootViewController = application.getKeyWindow().getRootViewController();

        // Create a MoPub ad. In this case a banner, but you can make it any size you want.
        banner = new MPAdView(AD_ID, AD_SIZE);
        // Let’s calculate our banner size. We need to do this because the resolution of a retina and normal device is different.
        float bannerWidth = (float)UIScreen.getMainScreen().getBounds().size().width();
        float bannerHeight = (float)(bannerWidth / AD_SIZE.width() * AD_SIZE.height() + 50);

        // Let’s set the frame (bounds) of our banner view. Remember on iOS view coordinates have their origin top-left.
        // Position banner on the top.
        banner.setFrame(new CGRect((UIScreen.getMainScreen().getBounds().size().width()/2)-AD_SIZE.width()/2, 0, bannerWidth, bannerHeight));
        banner.setBackgroundColor(new UIColor(0, 0, 1, 1)); // blue
        adViewController = new MPAdViewController(banner);

        // The delegate for the banner. It is required to override getViewController() to get ads.
        MPAdViewDelegate bannerDelegate = new MPAdViewDelegateAdapter() {
            @Override
            public UIViewController getViewController () {
                return adViewController;
            }
        };
        banner.setDelegate(bannerDelegate);
        // Add banner to our view controller.
        adViewController.getView().addSubview(banner);

        UIView view = new UIView();
        view.setFrame(new CGRect(50, 200, 10, 10));
//        view.setBackgroundColor(new UIColor(1, 0, 0, 1)); // red
        view.setBackgroundColor(new UIColor(0, 1, 0, 1)); // green
//        view.setBackgroundColor(new UIColor(0, 0, 1, 1)); // blue
        rootViewController.getView().addSubview(view);


        // We add the ad view controller to our root view controller.
        rootViewController.addChildViewController(adViewController);
        rootViewController.getView().addSubview(adViewController.getView());

        // Create a standard UIWindow at screen size, add the view controller and show it.
        application.getKeyWindow().setRootViewController(rootViewController);
        application.getKeyWindow().addSubview(rootViewController.getView());
        application.getKeyWindow().makeKeyAndVisible();
        return false;
    }

    // Ads
    public void showAd() {
        Gdx.app.log("iOS", "Show Ad");
        if(!adLoaded) {
            try {
                Gdx.app.log("iOS", "Loading Ad");
                banner.loadAd();
            }
            catch(NullPointerException e) {
                Gdx.app.log("iOS", "Failed to Load Ad - NullPointerException");
                Gdx.app.log("iOS Exception 1", e.getMessage());
                Gdx.app.log("iOS Exception 2", e.getClass().toString());
                Gdx.app.log("iOS Exception 3", e.toString());
                Gdx.app.log("iOS Exception 4", e.getCause().toString());
                Gdx.app.log("iOS Exception 5", e.getStackTrace().toString());
            }
            catch(Exception e) {
                Gdx.app.log("iOS", "Failed to Load Ad - Exception");
                Gdx.app.log("iOS Exception 1", e.getMessage());
                Gdx.app.log("iOS Exception 2", e.getClass().toString());
                Gdx.app.log("iOS Exception 4", e.getCause().toString());
                Gdx.app.log("iOS Exception 5", e.getStackTrace().toString());
                Gdx.app.log("iOS Exception 3", e.toString());
            }
            adLoaded = true;
        }
        else {
            Gdx.app.log("iOS", "Ad Already Loaded");
        }
//        double bannerWidth = UIScreen.getMainScreen().getBounds().size().width();
//        double bannerHeight = (bannerWidth / AD_SIZE.width() * AD_SIZE.height());
//        Let’s set the frame (bounds) of our banner view. Remember on iOS view coordinates have their origin top-left.
//        Position banner on the top.
//        Gdx.app.log("iOS", "Setting Ad Position");
//        if (adLoaded) {
//            banner.setFrame(new CGRect(
//                ((UIScreen.getMainScreen().getBounds().size().width()/2) - AD_SIZE.width() / 2),
//                0,
//                bannerWidth,
//                bannerHeight)
//            );
//        }
    }

    public void hideAd() {
        System.out.println("Hiding Ad");
        double bannerWidth = UIScreen.getMainScreen().getBounds().size().width();
        double bannerHeight = bannerWidth / AD_SIZE.width() * AD_SIZE.height();

        // Let’s set the frame (bounds) of our banner view. Remember on iOS view coordinates have their origin top-left.
        // Position banner above the top.
        banner.setFrame(new CGRect(0, -bannerHeight, bannerWidth, bannerHeight));
    }
}