package dataStructure.sort;

import java.util.Arrays;

/**
 * 基数排序
 * (基于桶排序)
 * @author booty
 * @date 2021/6/28 11:09
 */
public class RadixSort {


    /**
     * 基数排序
     * 依照个位数的大小进行排序，将数据放入0-9的队列中
     * 然后按照0-9的顺序取出，先进先出，
     * 之后十位数，百位数
     * 最后排序完毕后，取出时，即为有序
     *
     * 该排序速度大于快速排序和归并排序，
     * 但需要的内存很大，因为要创建10个容纳原数组长度的数组
     */
    public static int[] radixSort(int[] arr) {


        //找出最大值的位数，确定需要排几次
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }
        //排序次数
        int times = (max + "").length();

        //用于存放数据的数组
        int[][] temp = new int[10][arr.length];
        //用于记录存放个数的数组
        int[] counts = new int[10];

        for (int i = 0, n = 1; i <times; i++, n *= 10) {

            //单次排序
            for (int j = 0; j < arr.length; j++) {
                //取余数（指定位的数组）
                int remainder = arr[j] / n % 10;
                //放入指定位数的桶中第计数-1位置
                temp[remainder][counts[remainder]] = arr[j];
                //对饮桶计数+1
                counts[remainder]++;
            }

            //依次取出桶中元素,放入原数组
            int index = 0;
            //遍历计数的数组
            for (int k = 0; k < counts.length; k++) {
                //指定位数字的计数不为0
                if (counts[k] != 0) {
                    //依次取出该指定位数字存放在二维数组的数据，放入原数组
                    for (int l = 0; l < counts[k]; l++) {
                        arr[index] = temp[k][l];
                        index++;
                    }
                    counts[k] = 0;
                }
            }
        }
        return arr;
    }


    public static void main(String[] args) {
        int[] arr = {22, 232, 756, 242, 634, 21, 3, 5, 7, 2, 8, 49, 7};
        radixSort(arr);
        System.out.println(Arrays.toString(arr));
    }

}
