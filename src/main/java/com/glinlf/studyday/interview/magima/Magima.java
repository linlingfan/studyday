package com.glinlf.studyday.interview.magima;

/**
 * @author glinlf
 * @date 2019-04-08
 * @Description 有 n个人围成一圈，顺序排号。从第一个人开始报数（从 1到 3报数），凡报到 3的 人退出圈子，问最后留下的是原来第几号的那位。
 * 思路：
 * 顺序排号 ，看做一个size为n的数组。报数为3 当前值设为false；循环到n+1下标设为0重新开始。
 **/
public class Magima {

    public static void main(String[] args) {
        int lastIndex = getLastOneIndex(12);
        System.out.println("最后留下的是原来第" + lastIndex + "号的那位");
    }

    public static int getLastOneIndex(int num) {
        if (num < 1) {
            return 0;
        }
        if (num == 1) {
            return num;
        }
        boolean[] arrs = new boolean[num];
        for (int i = 0; i < arrs.length; i++) {
            arrs[i] = true;
        }
        int leftNum = num;
        int countNum = 0;
        int index = 0;
        while (leftNum > 1) {
            if (arrs[index] == true) {
                countNum++;
                if (countNum == 3) {
                    countNum = 0;
                    arrs[index] = false;
                    leftNum--;
                }
            }
            index++;
            if (index == num) {
                index = 0;
            }
        }
        int lastIndex = 0;
        for (int i = 0; i < num; i++) {
            if (arrs[i] == true) {
                lastIndex = i + 1;
            }
        }
        return lastIndex;
    }

// 编写前序遍历的实现
    public class Node {
        public Node parent;
        public Node left;
        public Node right;
        public String data; // “a”, “b”, “c”, …等各个节点的各自具体的值。
    }

    public interface INodeHandler {
        public void handle(Node n);
    }

    public class NodePrinter implements INodeHandler {
        public void handle(Node n) {
            System.out.print(n.data); // 在控制台上打印出节点，无回车换行。
        }
    }

    public class Tree {
        private Node root;

        public Tree(Node root) {
            this.root = root;
        }

        public void travel(INodeHandler nh) {
            this.travel(nh, root);
        }

        private void travel(INodeHandler nh, Node node) {//你的前序遍历代码
            // 递归实现
            Node leftNode = node.left;
            if (leftNode != null) {
                nh.handle(leftNode);
                travel(nh, leftNode);
            }
            Node rightNode = node.right;
            if (rightNode != null) {
                nh.handle(rightNode);
                travel(nh, rightNode);
            }
        }
    } // public class Tree
}
