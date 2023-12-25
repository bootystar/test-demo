package dataStructure.sort;

/**
 * @author booty
 * @date 2021/6/28 11:05
 */
public class ShellSort {

    /**
     * 希尔排序
     * 对插入排序的优化
     * 当插入排序中后方的数值较小时，其前方的所有元素都会重新赋值，影响效率
     * <p>
     * 希尔排序，现将指定需要排序的数组、集合进行分组
     * 分组数量为lengh/2，每组两个数字，
     * 同一组内两个数字的步长一致（下标之间的距离）
     * 并直接对这两个数字进行插入排序交换位置，小的放在前，大的放在后
     * 排序后再次进行分组，分组数量为length/2/2，
     * 依次类推，继续对分组内进行插入排序，直到分组数量为1
     * 当分组数量为1时，较小的数基本已经到了前面，较大的数都到了后面
     * <p>
     * 综合效率极高，尤其数据量较大时
     */
    public static int[] shellSort(int[] arr) {
        //取长度的一半作为间隔
        int gap = arr.length >> 1;
        while (gap > 0) {
//            System.out.println("分组元素间隔："+gap+"；分组处理前数据："+ Arrays.toString(arr));
            for (int i = gap; i < arr.length; i++) {
                //仅当前数小于前一个数时再做操作(gap间隔始终大于1，此处不会下标越界)
                if (arr[i] < arr[i - 1]) {
                    int j = i - gap;
                    int temp = arr[i];
                    //比较当前数与当前距离间隔length的数的大小
                    while (j >= 0 && arr[j] > temp) {
                        arr[j + gap] = arr[j];
                        j -= gap;
                    }
                    arr[j + gap] = temp;
                }

            }
//            System.out.println("分组元素间隔："+gap+"；分组处理后数据："+ Arrays.toString(arr));
            //取长度的一半
            gap = gap >> 1;
        }
        return arr;
    }
}
