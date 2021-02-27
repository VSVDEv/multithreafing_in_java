package com.vsvdev.phaser;

import java.util.concurrent.Phaser;

public class PhaserCojoiner implements Cojoiner {
    private final Phaser phaser = new Phaser(Constants.PARTIES+1);
    public void runWaiter() {
        phaser.arriveAndAwaitAdvance();
    }

    public void runSignaller() {
      phaser.arriveAndDeregister();
    }
}