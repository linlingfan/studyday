## synchronized

### 主要的使用方式
- 修饰普通实例方法，作用于当前对象的实例加锁，进入同步代码要获取当前实例的锁。
- 修饰静态方法，锁作用于当前类对象。进入同步代码前要获得当前类对象的锁。
    1. 也就是给当前类加锁，全局锁。不管new 多少个实例，由于静态方法属于类的成员，对所有该对象的实例都加锁。
    2. 同理如果是非静态方法加锁。不同线程调用不同对象实例，不会发生互斥现象。
- 修饰代码块，指定加锁对象，对给定对象加锁，进入同步代码库前要获得给定对象的锁。（普通代码块和静态代码块加锁产生效果和对方法加锁一样）
    1. synchronized(this)代码块也是锁定当前对象的。synchronized 
    2. 关键字加到 static 静态方法和 synchronized(class)代码块上都是是给 Class 类上锁。
    3. 尽量不要使用 synchronized(String a) 因为JVM中，字符串常量池具有缓冲功能！

***synchronized 在JVM 实现原理：***
- synchronized 同步语句块的实现使用的是 monitorenter 和 monitorexit 指令，其中 monitorenter 指令指向同步代码块的开始位置，monitorexit 指令则指明同步代码块的结束位置。
- synchronized 修饰的方法并没有 monitorenter 指令和 monitorexit 指令，取得代之的确实是 ACC_SYNCHRONIZED 标识，该标识指明了该方法是一个同步方法，JVM 通过该 ACC_SYNCHRONIZED 访问标志来辨别一个方法是否声明为同步方法，从而执行相应的同步调用。

***synchronized 优化：***
在 Java 早期版本中，synchronized 属于重量级锁，效率低下，因为监视器锁（monitor）是依赖于底层的操作系统的 Mutex Lock 来实现的，Java 的线程是映射到操作系统的原生线程之上的。如果要挂起或者唤醒一个线程，都需要操作系统帮忙完成，而操作系统实现线程之间的切换时需要从用户态转换到内核态，这个状态之间的转换需要相对比较长的时间，时间成本相对较高，这也是为什么早期的 synchronized 效率低的原因。
庆幸的是在 Java 6 之后 Java 官方对从 JVM 层面对synchronized 较大优化，所以现在的 synchronized 锁效率也优化得很不错了。
JDK1.6对锁的实现引入了大量的优化：
如自旋锁、适应性自旋锁、锁消除、锁粗化、偏向锁、轻量级锁等技术来减少锁操作的开销。

### 关键字volatile 的简单了解

例如： uniqueInstance = new Singleton(); 这段代码其实是分为三步执行：
   
   1. 为 uniqueInstance 分配内存空间
   2. 初始化 uniqueInstance
   3. 将 uniqueInstance 指向分配的内存地址
   
 但是由于 JVM 具有指令重排的特性，执行顺序有可能变成 1->3->2。
 指令重排在单线程环境下不会出现问题，但是在多线程环境下会导致一个线程获得还没有初始化的实例。
 例如，线程 T1 执行了 1 和 3，此时 T2 调用 getUniqueInstance() 后发现 uniqueInstance 不为空，因此返回 uniqueInstance，但此时 uniqueInstance 还未被初始化。
 
 使用 volatile 可以禁止 JVM 的指令重排，保证在多线程环境下也能正常运行。
 
 ***注意：volatile还有使得内存可见性的效果。***
 使用关键字volatile修饰的变量，在多线程环境中，如果对该变量进行修改更新到内存后会立即刷新缓存，使得其他线程重新去内存获取最新修改后的变量值。
 虽然如此，volatile仍然不能保证线程安全。
 
 ### Synchronized 和 ReenTrantLock 的对比
 
 - 两者都是可重入锁（自己可以再次获取自己的内部锁）
 - synchronized 依赖于 JVM 而 ReenTrantLock 依赖于 API（JDK层面）
 - ReenTrantLock 比 synchronized 增加了一些高级功能
    1. 等待可中断；
    2. 可实现公平锁；
    3. 可实现选择性通知（锁可以绑定多个条件）
 - 性能已不是选择标准
 优化后的synchronized性能和ReenTrantLock差不多。
 
 ### TODO ReenTrantLock 
 
 
 ### Java线程的六个状态
 1. NEW 新建状态 （线程还未调用start()时候）
 2. RUNNABLE 可运行状态（调用start之后，jvm启动了这个任务，但是可能被其他资源占用线程变成WAITING）
 3. BLOCKED 阻塞状态（线程被锁的时候，线程等待进去一个synchronized块方法或者可重入锁的时候）
 4. WAITING 等待状态（线程调用object.wait thread.join LockSupport.park 的时候变成 WAITING）
 5. TIMED_WAITING 超时等待状态（sleep 或者wait,join带时间参数等方法时）
 6. TERMINATED 终止状态（线程执行完成 或者被中断的时候变成TERMINATED）

 





