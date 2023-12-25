package dataStructure.search;

import java.lang.annotation.Target;
import java.util.Arrays;

/**
 * @author booty
 * @date 2021/6/28 16:09
 */
public class FibonacciSearch {


    /**
     * 获取斐波那契数列第n个数的值（递归）
     *
     * @param n 第n个数
     * @return 第n个数的值
     */
    public static int fib(int n) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        return fib(n - 1) + fib(n - 2);
    }

    /**
     * 获取斐波那契数列第n个数的值（循环）
     *
     * @param n 第n个数
     * @return 第n个数的值
     */
    public static int fib2(int n) {
        int a = 0;
        int b = 1;
        if (n == 0)
            return a;
        if (n == 1)
            return b;
        int c = 0;
        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }


    /**
     * 获取指定长度的获取斐波那契数列
     *
     * @param size 长度
     * @return 斐波那契数列
     */
    public static int[] fibArray(int size) {
        int[] arr = new int[size];
        arr[0] = 0;
        arr[1] = 1;
        for (int i = 2; i < arr.length; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        return arr;
    }

    /**
     * 斐波那契查找
     *
     * @param source 原数组
     * @param target    要查找的值
     * @return 下标，未找到为-1
     */
    public static int fibSearch(int target, int[] source) {
        //查找的开始位置
        int start = 0;
        //查找结束的位置
        int end = source.length - 1;
        //查找的分界线mid
        int mid;

        //源数组中最大的数字
        int max = source[end];
        //斐波数列分割数值的下标k
        int k = 0;
        int[] fibArray=fibArray(source.length);
        //0,1,1,2,3,5,8,13,21,34,55
        //找到最大值对应的斐波数列中的下标
        while (end > fibArray[k]-1) {
            k++;
        }
        //若数组的长度不足斐波数列长度，将其长度增加到斐波数列对应值长度
        int[] temp = Arrays.copyOf(source, fibArray[k]);
        for (int i = source.length; i < temp.length; i++) {
            //使用源数组最大的数对源数组空白的数据进行赋值
            temp[i] = max;
        }
        while (start <= end) {
            //查找下标（黄金分割的点就是fib（k-1），此时该点已经处理过了，所以再-1）
            mid = start + fib(k - 1) - 1;
            if (temp[mid] == target) {
                return mid;
            } else if (temp[mid] < target) {
                start = mid + 1;
                //比目标小时，区域为前半段，首先k-1获取的分割点为前半段的结束点（对应二分法的中点）然后再-1获取上上个分割点（对应二分法的1/4点）
                k = k - 2;
            } else {
                //比目标大时，区域为后半段，获取后半段的分割点即可
                end = mid - 1;
                k = k - 1;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] arr = {0,1,16,24,35,47,59,62,73,88,99};
        int n=10;
        int key=59;
        System.out.println(fibSearch(key,arr ));
    }


}
