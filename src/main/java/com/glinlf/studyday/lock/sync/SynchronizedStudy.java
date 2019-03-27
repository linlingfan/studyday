package com.glinlf.studyday.lock.sync;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author glinlf
 * @date 2019-03-26
 * @Description TODO
 **/
public class SynchronizedStudy {

    Logger LOG = LoggerFactory.getLogger(SynchronizedStudy.class);

    // 双重校验锁实现对象单例
    private volatile static SynchronizedStudy uniqueInstance;

    public SynchronizedStudy() {

    }

    public static SynchronizedStudy getUniqueInstance() {
        if (null == uniqueInstance) {
            synchronized (SynchronizedStudy.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new SynchronizedStudy();
                    ;
                }
            }
        }
        return uniqueInstance;
    }

    private static volatile AtomicInteger sum = new AtomicInteger(0);

    private static int initSum = 32;

    /**
     * 加锁方式保证多线程安全
     */
    public void add() {
        synchronized (this) {
            initSum++;
            System.out.println(Thread.currentThread().getName() + "--initSum:" + initSum);
        }
    }

    /**
     * 使用原子操作类 保证成员变量修改线程安全
     * 乐观锁的表现
     * 没有使用synchronized
     * AtomicInteger 类主要利用 CAS (compare and swap) + volatile 和 native 方法来保证原子操作，
     * 从而避免 synchronized 的高开销，执行效率大为提升。
     */
    public void AtomicAdd() {
        System.out.println(sum.addAndGet(1));
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedStudy synchronizedStudy = new SynchronizedStudy();
        int initThreadNum = 20;
        final CountDownLatch countDownLatch = new CountDownLatch(initThreadNum);
        for (int i = 0; i <= initThreadNum; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
//                        synchronizedStudy.add();
                        synchronizedStudy.AtomicAdd();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            countDownLatch.countDown();
        }
        Thread.sleep(50l);
        System.out.println("total sum : " + sum.get());
        System.out.println("total initSum : " + initSum);
    }

    /**
     * 快速创建多线程的几种方式
     */
    // (普通方式：继承Thread类或实现Runnable接口的方式)1.使用匿名内部类的方式
    public void createdThreadByTread() {

        // 方式1.1 相当于继承了Thread类 作为自雷重写run()方法
        new Thread() {
            public void run() {
                System.out.println("匿名内部类创建线程方式一");
            }
        }.start();

        // 方式2.2 相当于实现Runnable接口 ，将Runnable实现类作为匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("匿名内部类创建线程方式二");
            }
        }).start();
    }

    // 使用线程池的方式
    public void createThreadPool(){
        Exce
    }


    // Lambda并行流表达式的实现(parallelStream)
    @Test
    public void createdThreadByParallelStream() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
        list.stream().parallel().forEach(item -> {
            System.out.println(Thread.currentThread().getName() + "num:" + item);
            try {
                Thread.sleep(100l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        int sum = list.parallelStream().mapToInt(item -> item).sum();
        System.out.println("计算结果：" + sum);
    }

    // 使用Spring实现多线程 开启@EnableAsync注解——支持异步任务 略。见javaweb-kit项目
}

