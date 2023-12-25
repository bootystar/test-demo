package dataStructure.search;

/**
 * @author booty
 * @date 2021/6/28 14:55
 */
public class InsertSearch {

    /**
     * 插值查找
     * 原理同二分查找
     * 通过计算比例，使偏移量能更快到达目标,
     * 有时候能提高性能，但值的增长分布不均衡时，相比二分查找效率反而会降低
     */
    public static int insertSearch(int target, int[] source) {
        int start = 0;
        int end = source.length - 1;
        int middle;

        while (start < end) {
            middle = start + (end - start) * (target - source[start]) / (source[end] - source[start]);
            if (source[middle] == target) {
                return middle;
            } else if (source[middle] < target) {
                start = middle + 1;
            } else {
                end = middle - 1;
            }
        }
        return -1;
    }
}
