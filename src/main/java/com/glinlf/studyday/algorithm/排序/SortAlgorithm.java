package com.glinlf.studyday.algorithm.排序;

import java.util.Arrays;

/**
 * @author glinlf
 * @date 2019-05-05
 * @Description 几种经典排序算法
 **/
public class SortAlgorithm {

    // 冒泡排序： O(n^2)
    public static int[] bubbleSort(int[] arrs) {
        if (null == arrs || arrs.length == 0) {
            return arrs;
        }
        for (int i = 0; i < arrs.length; i++) {
            for (int j = 0; j < arrs.length - 1 - i; j++) {
                if (arrs[j] > arrs[j + 1]) {
                    int swap = arrs[j];
                    arrs[j] = arrs[j + 1];
                    arrs[j + 1] = swap;
                }
            }
        }
        return arrs;
    }

    // 选择排序 选择最小或最大的 元素下标。最后交换位置 O(n^2)
    public static int[] selectSort(int[] arrs) {
        if (null == arrs || arrs.length == 0) {
            return arrs;
        }
        for (int i = arrs.length - 1; i >= 0; i--) { // 从右边最大开始排序
            int maxIndex = i;
            for (int j = i; j >= 0; j--) {
                if (arrs[j] > arrs[maxIndex]) {
                    maxIndex = j;
                }
            }
            int swap = arrs[maxIndex];
            arrs[maxIndex] = arrs[i];
            arrs[i] = swap;

        }
        return arrs;
    }

    public static int[] selectSortMin(int[] arrs) {
        if (null == arrs || arrs.length == 0) {
            return arrs;
        }
        for (int i = 0; i < arrs.length; i++) { // 从右边最大开始排序
            int minIndex = i;
            for (int j = i; j < arrs.length; j++) {
                if (arrs[j] < arrs[minIndex]) {
                    minIndex = j;
                }
            }
            int swap = arrs[minIndex];
            arrs[minIndex] = arrs[i];
            arrs[i] = swap;

        }
        return arrs;
    }

    // 插入排序 O(n^2)
    public static int[] insertSort(int[] arrs) {
        if (null == arrs || arrs.length == 0) {
            return arrs;
        }
        int insertNum;
        for (int i = 0; i < arrs.length - 1; i++) {
            insertNum = arrs[i + 1];
            int preIndex = i;
            while (preIndex >= 0 && insertNum < arrs[preIndex]) {
                arrs[preIndex + 1] = arrs[preIndex];
                preIndex--;
            }
            arrs[preIndex + 1] = insertNum;
        }
        return arrs;
    }

    // 快速排序
    public static int[] quickSort(int[] arrs, int start, int end) {
        if (null == arrs || arrs.length < 1 || start < 0 || end < start) {
            return arrs;
        }
        partition(arrs, start, end);
        return arrs;
    }

    // 快排递归算法 时间复杂度O(nlogn)
    public static void partition(int[] arrs, int start, int end) {
        if (start > end)
            return;
        int i = start, j = end;
        int num = arrs[i]; // 取最左边为基数
        while (i < j) {
            while (i < j && arrs[j] >= num) j--;
            arrs[i]=arrs[j];
            while (i < j && arrs[i] <= num) i++;
            arrs[j]=arrs[i];
//            int temp = arrs[i];
//            arrs[i] = arrs[j];
//            arrs[j] = temp;
        }
//        arrs[start]=arrs[i];
//        arrs[i]=num;
        arrs[i] = num; // 填入基数
        partition(arrs, start, i - 1);
        partition(arrs, i + 1, end);
    }

    public static void main(String[] args) {
        int[] arrs = {8, 3, 1, 4, 5, 2, 6, 7};
//        System.out.println(Arrays.toString(SortAlgorithm.bubbleSort(arrs)));
//        System.out.println(Arrays.toString(SortAlgorithm.selectSort(arrs)));
//        System.out.println(Arrays.toString(SortAlgorithm.selectSortMin(arrs)));
//        System.out.println(Arrays.toString(SortAlgorithm.insertSort(arrs)));
        System.out.println(Arrays.toString(SortAlgorithm.quickSort(arrs, 0, arrs.length - 1)));
    }
}
