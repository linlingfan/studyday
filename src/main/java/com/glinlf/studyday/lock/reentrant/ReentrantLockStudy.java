package com.glinlf.studyday.lock.reentrant;

import org.junit.Test;

import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author glinlf
 * @date 2019-03-27
 * @Description TODO
 **/
public class ReentrantLockStudy {


    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Condition newCondition = lock.newCondition();

    private Integer sum = 0;

    public void testReentrantLock() throws InterruptedException {
        lock.lock();
        sum++;
        System.out.println(Thread.currentThread().getName() + "-sum:" + sum);
        lock.unlock();
    }

    public static void main(String[] args) {
        // 模拟并发
        ReentrantLockStudy study = new ReentrantLockStudy();
        int initThreadNum = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(initThreadNum);
        for (int i = 0; i <= initThreadNum; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await(); // 线程等待
                        study.testReentrantLock();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            countDownLatch.countDown(); // 减到0全部唤醒
        }


    }

    private int maxSize = 10; // 生产者最大值
    private LinkedList<Integer> resources = new LinkedList<>();

    // 生产者
    public class Producer implements Runnable {

        private int proSize;

        public Producer(int proSize) {
            this.proSize = proSize;
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            lock.lock();
            try {

                while (true) {
                    for (int i = 0; i < proSize; i++) {
                        if (resources.size() >= maxSize) {
                            System.out.println(threadName +" 仓库已满...等待消费！");
                            try {
                                condition.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("threadName 开始生产...产品数：" + i + "仓库总量：" + resources.size());
                        resources.add(i);
                        newCondition.signal(); // 注意consumer使用newCondition使得线程等待 需要使用对应的newCondition唤起。
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }

    // 消费者
    public class Consumer implements Runnable {

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            while (true) {
                lock.lock();
                try {
                    while (resources.size() <= 0) {
                        System.out.println(threadName + " 当前仓库没有产品，请稍等...");
                        try {
                            // 进入阻塞状态
                            newCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // 消费数据
                    int size = resources.size();
                    for (int i = 0; i < size; i++) {
                        Integer remove = resources.remove();
                        System.out.println(threadName + " 当前消费产品编号为：" + remove);
                    }
                    // 唤醒生产者
                    condition.signal();
                } finally {
                    lock.unlock();
                }
            }

        }
    }

    @Test
    public void testProducerAndConsumer() throws InterruptedException {
        ReentrantLockStudy study = new ReentrantLockStudy();
        Producer producer = study.new Producer(20);
        Consumer consumer = study.new Consumer();
        final Thread producerThread = new Thread(producer, "producer");
        final Thread consumerThread = new Thread(consumer, "consumer");
        producerThread.start();
        TimeUnit.SECONDS.sleep(2);
        consumerThread.start();
        Thread.sleep(5000l);
    }

    @Test
    public void testLock(){
        lock.lock();
    }
}
