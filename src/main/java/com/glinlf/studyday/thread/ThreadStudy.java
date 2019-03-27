package com.glinlf.studyday.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author glinlf
 * @date 2019-03-27
 * @Description TODO
 **/
public class ThreadStudy {

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public void cachedThreadPool() {
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("run...");
            }
        });
    }


    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    public void fixedThreadPool() {
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

    public void scheduledThreadPool() {
        for (int i = 0; i < 10; i++) {
            scheduledThreadPool.schedule(new Runnable() {
                public void run() {
                    System.out.println("delay 3 seconds");
                }
            }, 3, TimeUnit.SECONDS);
        }
    }

    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    public void singleThreadExecutor() {
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) {

    }
}
