package dataStructure.search;


/**
 * @author booty
 * @date 2021/6/28 14:03
 */
public class BinarySearch {


    /**
     * 二分查找（递归）
     * 传入的数组必须有序，效率高于线性查找
     */
    public static int binarySearch(int target, int[] source, int start, int end) {
        if (start < end) {
            int middle = (start + end) / 2;
            if (source[middle] == target) {
                return middle;
            } else if (source[middle] < target) {
                return binarySearch(target, source, middle + 1, end);
            } else if (source[middle] > target) {
                return binarySearch(target, source, start, middle - 1);
            }
        }
        return -1;
    }

    /**
     * 二分查找（循环）
     * 传入的数组必须为有序数组
     */
    public static int binarySearch2(int target, int[] source) {
        int start = 0;
        int end = source.length - 1;
        int middle;
        while (start <= end) {
            middle = (start + end) / 2;
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


    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 11, 13, 15, 17};
        System.out.println(binarySearch(13, arr, 0, arr.length - 1));
        System.out.println(binarySearch(12, arr, 0, arr.length - 1));


        System.out.println(binarySearch2(13, arr));
        System.out.println(binarySearch2(12, arr));

    }


}
