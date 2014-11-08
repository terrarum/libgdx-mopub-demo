package com.mygdx.mopubtest;

import com.mygdx.mopubtest.ListenerManager.ListenerType;

public class HideAdListener implements Listener {
    IOSLauncher base;

    @Override
    public void call() {
        this.base.hideAd();
    }

    public void setBase(IOSLauncher base) {
        this.base = base;
    }

    @Override
    public ListenerType type() {
        return ListenerType.SHOWAD;
    }
}