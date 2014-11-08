package com.mygdx.mopubtest;

import java.util.ArrayList;

public class ListenerManager {
    ArrayList<Listener> listeners;

    public ListenerManager() {
        listeners = new ArrayList<Listener>();
    }

    public void add(Listener l) {
        if(listeners == null) {
            listeners = new ArrayList<Listener>();
        }
        listeners.add(l);
        System.out.println("Listener Added!");
    }

    public void call(ListenerType type) {
        for(Listener l : listeners) {
            if(l.type() == type) {
                l.call();
            }
        }
    }

    public enum ListenerType {
        SHOWAD, HIDEAD
    }
}