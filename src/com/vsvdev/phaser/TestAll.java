package com.vsvdev.phaser;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 *
 * devoxxUkraine thread safe with phaser
 */
public class TestAll {
    public static void main(String... args) {
        for (int i = 0; i < 10; i++) {
            test();
            System.out.println();
        }
        CojoinedTasksTester.shutdown();
    }

    private static void test() {
        Stream.<Supplier<Cojoiner>>of(
                NoneCojoiner::new,
                WaitNotifyCojoiner::new
                        ,
                CountDownLatchCojoiner::new
                ,
                VolatileSpinCojoiner::new,
                PhaserCojoiner::new

        )
                .forEach(CojoinedTasksTester::test);
    }
}
