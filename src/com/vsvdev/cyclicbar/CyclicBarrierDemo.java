package com.vsvdev.cyclicbar;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 *Класс java.util.concurrent.CyclicBarrier
 * Вариант CountDownLatch, допускающий повторное ожидание
 *
 */
public class CyclicBarrierDemo {
    static final int THREADS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws Exception {

        final int JUMPS = 10;
        AtomicReference<Doer> doer = new AtomicReference<>();
        AtomicInteger count = new AtomicInteger(0);
        CyclicBarrier barrier = new CyclicBarrier(THREADS, () -> {
            if (count.get() != 1) {
                System.out.println("oops");
            }
            doer.set(new Doer());
            count.set(0);

        });

        Runnable r = () -> {
            for (int i = 0; i < JUMPS; i++) {
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException ignored) {
                    System.out.println(ignored);
                }
            }
            doer.get().doOnce(count::incrementAndGet);
        };


        List<Thread> threads = Stream.generate(() -> new Thread(r))
                .limit(THREADS).peek(Thread::start)
                .collect(Collectors.toList());
        for (Thread thread : threads) {
            thread.join();

        }
    }
}
class Doer{

    private volatile int flag = 0;
    private static final AtomicIntegerFieldUpdater<Doer> FLAG_UPDATER =
    AtomicIntegerFieldUpdater.newUpdater(Doer.class,"flag");
    void doOnce (Runnable action){
      if (FLAG_UPDATER.compareAndSet(this,0,1)){
          action.run();
      }
    }
}
