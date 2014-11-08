package com.mygdx.mopubtest.android;

import com.mygdx.mopubtest.Listener;
import com.mygdx.mopubtest.ListenerManager.ListenerType;

public class HideAdListener implements Listener {
    AndroidLauncher base;

    @Override
    public void call() {
        this.base.hideAd();
    }

    public void setBase(AndroidLauncher base) {
        this.base = base;
    }

    @Override
    public ListenerType type() {
        return ListenerType.HIDEAD;
    }
}