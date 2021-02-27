package com.vsvdev.singleton;

public class SingletonSynchro {
    private static SingletonSynchro instance;

    public static synchronized SingletonSynchro getInstance() {
        if (instance == null) {
            instance = new SingletonSynchro();
        }
        return instance;
    }

    private SingletonSynchro() {}
}