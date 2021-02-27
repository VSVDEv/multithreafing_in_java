package com.vsvdev.phaser;

public class WaitNotifyCojoiner implements Cojoiner {
    private final Object monitor = new Object();
    private boolean ready = false;
    public void runWaiter() {
        synchronized (monitor){
            boolean interrupted = Thread.interrupted();
            while (!ready) {
                try {
                    monitor.wait(0);
                } catch (InterruptedException e) {
                    interrupted=true;
                    e.printStackTrace();
                }
            }
            if (interrupted) Thread.currentThread().interrupt(); //self-interrupt
        }

    }

    public void runSignaller() {
        synchronized (monitor){
           ready =true;
           monitor.notifyAll();
        }
    }
}
