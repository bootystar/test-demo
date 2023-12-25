package dataStructure.algorithm;

import java.util.Stack;
import java.util.stream.Stream;

/**
 * 分治算法
 * 将大问题拆分为子问题,
 * 先求解子问题,然后从子问题得到原问题的解
 *
 * 汉诺塔游戏
 * 3个塔,ABC, 将A塔的数字从大道小移动到C塔上,一次只能移动一个数字,且小数字必须在大数字上
 *
 * @author booty
 * @date 2021/7/8 16:49
 */
public class HanoiTower {

    public static void main(String[] args) {
        start(7);
    }


    /**
     * 初始化汉诺塔数据
     *
     * @param num 需要移动的物品数量
     */
    public static void start(int num) {
        Tower<Integer> a = new Tower<>(1, "主塔");
        Tower<Integer> b = new Tower<>(2, "中间塔");
        Tower<Integer> c = new Tower<>(3, "目标塔");
        for (int i = num; i > 0; i--) {
            a.push(i);
        }
        //打印初始状态
        printChange(a, b, c);
        //交换位置
        exchange(num, a, b, c);
    }


    /**
     * 递归处理汉诺塔问题
     *
     * @param outTower 取出元素的主塔
     * @param tempTower 临时存放元素的中间塔
     * @param targetTower 放入元素的目标塔
     */
    public static void exchange(int remain, Tower<Integer> outTower, Tower<Integer> tempTower, Tower<Integer> targetTower) {
        if (remain == 1) {
            //取出元素放入目标塔
            Integer pop = outTower.pop();
            targetTower.push(pop);
            //打印状态
            printChange(outTower, tempTower, targetTower);
        } else {
            /*
            若需要取出元素的塔元素数量大于2,将其看做两个整体
            第一个整体为 最下方元素(也就是最大的)
            第二个整体为 上方所有元素看作一个元素处理
            那么,
            先将上方元素放到中间塔上,
            然后最下方元素放到目标塔,
            最后将中间塔元素放入目标塔,即可
             */

            //递归将上方所有元素放入中间塔
            exchange(remain - 1, outTower, targetTower, tempTower);

            //将下方元素放入目标塔
            Integer pop = outTower.pop();
            targetTower.push(pop);

            //打印变化
            printChange(outTower, tempTower, targetTower);

            //递归将中间塔的元素放入目标塔
            exchange(remain - 1, tempTower, outTower, targetTower);

        }
    }

    private static void printChange(Tower<Integer> t1, Tower<Integer> t2, Tower<Integer> t3) {
        System.out.println("=========>");
        Stream.of(t1, t2, t3).sorted().forEach(System.out::println);
    }


    private static class Tower<T> extends Stack<T> implements Comparable<Tower<T>> {
        private final String name;
        private final int order;

        public Tower(int order, String name) {
            super();
            this.order = order;
            this.name = name;
        }

        @Override
        public String toString() {
            String str = name + " [";
            boolean append = false;
            for (Object o : elementData) {
                if (o != null) {
                    append = true;
                    str += o.toString() + ", ";
                }
            }
            if (append) str = str.substring(0, str.length() - 2);
            str += "]";
            return str;
        }


        @Override
        public int compareTo(Tower<T> o) {
            return order - o.order;
        }
    }


}
