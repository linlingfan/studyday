## ReenTrantLoack (重入锁)

[ReentranLock 深入研究]{https://www.cnblogs.com/xrq730/p/4979021.html}

- Lock主要实现有 ReentrantLock、ReadLock和WriteLock。

- synchronized是基于JVM层面实现的，而Lock是基于JDK层面实现的。

- Lock支持获取锁超时和获取锁响应中断。而Synchronized不支持。

- ReentrantLock实现Lock接口，在ReentrantLock中引用了AbstractQueuedSynchronizer的子类，所有的同步操作都是依靠AbstractQueuedSynchronizer（队列同步器）

其他：

#### ReenTrantLock的加锁解锁

```$xslt
Lock lock = new ReentrantLock() // 默认非公平锁
Lock lock = new ReentrantLock(true) // 非公平锁
Lock lock = new ReentrantLock(false) // 公平锁
```

加锁：lock.lock();
解锁：lock.unlock(); // 每次加锁最终都要手动解锁

公平锁和非公平锁：
Lock类分公平锁和不公平锁，公平锁是按照加锁顺序来的，非公平锁是不按顺序的，也就是说先执行lock方法的锁不一定先获得锁。
不过Lock底层基于AQS实现，队列先进先出，先出的线程大概率获取到锁。

#### Condition 对象。制定操作线程状态（注意制定唤起线程）
- condition.wait() 线程等待。
- condition.signal() 唤起线程。
- Condition类的awiat方法和Object类的wait方法等效
- Condition类的signal方法和Object类的notify方法等效
- Condition类的signalAll方法和Object类的notifyAll方法等效

- 一个condition对象的signal（signalAll）方法和该对象的await方法是一一对应的，也就是一个condition对象的signal（signalAll）方法不能唤醒其他condition对象的await方法。
- ReentrantLock类可以唤醒指定条件的线程，而object的唤醒是随机的。


#### tryLock和lock和lockInterruptibly的区别

- tryLock能获得锁就返回true，不能就立即返回false，tryLock(long timeout,TimeUnit unit)，可以增加时间限制，如果超过该时间段还没获得锁，返回false
- lock能获得锁就返回true，不能的话一直等待获得锁
- lock和lockInterruptibly，如果两个线程分别执行这两个方法，但此时中断这两个线程，前者不会抛出异常，而后者会抛出异常

#### TODO 读写锁
Lock类有读锁和写锁，读读共享，写写互斥，读写互斥