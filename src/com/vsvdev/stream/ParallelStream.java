package com.vsvdev.stream;

import java.util.Arrays;

/**
 *
 * stream.parallel()
 * Возвращает stream, дальнейшие операции в котором будут
 * исполняться параллельно
 * Надо следить за доступом к общим данным из передаваемых в
 * stream операций
 */
public class ParallelStream {

    public static void main(String[] args) {
        int[] array = Commons.prepareArray();


        long startTime = System.currentTimeMillis();

        double sum = Arrays.stream(array)
                .parallel()
                .mapToDouble(Commons::function)
                .sum();

        long endTime = System.currentTimeMillis();

        System.out.println("sum = " + sum);
        System.out.println("time = " + (endTime - startTime) + "ms");
    }
}
