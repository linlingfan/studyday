package com.glinlf.studyday.collection.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
}
