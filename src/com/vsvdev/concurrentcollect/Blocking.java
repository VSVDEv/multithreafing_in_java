package com.vsvdev.concurrentcollect;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Блокирующие коллекции
 * SynchronousQueue без памяти, одна вставка = одно удаление)
 * ArrayBlockingQueue фиксированный размер)
 * LinkedBlockingDeque размер может быть не фиксирован)
 * PriorityBlockingQueue PriorityQueue x ArrayBlockingQueue
 */
public class Blocking {

    public static void main(String[] args) {
        BlockingQueue<String> syncQueue = new SynchronousQueue();
        Runnable producer = () -> {
            int i = 0;
            while (true) try {
                Thread.sleep(java.util.concurrent.ThreadLocalRandom.current().nextInt(500, 1000));
                String value = String.valueOf(i++);
                syncQueue.put(value);
                System.out.println("added: " + value);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        };
        Runnable consumer = () -> {
            while (true) try {
                Thread.sleep(java.util.concurrent.ThreadLocalRandom.current().nextInt(500, 2000));
                System.err.println("read: " + syncQueue.take());
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        };

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
