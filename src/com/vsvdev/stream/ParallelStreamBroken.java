package com.vsvdev.stream;

import java.util.Arrays;
import java.util.concurrent.atomic.DoubleAdder;

public class ParallelStreamBroken {
    /**
     *
     * bad practice
     * @param args
     */
    public static void main(String[] args) {
        int[] array = Commons.prepareArray();


        long startTime = System.currentTimeMillis();

        double[] sum = new double[1];
        DoubleAdder rightSum = new DoubleAdder();//to fix
        Arrays.stream(array)
                .parallel()
                .mapToDouble(Commons::function)
                .forEach(x -> sum[0] += x);//no atomicity sum
                //.forEach(rightSum::add);// atomicity sum //to fix


        long endTime = System.currentTimeMillis();

        System.out.println("sum = " + sum[0]);
      //  System.out.println("sum = " + rightSum.doubleValue());//fixed value
        System.out.println("time = " + (endTime - startTime) + "ms");
    }
}
