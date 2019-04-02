# RememberMe

***glinlf-2019-03-27***
***

### 并发
- synchronized相关  done
- ReenTrantLock使用 done
- ReenTranLock 源码分析 
- java 创建线程池相关  done
- Java Thread状态切换 done
- 原子类相关笔记 

### JVM 

### IO

### MYSQL


- #### 索引
    - 什么是索引
    - 如何创建索引
    - 创建索引的原则
    - 索引使用的注意点（具体优化）
- #### 事务相关

    - 事务遵循的规则
    - 事务隔离级别
    - 事务的传播级别
    
- #### 锁机制（MYISAM存储引擎和InnoDB存储引擎的锁）
    （细粒度分类）
    - 表锁
    - 行锁
    （是否可写分类）
    - 共享锁（读锁）
    - 排它锁（写锁）
    - 死锁和避免死锁
- #### mysql底层的数据结构
    - B树
    - B+树
        MYISAM 非聚集索引 叶节点data域存放数据记录的地址，辅助索引和主索引的区别是辅助索引可以有重复的key
        InnoDB 聚集索引 叶节点data域存放完整的数据记录，数据文件本身要按主键聚集（必须要主键），辅助索引都引用主键作为data域（所以使用辅助索引查寻，理论要查询两次）。
- #### 大表优化
    - 限定数据范围
    - 读写分离
    - 垂直分表
    - 水平分区(分片的常见方案)
        1. 客户端代理（Sharding-JDBC）
        2. 中间件代理（mycat）
- #### 分布式事务
参考[https://www.cnblogs.com/jiangyu666/p/8522547.html](https://www.cnblogs.com/jiangyu666/p/8522547.html)

   解决方案：
    1. 基于XA协议的两阶段提交方案。
    2. TCC方案
    3. 基于消息的最终一致性方案
    4. GTS--分布式事务解决方案
- #### mysql集群相关？？

### Redis



### 网络