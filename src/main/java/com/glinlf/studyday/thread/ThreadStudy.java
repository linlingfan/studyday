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

    // SynchronousQueue 阻塞队列 ； 生产者生产速度大于线程处理速度，会一直创建新线程去处理... 注意oom
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public void cachedThreadPool() {
        for (int i =0 ;i<5000;i++) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("run..." + Thread.currentThread().getName());
                    try {
                        Thread.sleep(10000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
        }
    }

    // 队列没有限制大小，默认无限大。job队列可能因为太大oom？
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

    public static void main(String[] args) throws InterruptedException {
        ThreadStudy study = new ThreadStudy();
        study.cachedThreadPool();
        Thread.sleep(10000l);
    }
}
