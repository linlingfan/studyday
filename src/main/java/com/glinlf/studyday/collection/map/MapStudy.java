package com.glinlf.studyday.collection.map;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author glinlf
 * @date 2019-03-22
 * @Description 注意 尽量减少hash碰撞的处理（拉链法配合数组长度取模）
 **/
public class MapStudy {

    /**
     * 1.8的hashMap已经做了优化，在多线程情况下 对HashMap扩容不会导致死循环问题（声明两对指针，维护两个连链表，第二个线程重复第一个线程的操作)，
     * 不过仍然可能导致数据丢失等问题（建议使用 ConcurrentHashMap）
     * 初始容量16 扩容的话容量为原来2倍
     *
     * @return
     */
    static Map getHashMap() {
        var hashMap = new HashMap<>();
        hashMap.put("hello", "world");
        hashMap.put(null, "null");
        return hashMap;
    }

    /**
     * 线程安全问题 hashTable效率低下 基本不适用了！
     * 不支持null键
     * 初始长度11 扩容 容量变为原来的2n+1
     *
     * @return
     */
    static Map getHashTable() {
        var map = new Hashtable<>();
        map.put("hello", "hashTable");
//        map.put(null, "null");  // key not null
        return map;
    }

    public static void main(String[] args) {
        var map = MapStudy.getHashMap();
        System.out.println(map);
        var hashTable = MapStudy.getHashTable();
        System.out.println(hashTable);
        String hashStr = new String("hash");
        int hash = hashStr.hashCode();
        System.out.println("hash : " + hash);
        // 无符号 向右移动位符 ：向右移动高位补0。
        System.out.println(hash >>> 16);
        System.out.println((hash) ^ (hash >>> 16));
        // 位异或运算（^）运算规则是：两个数转为二进制，然后从高位开始比较，如果相同则为0，不相同则为1。
        // 转二进制 匹配相同为0 不同为1  2 ：0010  3：0011 2^3 : 0001 = 1
        System.out.println(2 ^ 3);

        // HashMap 数组下标计算方法
        // 位与运算符（&）运算规则：两个数都转为二进制，然后从高位开始比较，如果两个数都为1则为1，否则为0。
        System.out.println((16 - 1) & hash);
        // 补充:
        // 位或运算符（|）运算规则：两个数都转为二进制，然后从高位开始比较，两个数只要有一个为1则为1，否则就为0。
        // 位非运算符（~）运算规则：如果位为0，结果是1，如果位为1，结果是0.
    }
}
