package com.vsvdev.singleton;

public class SingletonSyncBlock {

    private static SingletonSyncBlock instance;

    public static  SingletonSyncBlock getInstance() {
        synchronized (SingletonSyncBlock.class) {
            if (instance == null) {
                instance = new SingletonSyncBlock();
            }
            return instance;
        }
    }
    private SingletonSyncBlock() {}
}