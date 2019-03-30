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
- 索引
- 索引优化
- 查询优化
- 事务遵循的规则
- 事务隔离级别
- 事务的传播级别
- 锁机制（MYISAM存储引擎和InnoDB存储引擎的锁）
    （细粒度分类）
    - 表锁
    - 行锁
    （是否可写分类）
    - 共享锁（读锁）
    - 排它锁（写锁）
- 死锁和避免死锁
- mysql底层的数据结构
    B-树
    B+树
        MYISAM 非聚集索引 叶节点data域存放数据记录的地址，辅助索引和主索引的区别是辅助索引可以有重复的key
        InnoDB 聚集索引 叶节点data域存放完整的数据记录，数据文件本身要按主键聚集（必须要主键），辅助索引都引用主键作为data域（所以使用辅助索引查寻，理论要查询两次）。
- 大表优化
    - 限定数据范围
    - 读写分离
    - 垂直分表
    - 水平分区(分片的常见方案)
        1. 客户端代理（Sharding-JDBC）
        2. 中间件代理（mycat）
- mysql集群相关？？

### Redis



### 网络