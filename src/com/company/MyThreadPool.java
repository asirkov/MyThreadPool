package com.company;

import  java.util.*;
import java.util.concurrent.*;

public class MyThreadPool implements Executor {
    private Queue<Runnable> workQueue = new ConcurrentLinkedQueue<>();
    private volatile boolean isRunning = true;

    public MyThreadPool(int thCount) {
        for(int i = 0; i < thCount; i++) {
            new Thread(new Worker()).start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if(isRunning) {
            workQueue.offer(runnable);
        }
    }

    public void shutdown() {
        isRunning = false;
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            while(isRunning) {
                Runnable newWork = workQueue.poll();
                if(newWork != null) {
                    newWork.run();
                }
            }
        }
    }
}
