package dataStructure.sort;

/**
 * @author booty
 * @date 2021/6/28 11:04
 */
public class CompareSort {

    /**
     * 比较交换排序
     * 获取最小值，将其排在第一位，
     * 然后获取剩下元素中的最小值，排在第二位，
     * 依次类推
     * <p>
     * 相比冒泡效率较高一点，减少了赋值的过程
     */
    public static int[] compareSort(int[] arr) {
        int min, index;
        for (int i = 0; i < arr.length - 1; i++) {
            //最小值
            min = arr[i];
            //最小值对应的下标
            index = i;
            for (int j = i + 1; j < arr.length; j++) {
                //记录最小值的下标和数值
                if (arr[j] < min) {
                    min = arr[j];
                    index = j;
                }
            }
            //若值发生改变，将最小值和当前值交换
            if (min != arr[i]) {
                arr[index] = arr[i];
                arr[i] = min;
            }
        }
        return arr;
    }
}
