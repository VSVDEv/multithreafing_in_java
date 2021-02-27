package com.vsvdev.semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 *
 *
 *Класс java.util.concurrent.Semaphore
 * Ограничивает одновременный доступ к ресурсу
 * В отличие от synchronized-блока, одновременно могут работать
 * несколько потоков (но не более заданного N)
 * Операции:
 * void acquire()
 * void release()
 *
 */
public class Semaphor {

    public static void main(String[] args) throws Exception {
        Semaphore semaphore = new Semaphore(2);
        Semaphore fairsemaphore = new Semaphore(2,true);

        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 10; ++i) {
           // DemoThread thread = new DemoThread(semaphore);
            DemoThread thread = new DemoThread(fairsemaphore);
            threads.add(thread);
            thread.start();
        }

        Thread.sleep(20000);

        for (Thread thread : threads) {
            thread.interrupt();
        }
    }


    private static class DemoThread extends Thread {

        private final Semaphore semaphore;

        private DemoThread(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                runUnsafe();
            } catch (InterruptedException e) {
                System.out.println(getName() + " interrupted");
            }
        }

        private void runUnsafe() throws InterruptedException {
            for (;;) {
                semaphore.acquire();
                try {
                    System.out.println(getName() + " acquired semaphore");
                    Thread.sleep(5000L);
                } finally {
                    System.out.println(getName() + " releasing semaphore");
                    semaphore.release();
                }
            }
        }
    }
}
