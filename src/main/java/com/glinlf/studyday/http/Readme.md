# HTTP

### 五层协议
    - 在计算机网络中中和OSI和TCP/IP的优点，采用一种只有五层协议的体系结构。
    1. OSI：应用层 表示层 回话层 运输层 网络层 数据链路层 物理层
    2. TCP/IP：应用层 运输层 网际层IP 网络接口层
    3. 五层协议：应用层 运输层 网络层 数据链路层 物理层
    
    - 说明：
        1. 应用层：应用层(application-layer）的任务是通过应用进程间的交互来完成特定网络应用。应用层协议定义的是应用进程（进程：主机中正在运行的程序）间的通信和交互的规则。对于不同的网络应用需要不同的应用层协议。在互联网中应用层协议很多，如域名系统DNS，支持万维网应用的 HTTP协议，支持电子邮件的 SMTP协议等等。我们把应用层交互的数据单元称为报文。
        2. 运输层：运输层(transport layer)的主要任务就是负责向两台主机进程之间的通信提供通用的数据传输服务。应用进程利用该服务传送应用层报文。
            - 主要协议：
                1. 传输控制协议TCP（Transmission Control Protocol）--提供面向连接的，可靠的数据传输服务。
                2. 用户数据协议 UDP（User Datagram Protocol）--提供无连接的，尽最大努力的数据传输服务（不保证数据传输的可靠性）。
            - UDP 的主要特点
                UDP 是无连接的；
                UDP 使用尽最大努力交付，即不保证可靠交付，因此主机不需要维持复杂的链接状态（这里面有许多参数）；
                UDP 是面向报文的；
                UDP 没有拥塞控制，因此网络出现拥塞不会使源主机的发送速率降低（对实时应用很有用，如 直播，实时视频会议等）；
                UDP 支持一对一、一对多、多对一和多对多的交互通信；
                UDP 的首部开销小，只有8个字节，比TCP的20个字节的首部要短。
            - TCP 的主要特点
                TCP 是面向连接的。（就好像打电话一样，通话前需要先拨号建立连接，通话结束后要挂机释放连接）；
                每一条 TCP 连接只能有两个端点，每一条TCP连接只能是点对点的（一对一）；
                TCP 提供可靠交付的服务。通过TCP连接传送的数据，无差错、不丢失、不重复、并且按序到达；
                TCP 提供全双工通信。TCP 允许通信双方的应用进程在任何时候都能发送数据。TCP 连接的两端都设有发送缓存和接收缓存，用来临时存放双方通信的数据；
                面向字节流。TCP 中的“流”（Stream）指的是流入进程或从进程流出的字节序列。“面向字节流”的含义是：虽然应用程序和 TCP 的交互是一次一个数据块（大小不等），但 TCP 把应用程序交下来的数据仅仅看成是一连串的无结构的字节流。

        3. 网络层：在 计算机网络中进行通信的两个计算机之间可能会经过很多个数据链路，也可能还要经过很多通信子网。网络层的任务就是选择合适的网间路由和交换结点， 确保数据及时传送。 在发送数据时，网络层把运输层产生的报文段或用户数据报封装成分组和包进行传送。在 TCP/IP 体系结构中，由于网络层使用 IP 协议，因此分组也叫 IP 数据报 ，简称 数据报。
        4. 数据链路层：数据链路层(data link layer)通常简称为链路层。两台主机之间的数据传输，总是在一段一段的链路上传送的，这就需要使用专门的链路层的协议。 在两个相邻节点之间传送数据时，数据链路层将网络层交下来的 IP 数据报组装程帧，在两个相邻节点间的链路上传送帧。每一帧包括数据和必要的控制信息（如同步信息，地址信息，差错控制等）。
        5. 在物理层上所传送的数据单位是比特。 物理层(physical layer)的作用是实现相邻计算机节点之间比特流的透明传送，尽可能屏蔽掉具体传输介质和物理设备的差异。

### TCP 三次握手和四次挥手
   - 三次握手：
        ```
        a. 客户端–发送带有 SYN 标志的数据包–一次握手–服务端
        
        b. 服务端–发送带有 SYN/ACK 标志的数据包–二次握手–客户端
        
        c. 客户端–发送带有带有 ACK 标志的数据包–三次握手–服务端
        ```     
        1. 为什么要三次握手？
            - 三次握手的目的是建立可靠的通信信道，说到通讯，简单来说就是数据的发送与接收，而三次握手最主要的目的就是双方确认自己与对方的发送与接收是正常的。
        2. 为什么要回传syn
            - 接收端传回发送端所发送的 SYN 是为了告诉发送端，我接收到的信息确实就是你所发送的信号了。
        3. 传了 SYN,为啥还要传 ACK
            - 双方通信无误必须是两者互相发送信息都无误。传了 SYN，证明发送方到接收方的通道没有问题，但是接收方到发送方的通道还需要 ACK 信号来进行验证。
    
   - 四次挥手（断开TCP连接）
   
   ```
   1. 客户端-发送一个 FIN，用来关闭客户端到服务器的数据传送
   2. 服务器-收到这个 FIN，它发回一 个 ACK，确认序号为收到的序号加1 。
      和 SYN 一样，一个 FIN 将占用一个序号
   3. 服务器-关闭与客户端的连接，发送一个FIN给客户端
   4. 客户端-发回 ACK 报文确认，并将确认序号设置为收到序号加1

   ```
   - 为什么要四次挥手（双方完全关闭连接）
        - 任何一方都可以在数据传送结束后发出连接释放的通知，待对方确认后进入半关闭状态。当另一方也没有数据再发送的时候，则发出连接释放通知，对方确认后就完全关闭了TCP连接。
          
   - 为什么建立连接是三次握手，关闭连接确是四次挥手呢？
      
      建立连接的时候， 服务器在LISTEN状态下，收到建立连接请求的SYN报文后，把ACK和SYN放在一个报文里发送给客户端。
      而关闭连接时，服务器收到对方的FIN报文时，仅仅表示对方不再发送数据了但是还能接收数据，而自己也未必全部数据都发送给对方了，所以己方可以立即关闭，也可以发送一些数据给对方后，再发送FIN报文给对方来表示同意现在关闭连接，因此，己方ACK和FIN一般都会分开发送，从而导致多了一次。

   [参考链接](https://blog.csdn.net/qzcsu/article/details/72861891)  

### TCP和UDP的区别

- UDP 在传送数据之前不需要先建立连接，远地主机在收到 UDP 报文后，不需要给出任何确认。虽然 UDP 不提供可靠交付，但在某些情况下 UDP 确是一种最有效的工作方式（一般用于即时通信），比如： QQ 语音、 QQ 视频 、直播等等

- TCP 提供面向连接的服务。在传送数据之前必须先建立连接，数据传送结束后要释放连接。 TCP 不提供广播或多播服务。由于 TCP 要提供可靠的，面向连接的运输服务（TCP的可靠体现在TCP在传递数据之前，会有三次握手来建立连接，而且在数据传递时，有确认、窗口、重传、拥塞控制机制，在数据传完后，还会断开连接用来节约系统资源），这一难以避免增加了许多开销，如确认，流量控制，计时器以及连接管理等。这不仅使协议数据单元的首部增大很多，还要占用许多处理机资源。TCP 一般用于文件传输、发送和接收邮件、远程登录等场景。

### TCP 协议如何保证可靠传输

1. 应用数据被分割成 TCP 认为最适合发送的数据块。
2. TCP 给发送的每一个包进行编号，接收方对数据包进行排序，把有序数据传送给应用层。
3. 校验和： TCP 将保持它首部和数据的检验和。这是一个端到端的检验和，目的是检测数据在传输过程中的任何变化。如果收到段的检验和有差错，TCP 将丢弃这个报文段和不确认收到此报文段。
4. TCP 的接收端会丢弃重复的数据。
5. 流量控制： TCP 连接的每一方都有固定大小的缓冲空间，TCP的接收端只允许发送端发送接收端缓冲区能接纳的数据。当接收方来不及处理发送方的数据，能提示发送方降低发送的速率，防止包丢失。TCP 使用的流量控制协议是可变大小的滑动窗口协议。 （TCP 利用滑动窗口实现流量控制）
6. 拥塞控制： 当网络拥塞时，减少数据的发送。
7. 停止等待协议 也是为了实现可靠传输的，它的基本原理就是每发完一个分组就停止发送，等待对方确认。在收到确认后再发下一个分组。
8. 超时重传： 当 TCP 发出一个段后，它启动一个定时器，等待目的端确认收到这个报文段。如果不能及时收到一个确认，将重发这个报文段。
    

### 浏览器输入url->显示主页的过程

1. DNS解析
2. TCP连接
3. 发送HTTP请求
4. 服务器处理请求并返回HTTP报文
5. 浏览器解析渲染页面
6. 连接结束

[参考链接](https://segmentfault.com/a/1190000006879700)

### SSL/TSL握手过程
    - HTTPS 请求 在创建与服务端链接的时候 会在HTTP 三次握手后再次建立一个 SSL/TLS安全确认握手：
    SSL握手详细过程：
    
    一、客户端发出加密通信请求ClientHello
    
    提供： 
    
    1，协议版本（如TLS1.0） 
    
    2，随机数1（用于生成对话密钥） 
    
    3，支持的加密方法（如RSA公钥加密） 
    
    4，支持的压缩方法
    
    二、服务器回应SeverHello
    
    回应内容： 
    
    1，确认使用的加密通信协议版本（TSL1.0） 
    
    2，随机数2（用于生成对话密钥） 
    
    3，确认加密方法（RSA） 
    
    4，服务器证书（包含非对称加密的公钥） 
    
    5，（可选）要求客户端提供证书的请求
    
    三、客户端验证证书
    
    如果证书不是可信机构颁布，或证书域名与实际域名不符，或者证书已经过期，就会向访问者显示一个警告，是否继续通信
    
    四、客户端回应
    
    证书没有问题，就会取出证书中的服务器公钥 
    
    然后发送： 
    
    1，随机数3（pre-master key，此随机数用服务器公钥加密，防止被窃听） 
    
    2，编码改变通知（表示随后的信息都将用双方商定的方法和密钥发送） 
    
    3，客户端握手结束通知
    
    五、双方生成会话密钥
    
    双方同时有了三个随机数，接着就用事先商定的加密方法，各自生成同一把“会话密钥” 
    
    服务器端用自己的私钥（非对称加密的）获取第三个随机数，会计算生成本次所用的会话密钥（对称加密的密钥），如果前一步要求客户端证书，会在这一步验证
    
    六、服务器最后响应
    
    服务器生成会话密钥后，向客户端发送： 
    
    1，编码改变通知（后面的信息都用双方的加密方法和密钥来发送） 
    
    2，服务器握手结束通知

    至此，握手阶段全部结束，接下来客户端与服务器进入加密通信，用会话密钥加密内容
    
    
HTTPS简单请求过程：

    建立服务器443端口连接
    
    SSL握手：随机数，证书，密钥，加密算法
    
    发送加密请求
    
    发送加密响应
    
    关闭SSL
    
    关闭TCP
    
HTTPS 的SSL握手简单过

    客户端发送随机数1，支持的加密方法（如RSA公钥加密）
    
    服务端发送随机数2，和服务器公钥，并确认加密方法
    
    客户端发送用服务器公钥加密的随机数3
    
    服务器用私钥解密这个随机数3，用加密方法计算生成对称加密的密钥给客户端，
    
    接下来的报文都用双方协定好的加密方法和密钥，进行加密
    

    