package dataStructure.search;

/**
 * @author booty
 * @date 2021/6/28 14:00
 */
public class LinearSearch {

    /**
     * 线形查找法
     * 基础的查找，对数据内的顺序无要求
     */
    public static int linearSearch(int target,int[] source){
        for (int i = 0; i < source.length; i++) {
            if (target==source[i]){
                return i;
            }
        }
        return -1;
    }

}
