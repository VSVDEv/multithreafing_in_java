package com.vsvdev.phaser;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchCojoiner implements Cojoiner {
    private CountDownLatch latch = new CountDownLatch(1);
    public void runWaiter() {
        boolean interrupted = Thread.interrupted();
       while (true){
           try {
               latch.await();
               break;
           } catch (InterruptedException e) {
               interrupted = true;
               e.printStackTrace();
           }
       }
       if (interrupted) Thread.currentThread().interrupt();


    }

    public void runSignaller() {
        latch.countDown();
    }
}