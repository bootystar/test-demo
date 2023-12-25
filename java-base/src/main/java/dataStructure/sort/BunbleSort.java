package dataStructure.sort;

/**
 * @author booty
 * @date 2021/6/28 11:03
 */
public class BunbleSort {

    /**
     * 冒泡排序
     * 将当前元素与其之后的元素依次就行比较
     * 若其之后的第n个元素小于其
     * 则交换当前元素与第n元素的位置
     * 然后比较第n+1下一个元素
     * <p>
     * 最基础的排序，效率最低
     * 时间复杂度 o n2
     *
     */
    public static int[] bubbleSort(int[] arr) {
        boolean flag = false;
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                //比较大小并交换
                if (arr[i] > arr[j]) {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    flag = true;
                }
            }
            //若在某个排序中一次交换也没有发生，说明已经有序了
            if (!flag) break;
            flag = false;
        }
        return arr;
    }
}
