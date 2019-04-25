package com.glinlf.studyday.collection.list;

import org.junit.Test;

import javax.swing.*;
import java.util.*;

/**
 * @author glinlf
 * @date 2019-03-21
 * @Description TODO
 **/
public class ListStudy {

    /**
     * 底层Object的数组结构
     * 可随机遍历 速度快
     * 修改操作涉及移动 n-i 个位置。耗费性能和时间
     *
     * @return
     */
    public static List getArrayList() {
        List list = new ArrayList();
        list.add("123");
        list.add("123");
        list.add("str");
        list.add(null);
        return list;
    }

    /**
     * 底层双向链表结构 对外暴露first 和 last（直接后继，直接前驱）
     * 1.7之前为 双向循环链表
     *
     * @return
     */
    public static List getLinkedList() {
        List linkedList = new LinkedList();
        linkedList.add("123");
        linkedList.add("123");
        linkedList.add("str");
        linkedList.add(null);
        linkedList.add(12);
        return linkedList;
    }

    public static void main(String[] args) {


        Integer[] number = {1, 2, 3, null};
        System.out.println(Arrays.asList(number));

        // LinkedList 删除
        List linkedList = ListStudy.getLinkedList();
        System.out.println(linkedList);
        linkedList.remove(1);
        System.out.println(linkedList);
        linkedList.remove(null);
        System.out.println(linkedList);
        linkedList.remove(new Integer(12));
        System.out.println(linkedList);
        // LinkedList 插入语 内部使用二分法 遍历找到当前下标的元素。将元素的
        linkedList.add(0, 22);
        System.out.println(linkedList);

        for (int i = 0; i < 0; i++) {
            System.out.println("jj");
        }

        System.out.println(linkedList.get(1));
    }

    /**
     * Iterator he ListIterator的区别
     */
    @Test
    public void testIterator() {
        List list = ListStudy.getArrayList();
        Iterator iterator = list.iterator(); // 创建迭代器
        while (iterator.hasNext()) {
//            if (iterator.next() == "str") {
//                list.add(321);  // 不允许插入或修改list，抛出线程不安全修改异常(源码对比了list的修改版本号)， 但是允许使用Iterator的remove删除操作。
//                list.remove(iterator.next()); // 不允许 只能使用iterator的删除方法
//                iterator.remove();
//            }
            System.out.println(iterator.next());
        }

        ListIterator listIterator = list.listIterator();

//        listIterator.hasPrevious(); // 逆向遍历
        while (listIterator.hasNext()) {
            int index = listIterator.nextIndex(); // nextIndex 要在 listIterator.next()之前获取。不然下标加1了
            Object obj = listIterator.next();
            System.out.println("index:" + index + ",value:" + obj);
            if (obj == null) {
                listIterator.add(321); // ListIterator 允许增加删除修改等操作
//                listIterator.remove();
            }
        }

        for (Object o : list) {
            System.out.println(o);
        }
    }
}
