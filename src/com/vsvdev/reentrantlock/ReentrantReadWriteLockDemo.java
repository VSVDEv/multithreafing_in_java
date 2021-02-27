package com.vsvdev.reentrantlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock
 * Класс java.util.concurrent.locks.ReentrantReadWriteLock
 * Поддерживает разделение доступа на чтение и на запись
 */
public class ReentrantReadWriteLockDemo {


    private final List<String> list = new ArrayList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    String get(int i) {
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        readLock.lock();
        try {
            return list.get(i);
        } finally {
            readLock.unlock();
        }
    }


    void add(String st) {
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            list.add(st);
        } finally {
            writeLock.unlock();
        }

    }
}
