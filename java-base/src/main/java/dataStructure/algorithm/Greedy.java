package dataStructure.algorithm;

import java.util.*;

/**
 * 贪心算法解决集合覆盖问题
 *
 * 例:有5个电台,每个电台覆盖几个城市,现求出覆盖所有城市的最少电台方案
 *
 * @author booty
 * @date 2021/7/12 10:18
 */
public class Greedy {

    public static void main(String[] args) {

        List<String> radio1 = Arrays.asList("北京", "上海", "天津");
        List<String> radio2 = Arrays.asList("北京", "广州", "深圳");
        List<String> radio3 = Arrays.asList("成都", "上海", "杭州");
        List<String> radio4 = Arrays.asList("天津", "上海");
        List<String> radio5 = Arrays.asList("杭州", "大连");

        List<Collection> greedy = greedy(radio1, radio2, radio3, radio4, radio5);
        System.out.println();
    }


    /**
     * 贪心算法
     * 将多个集合中的元素全部覆盖的方案,用到的集合尽量少
     * (结果不一定是最优解,只能保证尽量优)
     *
     * @param coll 集合数组
     * @return 用到的集合
     */
    public static List<Collection> greedy(Collection... coll) {
        //使用到的集合
        List<Collection> result = new ArrayList<>();
        //元素列表
        List<Object> elements = new ArrayList<>();

        //将多个集合中的元素放入 元素列表中
        for (Collection objects : coll) {
            for (Object object : objects) {
                //不包含则放入
                if (!elements.contains(object)) {
                    elements.add(object);
                }
            }
        }

        //当前循环内,包含元素最多的集合
        Collection maxCol = coll[0];
        //当前循环内,包含元素最多的集合所包含的元素数量
        int maxCount = maxCol.size();
        //当前循环内,当前集合包含的元素数量
        int currentCount;
        //元素列表不为空时循环
        while (elements.size() > 0) {
            //循环包含元素的集合
            for (Collection objects : coll) {
                //当前集合包含元素包含设置为0
                currentCount = 0;
                //循环包含元素的集合内的每个元素
                for (Object object : objects) {
                    //若元素列表中有当前集合内的元素,则 当前集合包含元素的计数+1
                    if (elements.contains(object)) {
                        currentCount++;
                    }
                }
                //若当前包含元素数量大于最大包含数量,将最大数量设为当前,将包含元素最多集合设为当前集合
                if (currentCount > maxCount) {
                    maxCol = objects;
                    maxCount=currentCount;
                }
            }
            //将循环找出的包含最多元素的集合添加到结果列表中
            result.add(maxCol);
            //包含最大元素数量重置为0
            maxCount=0;
            //将包含最多元素的集合的元素,从元素列表中移除
            for (Object o : maxCol) {
                elements.remove(o);
            }

            //一直循环,当元素列表size为0时,目前结果内的集合内的元素已经包含所有元素
        }

        return result;
    }

}
