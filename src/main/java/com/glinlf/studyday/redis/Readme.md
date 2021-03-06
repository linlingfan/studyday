## redis

   - 概述
       - Redis是速度非常快的NoSQL 内存键值数据库。可以存储键和物种不同类型的值的映射。
       - 键的类型只能为字符串，值支持五种数据类型：字符串、列表、集合、散列表、有序集合。
       - redis 支持很多的特性，例如将内存中的数据持久化到硬盘中，使用复制来拓展性能，使用分片来拓展写性能。
###  数据类型 

|数据类型|可以存储的值|操作
|:-|:-|:-|
|String|字符串、整数、浮点数|对整个字符串或者字符串的其中一部分执行操作，对整数和浮点数执行自增或者自减操作|
|List|列表|从两端压入或者弹出元素，对单个或者多个元素，进行修剪，只保留一个范围内的元素|
|Set|无序集合|添加、获取、移除单个元素检查一个元素是否存在于集合中，计算交集、并集、差集，从集合里面随机获取元素。|
|Hash|包含键值的无序散列表|添加、获取、移除单个键值对，获取所有键值对检查某个键是否存在|
|Zset|有序集合|添加、获取、删除元素，根据分值的范围或成员来获取元素，计算一个键的排名。|

### 数据结构

### 使用场景

    - 计数器 
        - 可以对 String 进行自增自减运算，从而实现计数器功能。Redis 这种内存型数据库的读写性能非常高，很适合存储频繁读写的计数量。
    - 缓存
        - 将热点数据放到内存中，设置内存的最大使用量以及淘汰策略来保证缓存的命中率。
    
    - 查找表
        - 例如 DNS 记录就很适合使用 Redis 进行存储。
        - 查找表和缓存类似，也是利用了 Redis 快速的查找特性。但是查找表的内容不能失效，而缓存的内容可以失效，因为缓存不作为可靠的数据来源。
    
    - 消息队列
        - List 是一个双向链表，可以通过 lpush 和 rpop 写入和读取消息。
        - 不过最好使用 Kafka、RabbitMQ 等消息中间件。
    
    - 会话缓存
        - 可以使用 Redis 来统一存储多台应用服务器的会话信息。
        - 当应用服务器不再存储用户的会话信息，也就不再具有状态，一个用户可以请求任意一个应用服务器，从而更容易实现高可用性以及可伸缩性。
    
    - 分布式锁
        - 在分布式场景下，无法使用单机环境下的锁来对多个节点上的进程进行同步。
        - 可以使用 Redis 自带的 SETNX 命令实现分布式锁，除此之外，还可以使用官方提供的 RedLock 分布式锁实现.
    
    - 其他
        - Set 可以实现交集、并集等操作，从而实现共同好友等功能。
        - ZSet 可以实现有序性操作，从而实现排行榜等功能。
          
### Redis 与 Memcached
    - 两者都是非关系型内存键值数据库，主要有以下不同：
        1. 数据类型
            - Memcached 仅支持字符串类型，而 Redis 支持五种不同的数据类型，可以更灵活地解决问题。
        2. 数据持久化
            - Redis 支持两种持久化策略：RDB 快照和 AOF 日志，而 Memcached 不支持持久化。
        3. 分布式
        - Memcached 不支持分布式，只能通过在客户端使用一致性哈希来实现分布式存储，这种方式在存储和查询时都需要先在客户端计算一次数据所在的节点。
          Redis Cluster 实现了分布式的支持。
        4. 内存管理机制
        - 在 Redis 中，并不是所有数据都一直存储在内存中，可以将一些很久没用的 value 交换到磁盘，而 Memcached 的数据则会一直在内存中。
        - Memcached 将内存分割成特定长度的块来存储数据，以完全解决内存碎片的问题。但是这种方式会使得内存的利用率不高，例如块的大小为 128 bytes，只存储 100 bytes 的数据，那么剩下的 28 bytes 就浪费掉了。
        5. Memcached 是多线程，非阻塞IO复用的网络模型；Redis使用的是单线程的多路复用模型。
### Redis过期时间

    - Redis 可以为每个键设置过期时间，当键过期时，会自动删除该键。
      
    - 对于散列表这种容器，只能为整个键设置过期时间（整个散列表），而不能为键里面的单个元素设置过期时间。
    
    - redis默认使用 定期删除+惰性删除。
        1. 定期删除：redis默认是每隔 100ms 就随机抽取一些设置了过期时间的key，检查其是否过期，如果过期就删除。注意这里是随机抽取的。为什么要随机呢？你想一想假如 redis 存了几十万个 key ，每隔100ms就遍历所有的设置过期时间的 key 的话，就会给 CPU 带来很大的负载！
        2. 惰性删除 ：定期删除可能会导致很多过期 key 到了时间并没有被删除掉。所以就有了惰性删除。假如你的过期 key，靠定期删除没有被删除掉，还停留在内存里，除非你的系统去查一下那个 key，才会被redis给删除掉。这就是所谓的惰性删除，也是够懒的哈！

         
### 数据淘汰策略
    - 可以设置内存的最大使用量，当内存使用超出时，会实行数据淘汰策略。
    - Redis有六中淘汰策略：
        1. volatile-lru ：从已经设置的过期时间集中淘选最近使用最少的数据淘汰。
        2. volatile-ttl ： 从已设置过期时间的数据集中挑选将要过期的数据淘汰。
        3. volatile-random：从已设置过期的数据集中任意选择数据淘汰。
        4. allkey-lru：从所有数据集中挑选最近最少使用的数据淘汰
        5. allkey-random：从所有数据集中任意选择数据进行淘汰
        6. no-eviction：禁止驱逐数据
    - 使用 Redis 缓存数据时，为了提高缓存命中率，需要保证缓存数据都是热点数据。可以将内存最大使用量设置为热点数据占用的内存量，然后启用 allkeys-lru 淘汰策略，将最近最少使用的数据淘汰。
    - Redis 4.0 引入了 volatile-lfu 和 allkeys-lfu 淘汰策略，LFU 策略通过统计访问频率，将访问频率最少的键值对淘汰。

### 持久化

- Redis是内存行数据库，为了保证数据在断电后不会丢失，需要将内存中的数据持久化到硬盘上。

    1. 快照持久化RDB（redis默认采用的持久化方式）
        - 将某个时间点的所有数据都放到硬盘上。
        - 可以将快照复制到其它服务器从而创建具有相同数据的服务器副本。
        - 如果系统发生故障，将会丢失最后一次创建快照之后的数据。
        - 如果数据量很大，保存快照的时间会很长。
    2. AOF（append Only File）持久化（主流的持久化方式，需要手动开启 appendonly yes）
        - 将写命令添加到 AOF 文件（Append Only File）的末尾。
        - 开启AOF持久化后每执行一条会更改Redis中的数据的命令，Redis就会将该命令写入硬盘中的AOF文件。AOF文件的保存位置和RDB文件的位置相同，都是通过dir参数设置的，默认的文件名是appendonly.aof。
        - 在Redis的配置文件中存在三种不同的 AOF 持久化方式，它们分别是：
            1. appendfsync always #每次有数据修改发生时都会写入AOF文件,这样会严重降低Redis的速度
            2. appendfsync everysec #每秒钟同步一次，显示地将多个写命令同步到硬盘
            3. appendfsync no #让操作系统决定何时进行同步
        - 为了兼顾数据和写入性能，用户可以考虑 appendfsync everysec选项 ，让Redis每秒同步一次AOF文件，Redis性能几乎没受到任何影响。而且这样即使出现系统崩溃，用户最多只会丢失一秒之内产生的数据。当硬盘忙于执行写入操作的时候，Redis还会优雅的放慢自己的速度以便适应硬盘的最大写入速度。
        
    - Redis 4.0对持久化的优化
        - Redis 4.0 开始支持 RDB 和 AOF 的混合持久化（默认关闭，可以通过配置项 aof-use-rdb-preamble 开启）。
          如果把混合持久化打开，AOF 重写的时候就直接把 RDB 的内容写到 AOF 文件开头。这样做的好处是可以结合 RDB 和 AOF 的优点, 快速加载同时避免丢失过多的数据。当然缺点也是有的， AOF 里面的 RDB 部分是压缩格式不再是 AOF 格式，可读性较差。
        - AOF重写？？？
            - AOF重写可以产生一个新的AOF文件，这个新的AOF文件和原有的AOF文件所保存的数据库状态一样，但体积更小。

### 事务
    - Redis 通过 MULTI、EXEC、WATCH 等命令来实现事务(transaction)功能。事务提供了一种将多个命令请求打包，然后一次性、按顺序地执行多个命令的机制，并且在事务执行期间，服务器不会中断事务而改去执行其他客户端的命令请求，它会将事务中的所有命令都执行完毕，然后才去处理其他客户端的命令请求。
      在传统的关系式数据库中，常常用 ACID 性质来检验事务功能的可靠性和安全性。在 Redis 中，事务总是具有原子性（Atomicity)、一致性(Consistency)和隔离性（Isolation），并且当 Redis 运行在某种特定的持久化模式下时，事务也具有持久性（Durability）。
### 事件
    - redis是一个事件驱动程序
        
        1. 文件事件
            - 服务器通过套接字与客户端或者其它服务器进行通信，文件事件就是对套接字操作的抽象。
            - Redis 基于 Reactor 模式开发了自己的网络事件处理器，使用 I/O 多路复用程序来同时监听多个套接字，并将到达的事件传送给文件事件分派器，分派器会根据套接字产生的事件类型调用相应的事件处理器。
        2. 时间事件
            - 服务器有些操作需要在给定的时间点执行，时间实践是对这些类定时操作的抽象。可分为：
                1.  定时事件：是让一段程序在指定的时间之内执行一次
                2. 周期性事件：是让一段程序每隔指定事件就执行一次
            - Redis 将所有时间事件都放在一个无序链表中，通过遍历整个链表查找出已到达的时间事件，并调用相应的事件处理器。
    - 事件的调度与执行
        - 服务器需要不断监听文件事件的套接字才能得到待处理的文件事件，但是不能一直监听，否则时间事件无法在规定的时间内执行，因此监听时间应该根据距离现在最近的时间事件来决定。
        
    
### redis的主从复制 ？TODO 自己实现一个？？
    - 通过使用 slaveof host port 命令来让一个服务器成为另一个服务器的从服务器。
      
    -  一个从服务器只能有一个主服务器，并且不支持主主复制。
    
### Sentinel哨兵模式 TODO


### redis分片 TODO 分片算法？ hash？






































        
        
        
        
        
        
        
        
        
        
        
        
        