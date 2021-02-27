package com.vsvdev.phaser;

public class VolatileSpinCojoiner implements Cojoiner {
    private volatile boolean ready = false;
    public void runWaiter() {
        while (!ready);
    }

    public void runSignaller() {
       ready=true;
    }
}