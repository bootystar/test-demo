package dataStructure.algorithm;

/**
 * 递归算法
 * 楼梯问题
 * 给定n级台阶，可以一次走一级，也可以一次走两级，
 * 编程实现：
 * 共有多少种走法
 *
 * @author booty
 * @date 2021/6/24 16:30
 */
public class Stairs {

    /**
     * 楼梯问题
     *
     * @param n 剩余台阶数
     * @return 走法
     */
    public static int stairs(int n) {
        if (n < 3) {
            //若剩余楼梯数小于3时，2级楼梯2种走法（1+1或2），1级楼梯仅一种走法
            return n;
        } else {
            //下一步走一步的走法
            int count1 = stairs(n - 1);
            //下一步走两步的走法
            int count2 = stairs(n - 2);
            //当前走法
            return count1 + count2;
        }

    }

    public static void main(String[] args) {
        System.out.println(stairs(2));
        System.out.println(stairs(3));
        System.out.println(stairs(4));
        System.out.println(stairs(5));
        System.out.println(stairs(6));
        System.out.println(stairs(7));
        System.out.println(stairs(8));
        System.out.println(stairs(20));
    }

}
