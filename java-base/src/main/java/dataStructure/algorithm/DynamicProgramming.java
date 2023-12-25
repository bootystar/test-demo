package dataStructure.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 动态规划算法
 * 背包问题
 * 背包所能容纳的重量为c,
 * 现有n个不同物品,
 * 物品n价值为pn,重量为wn,
 * 在不装入重复物品的情况下,求背包内能装入容量的最大价值
 *
 * @author booty
 * @date 2021/7/9 09:21
 */
public class DynamicProgramming {

    public static void main(String[] args) {
        Item item1 = new Item("吉他", 1000, 1);
        Item item2 = new Item("显示器", 1600, 2);
        Item item3 = new Item("电脑", 2000, 3);
        Item item4 = new Item("音箱", 2400, 4);

        List<Item> itemList = putInPackage(6, item1, item2, item3, item4);
        itemList.forEach(System.out::println);
        Optional<Double> reduce = itemList.stream().map(Item::getPrice).reduce(Double::sum);
        System.out.println("最大价值: " + reduce.get());
    }


    /**
     * 计算放入背包物品的最大价值
     *
     * @param packageCapacity 背包容量
     * @param items           要放入的物品
     * @return 存放的物品列表
     */
    public static List<Item> putInPackage(int packageCapacity, Item... items) {
        //存放最高价值的表格(+1是因为 第一行用于表示未存放物品时,总价值为0,第一列表示 容量为0时,总价值为0)
        double[][] form = new double[items.length + 1][packageCapacity + 1];

        //遍历物品,每一行代表一个物品,从1开始是因为 0代表背包内没有装物品时的价值
        for (int row = 1; row < form.length; row++) {
            //物品在物品数组中对应的下标,就是行号-1
            Item item = items[row - 1];
            //遍历背包容量(从1开始,背包容量为0时,默认价值为0)
            for (int cap = 1; cap < packageCapacity + 1; cap++) {
                //获取该容量最大价值的物品(上一个物品在该容量的价值)
                double lastMaxPrice = form[row - 1][cap];

                //若当前容量能放入该物品
                if (item.weight > cap) {
                    //当前容量无法装下物品,标记该容量的价值为上一个物品在该容量的价值
                    //如此,下次遍历时,上一个容量的价值,始终对应最大价值
                    form[row][cap] = lastMaxPrice;
                } else {
                    //当前容量能装下物品,标记剩余容量
                    int remainCap = cap - item.weight;
                    //获取剩余容量的最大价值
                    double remainCapMaxPrice = form[row - 1][remainCap];

                    //标记当前价值 = 物品价值+剩余容量最大价值
                    double currentPrice = item.price + remainCapMaxPrice;
                    //比较当前物品和上一个物品价值谁大, 将当前价值标记为大的那个
                    form[row][cap] = Double.max(currentPrice, lastMaxPrice);
                }
            }
        }
        //存放放入物品的list
        List<Item> itemList = new ArrayList<>();
        double maxPrice = 0;
        int itemIndex = 0;
        //遍历表格情况,并标记最大价值和对赢物品下标
        for (int y = 0; y < form.length; y++) {
            for (int x = 0; x < form[y].length; x++) {
                System.out.print(form[y][x] + "  ");
                if (form[y][x] > maxPrice) {
                    maxPrice = form[y][x];
                    itemIndex = y - 1;
                }
            }
            System.out.println();
        }
        //若最大价值不为0,则说明放入了物品
        if (maxPrice != 0) {
            //标记容量
            int remainCap = packageCapacity;
            //剩余容量大于0时,循环
            while ((remainCap > 0)) {
                //将该物品放入list
                itemList.add(items[itemIndex]);
                //更新容量 减去当前容量
                remainCap -= items[itemIndex].weight;
                //标记该容量第一个出现的最大价值的
                double capFirstMax = 0;
                for (int i = 1; i < form.length; i++) {
                    //判断是否大于最大价值,仅大于时操作,不大于不做操作
                    if (form[i][remainCap] > capFirstMax) {
                        //更新最大价值
                        capFirstMax = form[i][remainCap];
                        //将下一个要放入的物品下标标记为当前容量第一个最大价值的物品
                        itemIndex = i - 1;
                    }
                }
            }
        }
        return itemList;
    }


    private static class Item {
        private String name;
        private double price;
        private int weight;

        public Item(String name, double price, int weight) {
            this.name = name;
            this.price = price;
            this.weight = weight;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    ", weight=" + weight +
                    '}';
        }
    }


}
