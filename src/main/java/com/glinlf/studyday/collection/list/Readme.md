## List 集合笔记

### Arraylist 与 LinkedList 异同

   - 1. 是否保证线程安全：
        - ArrayList 和 LinkedList 都是不同步的，也就是不保证线程安全。
   - 2. 底层数据结构：
        - Arrayist 底层使用的是Object数组；
        - LinkedList 底层使用的是双向链表数据结构（JDK1.6之前为循环链表，JDK1.7取消了循环。注意双向链表和双向循环链表的区别）。
        
   - 3. 插入和删除是否受元素位置的影响：
        - ArrayList 采用数组存储，所以插入和删除元素的时间复杂度受元素位置的影响。** 比如：执行`add(E e)  `方法的时候， ArrayList 会默认在将指定的元素追加到此列表的末尾，这种情况时间复杂度就是O(1)。但是如果要在指定位置 i 插入和删除元素的话（`add(int index, E element) `）时间复杂度就为 O(n-i)。因为在进行上述操作的时候集合中第 i 和第 i 个元素之后的(n-i)个元素都要执行向后位/向前移一位的操作。 
        - LinkedList 采用链表存储，所以插入，删除元素时间复杂度不受元素位置的影响，都是近似 O（1）而数组为近似 O（n）。**
   - 4. 是否支持快速随机访问：** LinkedList 不支持高效的随机元素访问，而 ArrayList 支持。快速随机访问就是通过元素的序号快速获取元素对象(对应于`get(int index) `方法)。
   - 5. 内存空间占用：
        - ArrayList的空间浪费主要体现在在list列表的结尾会预留一定的容量空间，
        - 而LinkedList的空间花费则体现在它的每一个元素都需要消耗比ArrayList更多的空间（因为要存放直接后继和直接前驱以及数据）。 

### 创建线程安全的集合类
    
    ```
        List<E> synArrayList = Collections.synchronizedList(new ArrayList<E>());
        
    ```
    - Collections针对每种集合都声明了一个线程安全的包装类，在原集合的基础上添加了锁对象，集合中的每个方法都通过这个锁对象实现同步
