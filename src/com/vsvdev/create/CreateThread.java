package com.vsvdev.create;

public class CreateThread {
    public static void main(String[] args) {
        for (int i = 0; i < 10; ++i) {
            new HelloThread().start();
        }
        for (int i = 0; i < 10; ++i) {
            new Thread(new HelloRunnable()).start();
        }

        System.out.println("Hello from main thread");
    }
}

/**
 * First way to create thread using inherit
 */
 class HelloThread extends Thread {

    @Override
    public void run() {
        System.out.println("Hello from " + getName());
    }
}

/**
 *
 * Second way to create thread using implements
 * @class HelloRunnable only object it doesn`t have method start()
 * To crreate Thread use:  new Thread(new HelloRunnable()).start();
 */
   class HelloRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("Hello from runnable " + Thread.currentThread().getName());

        }
    }
