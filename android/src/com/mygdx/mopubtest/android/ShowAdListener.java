package com.mygdx.mopubtest.android;

import com.mygdx.mopubtest.Listener;
import com.mygdx.mopubtest.ListenerManager.ListenerType;

public class ShowAdListener implements Listener {
    AndroidLauncher base;

    @Override
    public void call() {
        this.base.showAd();
    }

    public void setBase(AndroidLauncher base) {
        this.base = base;
    }

    @Override
    public ListenerType type() {
        return ListenerType.SHOWAD;
    }
}