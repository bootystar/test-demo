package dataStructure.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 堆排序
 * (利用顺序存储二叉树的特性,获取左右节点对比进行排序)
 * 将待排序序列构造成一个大顶堆，此时，整个序列的最大值就是堆顶的根节点。
 * 将其与末尾元素进行交换，此时末尾就为最大值。
 * 然后将剩余n-1个元素重新构造成一个堆，这样会得到n个元素的次小值。
 * 如此反复执行，便能得到一个有序序列了
 *
 * @author booty
 * @date 2021/7/2 10:04
 */
public class HeapSort {
    static int[] arr = {9, 6, 8, 7, 0, 1, 10, 2, 4};

    /**
     * 通过一个数组 构建大顶堆
     * 顺序存储结构的二叉树,第一个非叶子节点的下标为arr.length / 2 - 1
     *
     * @param arr 需要排序的数组
     */
    public static void sort(int[] arr) {
        //1.构建大顶堆
        for (int adjustIndex = arr.length / 2 - 1; adjustIndex >= 0; adjustIndex--) {
            //从第一个非叶子结点从下至上调整，调整后数组下标减一,调整之前的节点
            adjustHeap(arr, adjustIndex, arr.length);
        }
        //2.调整堆结构+交换堆顶元素与末尾元素
        for (int lastIndex = arr.length - 1; lastIndex > 0; lastIndex--) {
            //将堆顶元素与末尾元素进行交换
            swap(arr, 0, lastIndex);
            //每次交换会将大的元素放到数组最后,每次调整后调整长度会减一
            //调整时,lastIndex指向的元素和其之后的元素不会参加调整
            //因lastIndex最后会指向0,之前每次都会进行比较交换
            //所以每次只需要调整堆顶3个元素,保证堆顶元素最大即可
            adjustHeap(arr, 0, lastIndex);
        }

    }


    /**
     * 调整大顶堆（仅是调整过程，建立在大顶堆已构建的基础上）
     *
     * @param arr    待排序的数组
     * @param index      需要调整的非叶子节点元素的下标
     * @param length 调整的长度
     */
    public static void adjustHeap(int[] arr, int index, int length) {
        //先取出当前元素i
        int temp = arr[index];
        for (int k = index * 2 + 1; k < length; k = k * 2 + 1) {
            //从i结点的左子结点开始，也就是2i+1处开始
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                //如果左子结点小于右子结点，k指向右子结点
                k++;
            }
            if (arr[k] > temp) {
                //如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
                arr[index] = arr[k];
                index = k;
            } else {
                break;
            }
        }
        arr[index] = temp;//将temp值放到最终的位置
    }


    /**
     * 交换元素
     *
     * @param arr 数据源
     * @param a   交换的元素a
     * @param b   交换的元素b
     */
    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }


    public static void main(String[] args) {

        System.out.println(Arrays.toString(arr));
    }

}
