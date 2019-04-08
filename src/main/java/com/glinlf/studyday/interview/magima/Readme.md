# 奇码 面试题

### 1. java 语言部分 1，请翻译下面的英文段落
Every thread has a priority. Threads with higher priority are executed in preference to threads with lower priority. Each thread may or may not also be marked as a daemon. When code running in some thread creates a new Thread object, the new thread has its priority initially set equal to the priority of the creating thread, and is a daemon thread if and only if the creating thread is a daemon.When a Java Virtual Machine starts up, there is usually a single non-daemon thread (which typically calls the method named main of some designated class).
### 2. 请输出以下两段程序结果。

  - 程序 A: 程序 B: 见类 parent and son and magimaTest
  程序 A 输出结果: 程序 B 输出结果:
  
### 3. 请用 java 代码实现一个树的“前序”遍历，如(图 a)，最终遍历打印出如(图 b)的字符串: 1
```
  public class Node { public Node parent; public Node left; public Node right; public String data;
// “a”, “b”, “c”, ...等各个节点的各自具体的值。
}
public interface INodeHandler {
public void handle(Node n); }
public class NodePrinter implements INodeHandler{ public void handle(Node n){
System.out.print(n.data);
public class Tree { private Node root;
public Tree (Node root){ this.root = root;
}
public void travel (INodeHandler nh) {
this.travel(nh, root); }
private void travel(INodeHandler nh,
/ /你的前序遍历代码
}
} // public class Tree
} }
// 在控制台上打印出节点，无回车换行。

```
### 4. 有 n 个人围成一圈，顺序排号。从第一个人开始报数(从 1 到 3 报数)，凡报到 3 的人退出 圈子，问最后留下的是原来第几号的那位。用 java 代码实现。
Node node){
2

### 5. 一个 5L 的杯子和 6L 的杯子怎么量出 3L 的水?
### 6. 编写几条 sql 实现查询用户的微信模板消息可达状态。
```
非常量表:
create table ThirdServiceAccount
(
ThirdServiceAccountID bigint not null auto_increment, RegisterUserID bigint,
ThirdServiceProviderID bigint, AcountTypeID bigint, ThirdAccountIdentity varchar(1024), AccountInfo varchar(4096), CreatedDatetime bigint, LastUpdatedDatetime bigint, isDeleted bool,
DeletedDatetime bigint,
primary key (ThirdServiceAccountID) );
create table ThirdServiceOauthInfo
(
ThirdServiceOauthInfoID bigint not null auto_increment, ThirdServiceAccountID bigint,
3

 ThirdServiceProviderID bigint, ThirdAppInfoID bigint,
AppID varchar(1024),
AccessToken varchar(1024),
RefreshToken varchar(1024),
CreatedDatetime bigint,
LastRefreshDatetime bigint, AccessTokenExpireInSeconds bigint,
Note varchar(2048),
SubscribingStatusID bigint,//关注状态 LastSubscribingStatusModifedDatetime bigint, BiboAccessToken varchar(1024),
ExtInfo varchar(4096),
primary key (ThirdServiceOauthInfoID) );
create table ThirdAppInfo
(
ThirdAppInfoID bigint not null auto_increment, ThirdServiceProviderID bigint,
OrgID bigint,
isEnable bool,
AppID varchar(256),
AppSecret varchar(256),
AppName varchar(128),
Priority int,
AccessToken varchar(1024), AccessTokenExpireInSeconds bigint, LastRefreshDatetime bigint,
ExtInfo varchar(2048),
CreatedDatetime bigint,
primary key (ThirdAppInfoID)
);
create table ThirdAppXCapability
(
ThirdAppXCapabilityID bigint not null auto_increment, AppInfoID bigint,
CapabilityID bigint,
primary key (ThirdAppXCapabilityID) );
常量表及数据:
create table UserThirdSubscribingStatus
(
UserThirdSubscribingStatusID bigint not null auto_increment,
4

 Name varchar(256),
primary key (UserThirdSubscribingStatusID)
); +------------------------------+---------------------------------------------+ | UserThirdSubscribingStatusID | Name +------------------------------+---------------------------------------------+
| 1 | User.Third.Subscribing.Status.Unknow
| 2 | User.Third.Subscribing.Status.Subscribing
| 3 | User.Third.Subscribing.Status.Unsubscribing |//未关注 +------------------------------+---------------------------------------------+
create table ThirdServiceAccountType
(
ThirdServiceAccountTypeID bigint not null auto_increment, ServiceProviderID bigint,
Name varchar(256),
primary key (ThirdServiceAccountTypeID)
); +---------------------------+------------------------------------------+ | ThirdServiceAccountTypeID | Name +---------------------------+------------------------------------------+
| 1 | ThirdService.Account.Type.Tencent.WeiXin |
| 2 | ThirdService.Account.Type.Tencent.QQ
| 3 | ThirdService.Account.Type.Sina.Weibo
| 4 | ThirdService.Account.Type.Email
| 5 | ThirdService.Account.Type.Mobile +---------------------------+------------------------------------------+
create table ThirdServiceProvider
(
ThirdServiceProviderID bigint not null auto_increment, SPName varchar(256),
|
| |
| |
SPInfo varchar(2048),
primary key (ThirdServiceProviderID)
);
+------------------------+----------------------------------------------+--------+
| ThirdServiceProviderID | SPName +------------------------+----------------------------------------------+--------+
| 1 | ThirdServiceProvider.Tencent.Division.WeiXin | NULL
| 2 | ThirdServiceProvider.Tencent.Division.QQ
| 3 | ThirdServiceProvider.Sina.Division.Weibo +------------------------+----------------------------------------------+--------+
create table ThirdAppCapability
(
ThirdAppCapabilityID bigint not null auto_increment,
| SPInfo | |
| NULL | NULL
| |
|//关注
| |
5

 Name varchar(256),
primary key (ThirdAppCapabilityID)
); +----------------------+-----------------------------------------------+ | ThirdAppCapabilityID | Name +----------------------+-----------------------------------------------+
| 1 | ThirdApp.Capability.Unknown
| 2 | ThirdApp.Capability.SendTransactionalMessage 模板消息的能力
+----------------------+-----------------------------------------------+
| |
|//代表有发送微信

```

用户的微信模板消息是否可达几个维度:
1.有帐号;
2.有通过公众号授权及关注(订阅)公众号; 3.公众号本身可用以及有发微信模板消息的能力; 满足以上三个条件则微信模板消息可达用户，否则不可达。
给定 RegisterUserID 为 123，编写 sql 实现查询用户的微信模板消息可达状态。(每条 sql 语句 关联表的数量不能超过两张)。

