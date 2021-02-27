package com.vsvdev.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Пакет java.util.concurrent.atomic
 * AtomicBoolean
 * AtomicInteger
 * AtomicLong
 * AtomicReference<V>
 * Операции:
 * V get()
 * void set(V newValue)
 * boolean compareAndSet(V expect, V update)
 */
/*
public final int incrementAndGet () {
        for (;;) {
        int current = get ();
        int next = current + 1;
        if ( compareAndSet ( current , next ))
        return next ;
        }
        }

        */
/*
    Compare and set

        (expect, update) --> result

    Базовый примитив для многих lock free алгоритмов


 */
public class Atomic {

    private final AtomicBoolean flag = new AtomicBoolean(false);

    void doOnce(Runnable action) {
        if (flag.compareAndSet(false, true)) {
            action.run();
        }
    }

    int getAndIncrement(AtomicInteger i) {
        int cur;
        do {
            cur = i.get();
        } while (!i.compareAndSet(cur, cur + 1));
        return cur;
    }


}

class SequenceGenerator {

    private static final AtomicInteger counter = new AtomicInteger();

    public static int nextInt() {
        return counter.getAndIncrement();
    }

    public static void main(String[] args) throws Exception {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 1000; ++i) {
            Thread thread = new Thread(() -> System.out.println(nextInt()));
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Counter final value: " + counter.get());
    }

}

class AtomicPoint {
    private final AtomicInteger x = new AtomicInteger(0);
    private final AtomicInteger y = new AtomicInteger(1);

    public void rotateClockwise(AtomicInteger x, AtomicInteger y) {
        int oldX = x.getAndSet(y.get());
        y.set(-oldX);
    }


}
