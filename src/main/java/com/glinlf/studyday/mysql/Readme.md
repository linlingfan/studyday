### MYSQL

- ### 索引
1. 什么是索引？
    --索引是一种特殊的文件(InnoDB数据表上的索引是表空间的一个组成部分)，它们包含着对数据表里所有记录的引用指针。
    好比一本书的目录。加快数据库查询速度。没有索引，默认会遍历全部数据去寻找符合条件的数据。有了相应的索引之后，数据库会直接在索引中查找符合条件的选项。
2. 索引的分类
    --由搜索引擎的不同可分为：
        1. InnoDB 聚簇索引（聚集索引）按照数据存放的物理位置为顺序的， 能提高多行检索的速度
        2. MYISAM 非聚簇索引（非聚集索引）不是按照数据存放的物理位置为顺序的 MYISAM 对于单行的检索很快
3. 如何创建索引
    1. 添加PRIMARY KEY（主键索引）
```$xslt
ALTER TABLE `table_name` ADD PRIMARY KEY ( `column` ) 
```
   2. 添加UNIQUE(唯一索引)
```$xslt
ALTER TABLE `table_name` ADD UNIQUE ( `column` ) 
```
   3. 添加INDEX(普通索引)
```$xslt
ALTER TABLE `table_name` ADD INDEX index_name ( `column` )
 
```
   4. 添加FULLTEXT(全文索引)
```$xslt
ALTER TABLE `table_name` ADD FULLTEXT ( `column`) 
```
   5. 添加多列索引（组合索引）
```$xslt
ALTER TABLE `table_name` ADD INDEX index_name ( `column1`, `column2`, `column3` )
```  
- ### 索引优化（查询优化）
##### 创建索引的原则：
1. 最左前缀匹配原则
2. =和in可以乱序
3. 尽量选择区分度高的列作为索引,区分度的公式是count(distinct col)/count(*) 表示字段不重复比例，唯一键区分度为1。
4. 索引列不能参与计算，保持列“干净”
5. 尽量的扩展索引，不要新建索引。
6. 定义有外键的数据列一定要建立索引。
7. 对于那些查询中很少涉及的列，重复值比较多的列不要建立索引。
8. 对于定义为text、image和bit的数据类型的列不要建立索引。
9. 对于经常存取的列避免建立索引。

##### 索引使用的注意点（具体优化）
1. 索引应建立在那些将用于JOIN,WHERE判断和ORDER BY排序的字段上。尽量不要对数据库中某个含有大量重复的值的字段建立索引。对于一个ENUM类型的字段来说，出现大量重复值是很有可能的情况。
2. 应尽量避免在 where 子句中对字段进行 null 值判断，否则将导致引擎放弃使用索引而进行全表扫描。eg：
```
select id from t where num is null
```
最好不要给数据库留NULL，尽可能的使用 NOT NULL填充数据库.
备注、描述、评论之类的可以设置为 NULL，其他的，最好不要使用NULL。
不要以为 NULL 不需要空间，比如：char(100) 型，在字段建立时，空间就固定了， 不管是否插入值（NULL也包含在内），都是占用 100个字符的空间的，如果是varchar这样的变长字段， null 不占用空间。

3. 应尽量避免在 where 子句中使用 != 或 <> 操作符，否则将引擎放弃使用索引而进行全表扫描。
4. 应尽量避免在 where 子句中使用 or 来连接条件，如果一个字段有索引，一个字段没有索引，将导致引擎放弃使用索引而进行全表扫描。eg：
```$xslt
select id from t where num=1 or Name = 'linfan'
不过可以使用联合查询充分使用索引

select id from t where num = 1
union all
select id from t where Name = 'linfan'

```
5. in 和 not in 也要慎用，否则会导致全表扫描。而且负向查询（not , not in, not like, <>, != ,!>,!< ） 不会使用索引。
   对于连续的数值，能用 between 就不要用 in 了。很多时候用 exists 代替 in 是一个好的选择，当然exists也不跑索引。
7. 模糊查询也将导致全表扫描。
    eg：like %abc% 不使用索引；abc%可以使用索引。
    如果要提高效率，可以考虑全文检索。
    a. like %keyword 索引失效，使用全表扫描。但可以通过翻转函数+like前模糊查询+建立翻转函数索引=走翻转函数索引，不走全表扫描。TODO
    b. like keyword% 索引有效。
    c. like %keyword% 索引失效，也无法使用反向索引。
8. 如果在 where 子句中使用参数，也会导致全表扫描。
    eg: select id from t where num = @num
    不过可以强制使用索引：
    eg: select id from t with(index(索引名)) where num = @num
    应尽量避免在 where 子句中对字段进行表达式操作，这将导致引擎放弃使用索引而进行全表扫描。
    eg: select id from t where num/2 = 100 可改为：select id from t where num = 100*2
9. 应尽量避免在where子句中对字段进行函数操作，这将导致引擎放弃使用索引而进行全表扫描。
10. 在使用索引字段作为条件时，如果该索引是复合索引（多列索引），那么必须使用到该索引中的第一个字段作为条件时才能保证系统使用该索引，否则该索引将不会被使用，并且应尽可能的让字段顺序与索引顺序相一致。
11. 应尽可能的避免更新 clustered 索引数据列。
12. MySQL查询只使用一个索引，因此如果where子句中已经使用了索引的话，那么order by中的列是不会使用索引的。

- ###事务相关
- 事务遵循的特性
    1. 原子性：事务是最小的执行单位，不允许分割。事务的原子性确保动作要么全部完成，要么完全不起作用；
    2. 一致性：执行事务前后，数据保持一致；
    3. 隔离性：并发访问数据库时，一个用户的事务不被其他事务所干扰，各并发事务之间数据库是独立的；
    4. 持久性：一个事务被提交之后。它对数据库中数据的改变是持久的，即使数据库发生故障也不应该对其有任何影响。

- 事务隔离级别
    1. READ_UNCOMMITTED （未提交读）
    2. READ_COMMITTED （提交读）
    3. REPEATABLE_READ （可重复读）
    4. SERIALIZABLE（串行化）
MySQL 是默认使用REPEATABLE_READ隔离级别；Oracle默认采用READ_COMMITTED隔离级别。
事务隔离机制的实现基于锁机制和并发调度。其中并发调度使用的是MVCC（多版本并发控制），通过行的创建时间和行的过期时间来支持并发一致性读和回滚等特性。
- 事务的传播行为（spring）


- #### 锁机制（MYISAM存储引擎和InnoDB存储引擎的锁）

MySQL为解决并发,数据安全，使用了锁的机制。

  - 按细粒度分类
  
    - 表锁：细粒度最大，对整张表加锁，实现简单，资源消耗比较少，加锁快，不会出现死锁。触发锁冲突的概率最高，并发度最低。
    MyISAM和 InnoDB引擎都支持表级锁。
    
    - 行锁：粒度最小 的一种锁，只针对当前操作的行进行加锁。 
    行级锁能大大减少数据库操作的冲突。其加锁粒度最小，并发度高，但加锁的开销也最大，加锁慢，会出现死锁。
    InnoDB支持的行级锁算法：
        1. Record Lock:对锁引项加锁，锁定符合条件行。其他事物不能修改和删除加锁项。
        2. Gap Lock:对索引项之间的'间隙'加锁，锁记录范围 (对第一条记录前的间隙或最后一条将记录后的间隙加锁），不包含索引项本身。
        其他事务不能在锁范围内插入数据，这样就防止了别的事务新增幻影行(幻读)。
        3. Next-key Lock:锁定索引项本身和索引范围。即Record Lock和Gap Lock的结合。可解决幻读问题。

    事务更新大表中的大部分数据直接使用表级锁效率更高；
    事务比较复杂，使用行级索很可能引起死锁导致回滚。
    
    其他相关：
    1. innodb对于行的查询使用next-key lock
    2. Next-locking keying为了解决Phantom Problem幻读问题
    3. 当查询的索引含有唯一属性时，将next-key lock降级为record key
    4. Gap锁设计的目的是为了阻止多个事务将记录插入到同一范围内，而这会导致幻读问题的产生
    5. 有两种方式显式关闭gap锁：（除了外键约束和唯一性检查外，其余情况仅使用record lock） 
        A. 将事务隔离级别设置为Read Committed
        B. 将参数innodb_locks_unsafe_for_binlog设置为1

  - 按是否可写分类（表级锁和行级锁可以进一步划分为共享锁（s）和排他锁（X））
    - 共享锁（读锁）
        共享锁（Share Locks，简记为S）又被称为读锁，其他用户可以并发读取数据，但任何事务都不能获取数据上的排他锁，直到已释放所有共享锁。
        若事务T对数据对象A加上S锁，则事务T只能读A；其他事务只能再对A加S锁，而不能加X锁，直到T释放A上的S锁。这就保证了其他事务可以读A，但在T释放A上的S锁之前不能对A做任何修改。

    - 排它锁（写锁）
        排它锁（(Exclusive lock,简记为X锁)）又称为写锁，若事务T对数据对象A加上X锁，则只允许T读取和修改A，其它任何事务都不能再对A加任何类型的锁，直到T释放A上的锁。
        它防止任何其它事务获取资源上的锁，直到在事务的末尾将资源上的原始锁释放为止。
        在更新操作(INSERT、UPDATE 或 DELETE)过程中始终应用排它锁。
  区别：
        1. 共享锁（S锁）：如果事务T对数据A加上共享锁后，则其他事务只能对A再加共享锁，不能加排他锁。
        获取共享锁的事务只能读数据，不能修改数据。
    
        2. 排他锁（X锁）：如果事务T对数据A加上排他锁后，则其他事务不能再对A加任任何类型的封锁。
        获取排他锁的事务既能读数据，又能修改数据。
    
    - 另外两个表级锁：IS和IX
        意向锁的作用就是当一个事务在需要获取资源锁定的时候，如果遇到自己需要的资源已经被排他锁占用的时候，该事务可以需要锁定行的表上面添加一个合适的意向锁。
        如果自己需要一个共享锁，那么就在表上面添加一个意向共享锁。而如果自己需要的是某行（或者某些行）上面添加一个排他锁的话，则先在表上面添加一个意向排他锁。
        意向共享锁可以同时并存多个，但是意向排他锁同时只能有一个存在。

        InnoDB另外的两个表级锁：
        1. 意向共享锁（IS）： 表示事务准备给数据行记入共享锁，事务在一个数据行加共享锁前必须先取得该表的IS锁。
        
        2. 意向排他锁（IX）： 表示事务准备给数据行加入排他锁，事务在一个数据行加排他锁前必须先取得该表的IX锁。
        
- 死锁和避免死锁
    原因：
    InnoDB的行级锁是基于索引实现的，如果查询语句为命中任何索引，那么InnoDB会使用表级锁. 
    此外，InnoDB的行级锁是针对索引加的锁，不针对数据记录，因此即使访问不同行的记录，如果使用了相同的索引键仍然会出现锁冲突。
    不同于MyISAM总是一次性获得所需的全部锁，InnoDB的锁是逐步获得的，当两个事务都需要获得对方持有的锁，导致双方都在等待，这就产生了死锁。
    
    避免死锁：
        1. 通过表级锁来减少死锁产生的概率；
        2. 如果不同程序会并发存取多个表，尽量约定以相同的顺序访问表，可以大大降低死锁机会。
        3. 同一个事务尽可能做到一次锁定所需要的所有资源。
    发生死锁：发生死锁后，InnoDB一般都可以检测到，并使一个事务释放锁回退，另一个则可以获取锁完成事务。


- #### mysql索引的底层数据结构
1. 哈希索引 底层的数据结构就是哈希表，因此在绝大多数需求为单条记录查询的时候，可以选择哈希索引，查询性能最快。其余大部分场景，建议选择BTree索引。
2. BTree索引(主要是B+树) 搜索性能等价于在关键字全集内做一次二分查找
    1. B-树
        特性：
            1.关键字集合分布在整颗树中；
            2.任何一个关键字出现且只出现在一个结点中；
            3.搜索有可能在非叶子结点结束；
            4.其搜索性能等价于在关键字全集内做一次二分查找；
            5.自动层次控制；
            
    2. B+树（B-Tree的一种变种）
        特性（不同于B-Tree的）：
            1.非叶子结点的子树指针与关键字个数相同；
            2.非叶子结点的子树指针P[i]，指向关键字值属于[K[i], K[i+1])的子树（B-树是开区间）；
            3.为所有叶子结点增加一个链指针；
            4.所有关键字都在叶子结点出现；
            5.内节点不存储data，只存储key
           
       a. MYISAM 非聚集索引 叶节点data域存放数据记录的地址，辅助索引和主索引的区别是辅助索引可以有重复的key
       b. InnoDB 聚集索引 叶节点data域存放完整的数据记录，数据文件本身要按主键聚集（必须要主键），辅助索引都引用主键作为data域（所以使用辅助索引检索，理论要检索两次）。
    - B树相比平衡二叉树
        1.B+-tree的磁盘读写代价更低
        2.B+-tree的查询效率更加稳定
    - 了解不同存储引擎的索引实现方式对于正确使用和优化索引都非常有帮助：
        1. 例如知道了InnoDB的索引实现后，就很容易明白为什么不建议使用过长的字段作为主键，因为所有辅助索引都引用主索引，过长的主索引会令辅助索引变得过大。
        2. 再例如，用非单调的字段作为主键在InnoDB中不是个好主意，因为InnoDB数据文件本身是一颗B+Tree，非单调的主键会造成在插入新记录时数据文件为了维持B+Tree的特性而频繁的分裂调整，十分低效，而使用自增字段作为主键则是一个很好的选择。
- #### mysql大表优化
    - 限定数据范围
    - 读写分离
    - 垂直分表
    - 水平分区(分片的常见方案)
        1. 客户端代理（Sharding-JDBC）
        2. 中间件代理（mycat）
        
    -  ###### 分库分表
        1. 
        
- #### 分布式事务
参考[https://www.cnblogs.com/jiangyu666/p/8522547.html](https://www.cnblogs.com/jiangyu666/p/8522547.html)

   解决方案：
    1. 基于XA协议的两阶段提交方案。
    2. TCC方案
    3. 基于消息的最终一致性方案
    4. GTS--分布式事务解决方案

- #### mysql集群相关？？