package com.vsvdev.completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * CompletableFuture
 * средство для передачи информации между параллельными потоками исполнения.
 * По существу это блокирующая очередь, способная передать только одно ссылочное значение.
 * В отличие от обычной очереди, передает также исключение, если оно возникло при вычислении передаваемого значения.
 * использует пул по кол-ву ядер
 * поддерживает комбинацию
 *
 */
public class CompetableDemo {
    static final int THREADS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws Exception {

        final int JUMPS = 10;
        AtomicReference<Doer> doer = new AtomicReference<>();
        AtomicInteger count = new AtomicInteger();
        Runnable r = () ->
                doer.get().doOnce(count::incrementAndGet);
        for (int i = 0; i < JUMPS; i++) {
            count.set(0);
            doer.set(new Doer());
            List<CompletableFuture<?>> futures =
                    Stream.generate(() -> CompletableFuture.runAsync(r))
                            .limit(THREADS).collect(Collectors.toList());
            for (CompletableFuture<?> future : futures) {
                future.get();
            }
            if (count.get() != 1) {
                System.out.println("oops");
            }
        }


    }

}

class Doer {

    private volatile int flag = 0;
    private static final AtomicIntegerFieldUpdater<Doer> FLAG_UPDATER =
            AtomicIntegerFieldUpdater.newUpdater(Doer.class, "flag");

    void doOnce(Runnable action) {
        if (FLAG_UPDATER.compareAndSet(this, 0, 1)) {
            action.run();
        }
    }
}
