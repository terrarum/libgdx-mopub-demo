package com.mygdx.mopubtest;

import com.mygdx.mopubtest.ListenerManager.ListenerType;

public interface Listener {
    public abstract void call();
    public abstract ListenerType type();
}