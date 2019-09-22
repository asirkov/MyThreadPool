package com.company;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThreadPool threadPool = new MyThreadPool(4);
        long start = System.nanoTime();

        List<Future<Double> > futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            final int j = i;
            futures.add( CompletableFuture.supplyAsync(() -> {
                double res = 0;
                for (int k = 0; k < 1000000; k++) {
                    res = res + Math.tan(j);
                }
                return res;
            }, threadPool ) );
        }

        double value = 0;
        for (Future<Double> future : futures) {
            value += future.get();
        }

        System.out.println("Value :" + value + ", " + (double) ((System.nanoTime() - start) / 1000_000_000) );
        threadPool.shutdown();

    }
}
